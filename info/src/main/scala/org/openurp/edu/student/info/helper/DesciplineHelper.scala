package org.openurp.edu.student.info.helper

import java.time.LocalDate

import org.openurp.base.edu.model.Major

class DesciplineHelper(graduateOn: LocalDate) {

  def getDisciplineCode(major: Major): String = {
    major.disciplineCode(graduateOn)
  }
}