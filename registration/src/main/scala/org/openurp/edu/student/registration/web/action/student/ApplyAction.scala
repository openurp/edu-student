/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.student.registration.web.action.student

import java.time.Instant

import org.beangle.commons.web.util.RequestUtils
import org.beangle.data.dao.OqlBuilder
import org.beangle.security.Securities
import org.beangle.webmvc.api.annotation.mapping
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.edu.model.{Semester, Student}
import org.openurp.boot.edu.helper.ProjectSupport
import org.openurp.edu.student.registration.model.{RegisterSession, UnpaidTuition}
import org.openurp.std.register.model.Register

class ApplyAction extends RestfulAction[Register] with ProjectSupport {


  override def index(): View = {
    val std = getStudent2()
    val query = OqlBuilder.from(classOf[Register], "r")
    query.where("r.std=:std", std)
    query.orderBy("r.semester.beginOn desc")
    val registers = entityDao.search(query)
    put("registers", registers)
    val schemeQuery = OqlBuilder.from(classOf[RegisterSession], "s")
    schemeQuery.where("s.project=:project", std.project)
    schemeQuery.where("s.endAt>:now", Instant.now)
    schemeQuery.where("locate(:grade,s.grades)>0", std.state.get.grade)

    val sessions = entityDao.search(schemeQuery)
    put("sessions", sessions)
    if (sessions.nonEmpty) {
      val session = sessions.head
      put("unpaidTutions", getUnpaidTution(std, session.semester))
    }
    if (registers.isEmpty) {
      forward("welcome")
    } else {
      forward()
    }
  }

  @mapping(value = "new", view = "new,form")
  override def editNew(): View = {
    val session = entityDao.get(classOf[RegisterSession], longId("session"))
    if (!session.canApply()) {
      redirect("index", "不在操作时间内")
    }

    val std = getStudent2()
    var entity = getEntity(entityType, simpleEntityName)
    //如果没有给id,也需要查一查
    if (!entity.persisted) {
      val applyQuery = OqlBuilder.from(classOf[Register], "r")
      applyQuery.where("r.semester=:semester", session.semester)
      applyQuery.where("r.std=:me", std)
      entityDao.search(applyQuery) foreach { e =>
        entity = e
      }
    }
    put("session", session)
    put("std", std)
    put("mobile",std.user.mobile.getOrElse(""))
    editSetting(entity)
    put(simpleEntityName, entity)
    forward()
  }

  private def getUnpaidTution(std: Student, semester: Semester): Iterable[UnpaidTuition] = {
    val tuitionQuery = OqlBuilder.from(classOf[UnpaidTuition], "ut")
    tuitionQuery.where("ut.std=:std and ut.semester=:semester", std, semester)
    entityDao.search(tuitionQuery)
  }

  override def saveAndRedirect(apply: Register): View = {
    val scheme = entityDao.get(classOf[RegisterSession], longId("session"))
    val std = getStudent2()
    if (!scheme.canApply()) {
      redirect("index", "不在操作时间内")
    }
    if (getUnpaidTution(std, scheme.semester).nonEmpty) {
      redirect("index", "没有完成缴费")
    }
    apply.std = std
    apply.semester = scheme.semester
    apply.registered = Some(true)
    apply.registerAt = Some(Instant.now)
    apply.checkin = Some(true)
    apply.tuitionPaid = Some(true)
    apply.operateBy = std.user.code + " " + std.user.name
    apply.operateIp = RequestUtils.getIpAddr(request)

    std.user.mobile = get("mobile")
    saveOrUpdate(List(apply, std.user))
    redirect("index", "info.save.success")
  }

  protected def getStudent2(): Student = {
    val builder = OqlBuilder.from(classOf[Student], "s")
    builder.where("s.user.code=:code", Securities.user)
    entityDao.search(builder).head
  }

}
