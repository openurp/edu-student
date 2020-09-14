[#ftl]
[@b.head/]
[#assign title]${session.semester.schoolYear}学年度${session.semester.name}学期注册报到，学籍信息确认[/#assign]
[@b.toolbar title=title/]
	[@b.form name="registerForm" action=b.rest.save(register) theme="list"]
	  [@b.field label="学年学期"]${session.semester.schoolYear}学年度${session.semester.name}学期[/@]
	  [@b.field label="学号"]${std.user.code} ${std.user.name}[/@]
	  [@b.field label="年级"]${std.state.grade}[/@]
	  [@b.field label="院系"]${std.state.department.name}[/@]
    [@b.field label="专业(方向)"]${(std.state.major.name)} ${(std.state.direction.name)!}[/@]
    [@b.field label="班级"]${(std.state.squad.name)}[/@]
    [@b.field label="学籍状态"]${(std.state.status.name)}[/@]
    [@b.field label="学费情况"]未欠费[/@]
    [@b.field label="注册日期"]${b.now?string("yyyy-MM-dd")}[/@]
    [@b.textfield label="联系电话" name="mobile" value=std.user.mobile maxlength="11" required="true"/]

    [@b.formfoot]
      <input type="hidden" name="session.id" value="${session.id}"/>
      [@b.submit value="确认学籍信息，开始注册"/]
    [/@]
  [/@]
[@b.foot/]