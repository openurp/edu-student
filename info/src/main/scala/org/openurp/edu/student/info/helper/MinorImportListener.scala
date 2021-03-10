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