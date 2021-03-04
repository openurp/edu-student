package org.openurp.edu.student.info

import java.io.{BufferedOutputStream, File, FileOutputStream}

import org.beangle.commons.lang.SystemInfo
import org.openurp.edu.student.info.helper.DocHelper

object DocTest {

  def main(args: Array[String]): Unit = {
        val data = Map("name" -> "钟昌洋", "x" -> "男", "bY" -> "1981", "bM" -> "10", "bD" -> "1", "school" -> "上海理工大学继续教育学院", "major" -> "广告学", "minor" -> "广告学", "degree" -> "degree", "diplomaNo" -> "1025242020000154", "y" -> "二0二一", "M" -> "三", "d" -> "四")
        val url = this.getClass.getResource("/org/openurp/edu/student/info/degreeDoc.docx")
        val bytes = DocHelper.toDoc(url, data)
        val file = new File(SystemInfo.tmpDir + File.separator + "degree.docx");
        val fos = new FileOutputStream(file)
        val bos = new BufferedOutputStream(fos)
        bos.write(bytes)


//    val data = Map("name" -> "钟昌洋", "x" -> "男", "bY" -> "1981", "bM" -> "10","major" -> "广告学", "minor" -> "广告学","sY" -> "2018", "sM" -> "9", "eY" -> "2020", "eM" -> "7", "y" -> "2021", "M" -> "3", "d" -> "4","code"->"102525202005000119")
//    val url = this.getClass.getResource("/org/openurp/edu/student/info/diplomaDoc.docx")
//    val bytes = DocHelper.toDoc(url, data)
//    val file = new File(SystemInfo.tmpDir + File.separator + "diploma.docx");
//    val fos = new FileOutputStream(file)
//    val bos = new BufferedOutputStream(fos)
//    bos.write(bytes)
  }


}
