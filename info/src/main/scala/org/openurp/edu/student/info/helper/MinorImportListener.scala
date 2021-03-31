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

import java.time.Instant

import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.edu.model.Student
import org.openurp.std.info.model.Minor

class MinorImportListener(entityDao: EntityDao) extends ImportListener {
	override def onStart(tr: ImportResult): Unit = {}

	override def onFinish(tr: ImportResult): Unit = {}

	override def onItemStart(tr: ImportResult): Unit = {
		transfer.curData.get("minor.std.user.code") foreach { code =>
			val cs = entityDao.findBy(classOf[Minor], "std.user.code", List(code))
			if (cs.nonEmpty) {
				transfer.current = cs.head
			}
		}
	}

	override def onItemFinish(tr: ImportResult): Unit = {
		val minor = transfer.current.asInstanceOf[Minor]
		minor.updatedAt = Instant.now
		entityDao.saveOrUpdate(minor)
	}
}