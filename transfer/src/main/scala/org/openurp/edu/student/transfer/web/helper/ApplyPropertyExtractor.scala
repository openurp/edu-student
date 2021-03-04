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
package org.openurp.edu.student.transfer.web.helper

import java.text.NumberFormat

import org.beangle.data.transfer.exporter.DefaultPropertyExtractor
import org.openurp.std.transfer.model.TransferApply

class ApplyPropertyExtractor extends DefaultPropertyExtractor {

  val gpaFormater = NumberFormat.getNumberInstance
  val majorGpaFormater = NumberFormat.getNumberInstance
  gpaFormater.setMinimumFractionDigits(2)
  majorGpaFormater.setMinimumFractionDigits(5)

  override def getPropertyValue(target: Object, property: String): Any = {
    property match {
      case "gpa" => gpaFormater.format(target.asInstanceOf[TransferApply].gpa)
      case "majorGpa" =>
        majorGpaFormater.format(target.asInstanceOf[TransferApply].majorGpa)
      case "otherGpa" => majorGpaFormater.format(target.asInstanceOf[TransferApply].otherGpa)
      case _ => super.getPropertyValue(target, property)
    }
  }
}
