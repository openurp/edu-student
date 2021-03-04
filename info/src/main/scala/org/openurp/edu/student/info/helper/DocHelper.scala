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

import java.io.ByteArrayOutputStream
import java.net.URL
import java.time.LocalDate

import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.beangle.commons.collection.Collections
import org.openurp.std.info.model.Graduation

object DocHelper {

  def toDegreeDoc(graduation: Graduation): Array[Byte] = {
    val std = graduation.std
    val data = Collections.newMap[String, String]
    data.put("name", std.user.name)
    data.put("x", std.user.gender.name)

    std.person.foreach(person => {
      data.put("bY", person.birthday.getYear.toString)
      data.put("bM", person.birthday.getMonthValue.toString)
      data.put("bD", person.birthday.getDayOfMonth.toString)
    })

    data.put("school", std.project.school.name)
    data.put("major", std.state.get.major.name)
    data.put("minor", std.state.get.major.name) //修复模型后修复复修专业

    graduation.degree.foreach(degree => {
      data.put("degree", degree.name)
    })
    graduation.diplomaNo.foreach(diplomaNo => {
      data.put("diplomaNo", diplomaNo)
    })

    val chineseMap = Map("0" -> "0", "1" -> "一", "2" -> "二", "3" -> "三", "4" -> "四", "5" -> "五", "6" -> "六", "7" -> "七", "8" -> "八", "9" -> "九")
    val y = LocalDate.now().getYear.toString
    val m = LocalDate.now().getMonthValue.toString
    val d = LocalDate.now().getDayOfMonth.toString
    var year = new String("")
    for (c <- y) {
      year = year + chineseMap.get(c.toString).get
    }

    val month: String = m.length match {
      case 1 => chineseMap.get(m).get
      case 2 => "十" + chineseMap.get(m.charAt(1).toString)
    }

    val day: String = d.length match {
      case 1 => chineseMap.get(d).get
      case _ => {
        if (d.charAt(0).toString == "1") "十" + chineseMap.get(d.charAt(1).toString)
        else chineseMap.get(d.charAt(0).toString) + "十" + chineseMap.get(d.charAt(1).toString)
      }
    }

    data.put("y", year)
    data.put("M", month)
    data.put("d", day)

    val url = this.getClass.getResource("/org/openurp/edu/student/info/degreeDoc.docx")
    DocHelper.toDoc(url, data)
  }

  def toDiplomaDoc(graduation: Graduation): Array[Byte] = {
    val std = graduation.std
    val data = Collections.newMap[String, String]
    data.put("name", std.user.name)
    data.put("x", std.user.gender.name)

    std.person.foreach(person => {
      data.put("bY", person.birthday.getYear.toString)
      data.put("bM", person.birthday.getMonthValue.toString)
    })

    data.put("major", std.state.get.major.name)
    data.put("minor", std.state.get.major.name) //修复模型后修复复修专业

    data.put("sY", std.beginOn.getYear.toString)
    data.put("sM", std.beginOn.getMonthValue.toString)
    data.put("eY", std.endOn.getYear.toString)
    data.put("eM", std.endOn.getMonthValue.toString)

    data.put("y", LocalDate.now().getYear.toString)
    data.put("M", LocalDate.now().getMonthValue.toString)
    data.put("d", LocalDate.now().getDayOfMonth.toString)

    data.put("code", graduation.code)

    val url = this.getClass.getResource("/org/openurp/edu/student/info/diplomaDoc.docx")
    DocHelper.toDoc(url, data)
  }

  def toDoc(url: URL, data: collection.Map[String, String]): Array[Byte] = {
    val templateIs = url.openStream()
    val doc = new XWPFDocument(templateIs)
    import scala.jdk.javaapi.CollectionConverters._

    for (p <- asScala(doc.getParagraphs)) {
      val runs = p.getRuns
      if (runs != null) {
        for (r <- asScala(runs)) {
          var text = r.getText(0)
          if (text != null) {
            println(text)
            data.find { case (k, v) => text.contains("${" + k + "}") } foreach { e =>
              text = text.replace("${" + e._1 + "}", e._2)
              r.setText(text, 0)
            }
          }
        }
      }
    }

    for (tbl <- asScala(doc.getTables)) {
      for (row <- asScala(tbl.getRows)) {
        for (cell <- asScala(row.getTableCells)) {
          for (p <- asScala(cell.getParagraphs)) {
            for (r <- asScala(p.getRuns)) {
              var text = r.getText(0)
              if (text != null) {
                data.find { case (k, v) => text.contains("${" + k + "}") } foreach { e =>
                  text = text.replace("${" + e._1 + "}", e._2)
                  r.setText(text, 0)
                }
              }
            }
          }
        }
      }
    }
    val bos = new ByteArrayOutputStream()
    doc.write(bos)
    templateIs.close()
    bos.toByteArray
  }
}
