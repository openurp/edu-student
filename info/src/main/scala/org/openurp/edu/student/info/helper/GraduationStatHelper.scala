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
package org.openurp.edu.student.info.helper

import org.openurp.code.edu.model.EducationLevel
import org.openurp.edu.student.stat.data.GraduationStat

class GraduationStatHelper {

  def dataByDepartmentEducationLevel(eduLevels: Seq[EducationLevel], graduationStats: Seq[GraduationStat]): Map[String, Map[String, Seq[GraduationStat]]] = {
    graduationStats.filter(e => eduLevels.contains(e.eduLevel))
      .groupBy(_.department.id.toString).map {
      case (departmentId, value) => (departmentId, value.groupBy(_.eduLevel.id.toString).map {
        case (levelId, value) => (levelId, value.sortBy(f => f.major.id + f.gender.id))
      })
    }

  }

  def dataByEducationLevelMajor(graduationStats: Seq[GraduationStat]): Map[String, Map[String, Map[String, Seq[GraduationStat]]]] = {
    graduationStats.groupBy(_.eduLevel.id.toString).map {
      case (levelId, value) => (levelId, value.groupBy(_.major.id.toString).map {
        case (majorId, value) => (majorId, value.groupBy(_.gender.id.toString))
      })
    }
  }

  def dataByDepartmentEducationLevel(graduationStats: List[GraduationStat]): Map[String, Map[String, Map[String, List[GraduationStat]]]] = {
    graduationStats.groupBy(_.department.id.toString).map {
      case (departmentId, value) => (departmentId, value.groupBy(_.eduLevel.id.toString).map {
        case (levelId, value) => (levelId, value.groupBy(_.major.id.toString))
      })
    }
  }
}
