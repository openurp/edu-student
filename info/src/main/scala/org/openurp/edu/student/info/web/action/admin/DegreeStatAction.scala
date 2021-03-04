package org.openurp.edu.student.info.web.action.admin

import org.beangle.commons.collection.Collections
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.edu.model.Major
import org.openurp.base.model.Department
import org.openurp.code.edu.model.EducationLevel
import org.openurp.code.person.model.Gender
import org.openurp.edu.student.info.helper.{DesciplineHelper, GraduationStatHelper}
import org.openurp.edu.student.stat.data.GraduationStat
import org.openurp.std.info.model.Graduation

class DegreeStatAction extends RestfulAction {
  override protected def indexSetting(): Unit = {
    put("years", entityDao.search(OqlBuilder.from(classOf[Graduation].getName, "graduation").select("distinct graduation.degreeAwardOn").where("graduation.degreeAwardOn is not null")))
  }

  def stat(): View = {
    val builder: OqlBuilder[Array[AnyRef]] = OqlBuilder.from(classOf[Graduation].getName + " graduation")
    getDate("degreeAwardOn").foreach(degreeAwardOn => {
      builder.where("graduation.degreeAwardOn = :degreeAwardOn", degreeAwardOn)
      put("desciplineHelper", new DesciplineHelper(degreeAwardOn))
      put("degreeAwardOn",degreeAwardOn)
    })
    builder.where("graduation.std.state.major is not null")
    builder.where("graduation.std.state.department is not null")
    builder.groupBy("graduation.std.state.department.id, graduation.std.level.id,graduation.std.state.major.id,graduation.std.person.gender.id")
    builder.select("graduation.std.state.department.id, graduation.std.level.id,graduation.std.state.major.id,graduation.std.person.gender.id, count(*)")
    val results= entityDao.search(builder).asInstanceOf[Seq[Array[Any]]]
    val departmentMap: Map[String, Department] = entityDao.getAll(classOf[Department]).map(x => (x.id.toString, x)).toMap
    val educationLevelMap = entityDao.getAll(classOf[EducationLevel]).map(x => (x.id.toString, x)).toMap
    val majorMap = entityDao.getAll(classOf[Major]).map(x => (x.id.toString, x)).toMap
    val genderMap = entityDao.getAll(classOf[Gender]).map(x => (x.id.toString, x)).toMap
    put("departmentMap",departmentMap)
    put("departments",departmentMap.values.toSeq)
    put("educationLevelMap",educationLevelMap)
    put("educationLevels",educationLevelMap.values.toSeq)
    put("majorMap",majorMap)
    put("majors",majorMap.values.toSeq)
    put("genderMap",genderMap)
    put("genders",genderMap.values.toSeq)
    val gradautionStats = Collections.newBuffer[GraduationStat]
    results.foreach(result => {
      gradautionStats.+=:(new GraduationStat(departmentMap.get(result(0).toString).get, educationLevelMap.get(result(1).toString).get, majorMap.get(result(2).toString).get, genderMap.get(result(3).toString).get, result(4).asInstanceOf[Number]))
    })
    put("graduationStats", gradautionStats.toSeq)
    put("graduationStatHelper", new GraduationStatHelper)
    forward()
  }

}
