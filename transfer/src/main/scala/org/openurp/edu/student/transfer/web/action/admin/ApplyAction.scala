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

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.transfer.exporter.ExportSetting
import org.beangle.webmvc.api.annotation.{mapping, param}
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.base.web.ProjectSupport
import org.openurp.edu.student.log.model.StdTransferApplyLog
import org.openurp.edu.student.transfer.model.{TransferApply, TransferScheme}
import org.openurp.edu.student.transfer.service.FirstGradeService
import org.openurp.edu.student.transfer.web.action.DocHelper
import org.openurp.edu.student.transfer.web.helper.ApplyPropertyExtractor

class ApplyAction extends RestfulAction[TransferApply] with ProjectSupport {
  var firstGradeService: FirstGradeService = _

  override def indexSetting(): Unit = {
    val project = getProject
    val schemeQuery = OqlBuilder.from(classOf[TransferScheme], "scheme")
    schemeQuery.where("scheme.project=:project", project)
    schemeQuery.orderBy("scheme.applyEndAt desc")
    val schemes = entityDao.search(schemeQuery)
    put("schemes", schemes)
    put("departs", project.departments)
  }

  @mapping(value = "{id}")
  override def info(id: String): View = {
    val apply = getModel[TransferApply](entityName, convertId(id))
    put("transferApply", apply)
    put("logs", entityDao.findBy(classOf[StdTransferApplyLog], "std", List(apply.std)))
    forward()
  }

  @mapping("download/{id}")
  def download(@param("id") id: String): View = {
    val apply = entityDao.get(classOf[TransferApply], id.toLong)
    val bytes = DocHelper.toDoc(apply)
    val filename = new String(apply.std.user.code.getBytes, "ISO8859-1")
    response.setHeader("Content-disposition", "attachment; filename=" + filename + ".docx")
    response.setHeader("Content-Length", bytes.length.toString)
    val out = response.getOutputStream
    out.write(bytes)
    out.flush()
    out.close()
    null
  }

  def report(): View = {
    val query = OqlBuilder.from(classOf[TransferApply], "apply")
    query.where("apply.id in(:applies)", longIds("transferApply"))
    query.orderBy("apply.std.user.code")
    val applies = entityDao.search(query)
    put("applies", applies)
    forward()
  }

  def recalcGpa(): View = {
    val ids = longIds("transferApply")
    val applies = entityDao.find(classOf[TransferApply], ids)
    applies foreach { apply =>
      val gpaStat = firstGradeService.stat(apply)
      apply.gpa = gpaStat.gpa
      apply.majorGpa = gpaStat.majorGpa
      apply.otherGpa = gpaStat.otherGpa
    }
    entityDao.saveOrUpdate(applies)
    redirect("search", "info.save.success")
  }

  override def configExport(setting: ExportSetting): Unit = {
    super.configExport(setting)
    setting.context.extractor = new ApplyPropertyExtractor
  }
}
