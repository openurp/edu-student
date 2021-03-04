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
package org.openurp.edu.student.transfer.service

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.openurp.base.edu.model.Course
import org.openurp.code.edu.model.GradeType
import org.openurp.edu.grade.course.model.CourseGrade
import org.openurp.edu.grade.model.Grade
import org.openurp.edu.program.domain.AlternativeCourseProvider
import org.openurp.edu.program.model.AlternativeCourse
import org.openurp.std.transfer.model.TransferApply

import scala.collection.mutable

class DefaultFirstGradeService extends FirstGradeService {

  var entityDao: EntityDao = _
  var alternativeCourseProvider: AlternativeCourseProvider = _

  def stat(apply: TransferApply): FirstGradeStat = {
    val std = apply.std
    val query = OqlBuilder.from(classOf[CourseGrade], "cg")
    query.where("cg.std=:std", std)
    query.where("cg.status=:status", Grade.Status.Published)
    query.where("cg.semester.endOn < :endOn", apply.option.scheme.semester.endOn)
    // 不算再次重修的，不算替代的，缓考过滤掉，只取期末总评
    query.where("not exists(from " + classOf[CourseGrade].getName +
      " cg2 where cg2.std=cg.std and cg2.status=" + Grade.Status.Published +
      " and cg2.course=cg.course and cg2.semester.beginOn < cg.semester.beginOn)")
    val courseGrades = entityDao.search(query).toBuffer

    val scList = alternativeCourseProvider.getAlternatives(std)
    if (scList.nonEmpty) {
      val gradeCourses = courseGrades.map(_.course).toSet
      for (sc <- scList) {
        removeSubstitutes(gradeCourses, sc, courseGrades)
      }
    }

    import org.openurp.edu.grade.course.domain.GradeFilters._
    // 过滤缓考成绩
    val leftGrades = chain(NotExemption,Stable).filter(courseGrades)

    var allGp: Float = 0
    var allCredit: Float = 0

    var majorGp: Float = 0
    var majorCredit: Float = 0

    var otherGp: Float = 0
    var otherCredit: Float = 0

    for (g <- leftGrades) {
      val gaGp: Float =
        g.getGaGrade(new GradeType(GradeType.DelayGa)) match {
          case Some(dg) => dg.gp.getOrElse(0f)
          case None =>
            g.getGaGrade(new GradeType(GradeType.EndGa)) match {
              case Some(eg) => eg.gp.getOrElse(0f)
              case None =>
                if(g.getExamGrade(new GradeType(GradeType.Makeup)).nonEmpty){
                  0f
                }else{
                  g.gp.getOrElse(0f)
                }
            }
        }
      allGp += gaGp * g.course.credits
      allCredit += g.course.credits
      if (g.courseType.major) {
        majorGp += gaGp * g.course.credits
        majorCredit += g.course.credits
      } else {
        otherGp += gaGp * g.course.credits
        otherCredit += g.course.credits
      }
    }

    val allGpa = if (allCredit != 0) allGp / allCredit else 0
    val majorGpa = if (majorCredit != 0) majorGp / majorCredit else 0
    val otherGpa = if (otherCredit != 0) otherGp / otherCredit else 0
    FirstGradeStat(allGpa, majorGpa, otherGpa)
  }

  protected def removeSubstitutes(gradeCourses: collection.Set[Course], substitute: AlternativeCourse, cgs: mutable.Buffer[CourseGrade]): Unit = {
    val fullGrade1 = substitute.olds.forall(x => gradeCourses.contains(x))
    if (fullGrade1) {
      val subs = substitute.news.toSet
      val removed = cgs.filter { x => subs.contains(x.course) }
      if (removed.nonEmpty) cgs.subtractAll(removed)
    }
  }
}
