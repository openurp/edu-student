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
package org.openurp.edu.student.registration.model

import java.time.Instant
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.InstantRange
import org.openurp.base.edu.model.{Project, Semester}
import org.openurp.code.edu.model.EducationLevel

class RegisterSession extends LongId with InstantRange {

  var project: Project = _

  var semester: Semester = _

  var grades: String = _

  var level:EducationLevel=_

  def canApply(): Boolean = {
    val now = Instant.now
    !(this.beginAt.isAfter(now) || this.endAt.isBefore(now))
  }
}
