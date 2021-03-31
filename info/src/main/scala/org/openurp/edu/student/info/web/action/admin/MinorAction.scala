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
package org.openurp.edu.student.info.web.action.admin

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.transfer.excel.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.api.view.Stream
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.boot.edu.helper.ProjectSupport
import org.openurp.code.edu.model.{DisciplineCategory, Institution}
import org.openurp.edu.student.info.helper.MinorImportListener
import org.openurp.std.info.model.Minor

class MinorAction extends RestfulAction[Minor] with ProjectSupport {

	override def indexSetting(): Unit = {
		val builder = OqlBuilder.from(classOf[Minor].getName,"m")
		builder.select("distinct m.school")
		val schools = entityDao.search(builder)
		put("schools", schools)
		super.indexSetting()
	}

	override def editSetting(entity: Minor): Unit = {
		put("schools", entityDao.getAll(classOf[Institution]))
		put("majorCategories", getCodes(classOf[DisciplineCategory]))
		super.editSetting(entity)
	}

	protected override def configImport(setting: ImportSetting): Unit = {
		val fl = new ForeignerListener(entityDao)
		fl.addForeigerKey("name")
		setting.listeners = List(fl, new MinorImportListener(entityDao))
	}

	/**
	 * 下载模板
	 */
	@response
	def downloadTemplate(): Any = {
		val schools = entityDao.search(OqlBuilder.from(classOf[Institution], "ms").orderBy("ms.name")).map(_.name)
		val majorCategories = entityDao.search(OqlBuilder.from(classOf[DisciplineCategory], "dc").orderBy("dc.name")).map(_.name)

		val schema = new ExcelSchema()
		val sheet = schema.createScheet("数据模板")
		sheet.title("辅修学生信息模板")
		sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
		sheet.add("学号", "minor.std.user.code").length(100).required()
		sheet.add("主修学校", "minor.school.name").ref(schools).required()
		sheet.add("主修专业", "minor.majorName").length(300).required()
		sheet.add("主修专业英文名", "minor.enMajorName").length(300)
		sheet.add("主修专业学科门类", "minor.majorCategory.name").ref(majorCategories).required()

		val code = schema.createScheet("数据字典")
		code.add("学校").data(schools)
		code.add("专业学科门类").data(majorCategories)
		val os = new ByteArrayOutputStream()
		schema.generate(os)
		Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "辅修学生信息模板.xlsx")
	}

}
