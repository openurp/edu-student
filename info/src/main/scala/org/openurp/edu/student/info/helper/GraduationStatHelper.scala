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
