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
package org.openurp.edu.student.registration.web.action.admin

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.boot.edu.helper.ProjectSupport
import org.openurp.edu.student.registration.model.RegisterSession

class SessionAction extends RestfulAction[RegisterSession] with ProjectSupport {
  override def indexSetting(): Unit = {
    val semester = getCurrentSemester
    val sessions = entityDao.findBy(classOf[RegisterSession], "semester", List(semester))
    put("sessions", sessions)
    put("project", getProject)
    put("semester", semester)
  }

  override def saveAndRedirect(entity: RegisterSession): View = {
    entity.project = getProject
    if (null == entity.semester) {
      entity.semester = getCurrentSemester
    }
    saveOrUpdate(entity)
    redirect("index", "info.save.success")
  }

  override def editSetting(scheme: RegisterSession): Unit = {
    if (null == scheme.semester) {
      scheme.semester = getCurrentSemester
    }
  }
}
