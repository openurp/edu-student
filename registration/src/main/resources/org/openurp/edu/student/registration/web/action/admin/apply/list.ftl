[#ftl]
[@b.head/]
  [@b.grid items=registers var="register"]
    [@b.gridbar]
      function exportData(){
        var form = document.searchForm;
        bg.form.addInput(form, "keys", "std.user.code,std.user.name,std.person.gender.name,fromGrade,fromDepart.name,fromMajor.name,fromDirection.name,fromSquad.name,toGrade,toDepart.name,toMajor.name,toDirection.name,toSquad.name,gpa,majorGpa,otherGpa,mobile,adjustable,state");
        bg.form.addInput(form, "titles", "学号,姓名,性别,转出年级,转出院系,转出专业,转出方向,转出班级,转入年级,转入院系,转入专业,转入方向,转入班级,总绩点,专业课成绩绩点,专业课外成绩绩点,联系电话,是否服从调剂,状态");
        bg.form.addInput(form, "fileName", "学生转专业申请名单");
        bg.form.submit(form, "${b.url('!export')}","_self");
      }
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="学号" property="std.user.code" width="15%"/]
      [@b.col title="姓名" property="std.user.name" width="10%"]
        ${register.std.user.name}
      [/@]
      [@b.col title="年级" property="std.state.grade" width="10%"/]
      [@b.col title="院系" property="std.state.department.name"  width="15%"]
        ${(register.std.state.department.shortName)!register.std.state.department.name}
      [/@]
      [@b.col title="专业(方向)" width="20%" property="std.state.major.name"]
      <span style="font-size:0.8em">${(register.std.state.major.name)!} ${(register.std.state.direction.name)!}</span>
      [/@]
      [@b.col title="学籍状态" property="std.state.status.name" width="10%"/]
      [@b.col title="注册日期" width="10%" property="registerAt" width="10%"]
        ${register.registerAt?string("yyyy-MM-dd")}
      [/@]
    [/@]
  [/@]
[@b.foot/]
