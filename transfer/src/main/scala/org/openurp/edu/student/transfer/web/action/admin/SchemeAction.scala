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
package org.openurp.edu.student.transfer.web.action.admin

import java.time.ZoneId

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.edu.base.model.{Direction, Major}
import org.openurp.edu.web.ProjectSupport
import org.openurp.edu.student.transfer.model.{TransferApply, TransferOption, TransferScheme}

class SchemeAction extends RestfulAction[TransferScheme] with ProjectSupport {
  override def indexSetting(): Unit = {
    val semester = getCurrentSemester
    val schemes = entityDao.findBy(classOf[TransferScheme], "semester", List(semester))
    schemes foreach { scheme =>
      val applyQuery = OqlBuilder.from[Any](classOf[TransferApply].getName, "a")
      applyQuery.where("a.option.scheme=:scheme", scheme)
      applyQuery.select("a.option.id,count(*)").groupBy("a.option.id")
      val rs = entityDao.search(applyQuery)
      val optionsMap = scheme.options.groupBy(_.id)
      rs foreach { d =>
        val data = d.asInstanceOf[Array[AnyRef]]
        optionsMap.get(data(0).asInstanceOf[Number].longValue()) foreach { option =>
          option.head.currentCount = data(1).asInstanceOf[Number].intValue()
        }
      }
      entityDao.saveOrUpdate(scheme)
    }
    put("schemes", schemes)
    put("project", getProject)
    put("semester", semester)
  }

  override def editSetting(scheme: TransferScheme): Unit = {
    if (null == scheme.semester) {
      scheme.semester = getCurrentSemester
    }
  }

  def addOptions: View = {
    val scheme = entityDao.get(classOf[TransferScheme], longId("transferScheme"))
    val project = getProject
    val majors = entityDao.findBy(classOf[Major], "project", List(project))
    val options = Collections.newBuffer[TransferOption]
    val now = scheme.applyBeginAt.atZone(ZoneId.systemDefault()).toLocalDate
    majors foreach { m =>
      if (m.within(now) && !m.code.startsWith("FX")) {
        var findDirection = false
        m.directions foreach { d =>
          val departs = Collections.newBuffer[Department]
          if (d.within(now)) {
            d.journals foreach { dj =>
              if (dj.within(now)) {
                departs += dj.depart
              }
            }
          }
          findDirection = departs.nonEmpty
          departs foreach { depart =>
            val existed = scheme.options.exists(x => x.depart == depart && x.major == m && x.direction == Some(d))
            if (!existed) {
              val option = new TransferOption
              option.depart = depart
              option.major = m
              option.direction = Some(d)
              options += option
            }
          }
        }
        if (!findDirection) {
          m.journals foreach { mj =>
            if (mj.within(now)) {
              val existed = scheme.options.exists(x => x.depart == mj.depart && x.major == m && x.direction.isEmpty)
              if (!existed) {
                val option = new TransferOption
                option.depart = mj.depart
                option.major = m
                options += option
              }
            }
          }
        }
      }
    }

    put("scheme", scheme)
    val sorted = options.sortBy(x => x.depart.code + x.major.code)
    put("options", sorted)
    forward("editOptions")
  }

  def editOptions: View = {
    val scheme = entityDao.get(classOf[TransferScheme], longId("transferScheme"))
    put("options", scheme.options)
    put("scheme", scheme)
    forward("editOptions")
  }

  def saveOptions: View = {
    val scheme = entityDao.get(classOf[TransferScheme], longId("transferScheme"))
    val count = getInt("optionCount", 0)
    (1 to count) foreach {
      idx =>
        get("option" + idx + "_id") match {
          case Some(id) =>
            val o = entityDao.get(classOf[TransferOption], id.toLong)
            val planCount = getInt("option" + idx + "_count", 0)
            if (planCount <= 0) {
              scheme.options.subtractOne(o)
            } else {
              o.planCount = planCount
              entityDao.saveOrUpdate(o)
            }
          case None =>
            val name = get("option" + idx + "_name").get
            val planCount = getInt(name, 0)
            if (planCount > 0) {
              val ids = Strings.split(name, "_")
              val depart = entityDao.get(classOf[Department], ids(0).toInt)
              val major = entityDao.get(classOf[Major], ids(1).toLong)
              var direction: Option[Direction] = None
              if (ids(2) != "null") {
                direction = Some(entityDao.get(classOf[Direction], ids(2).toLong))
              }
              val option = new TransferOption
              option.scheme = scheme
              option.depart = depart
              option.major = major
              option.direction = direction
              option.planCount = planCount
              scheme.options += option
            }
        }
    }
    entityDao.saveOrUpdate(scheme)
    put("scheme", scheme)
    forward("info")
  }

  override def saveAndRedirect(entity: TransferScheme): View = {
    entity.project = getProject
    if (null == entity.semester) {
      entity.semester = getCurrentSemester
    }
    saveOrUpdate(entity)
    redirect("index", "info.save.success")
  }
}
