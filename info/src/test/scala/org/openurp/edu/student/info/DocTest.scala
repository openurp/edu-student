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
package org.openurp.edu.student.info

import java.io.{BufferedOutputStream, File, FileOutputStream}

import org.beangle.commons.lang.SystemInfo
import org.openurp.edu.student.info.helper.DocHelper

object DocTest {

  def main(args: Array[String]): Unit = {
        val data = Map("name" -> "钟昌洋", "x" -> "男", "bY" -> "1981", "bM" -> "10", "bD" -> "1", "ss" -> "上海理工大学继续教育学院","school" -> "上海理工大学继续教育学院", "major" -> "广告学", "minor" -> "广告学", "degree" -> "文学学士", "diplomaNo" -> "1025242020000154", "y" -> "二0二一", "M" -> "三", "d" -> "四")
        val url = this.getClass.getResource("/org/openurp/edu/student/info/majorDegreeDoc.docx")
        val bytes = DocHelper.toDoc(url, data)
        val file = new File(SystemInfo.tmpDir + File.separator + "degree.docx");
        val fos = new FileOutputStream(file)
        val bos = new BufferedOutputStream(fos)
        bos.write(bytes)
        bos.close()


//    val data = Map("name" -> "钟昌洋", "x" -> "男", "bY" -> "1981", "bM" -> "10","major" -> "广告学",  "ss" -> "上海理工大学继续教育学院","minor" -> "广告学","sY" -> "2018", "sM" -> "9", "eY" -> "2020", "eM" -> "7", "y" -> "2021", "M" -> "3", "d" -> "4","code"->"102525202005000119")
//    val url = this.getClass.getResource("/org/openurp/edu/student/info/minorDiplomaDoc.docx")
//    val bytes = DocHelper.toDoc(url, data)
//    val file = new File(SystemInfo.tmpDir + File.separator + "diploma.docx");
//    val fos = new FileOutputStream(file)
//    val bos = new BufferedOutputStream(fos)
//    bos.write(bytes)
//    bos.close()
  }


}
