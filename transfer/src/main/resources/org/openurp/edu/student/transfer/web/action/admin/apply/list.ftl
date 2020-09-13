[#ftl]
[@b.head/]
  [@b.grid items=transferApplies var="transferApply"]
    [@b.gridbar]
      bar.addItem("计算绩点", action.multi("recalcGpa"));
      bar.addItem("打印申请表",action.multi("report",null,null,"_blank"));
      bar.addItem("${b.text('action.export')}", "exportData()");
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
      [@b.col title="学号" property="std.user.code" width="13%"/]
      [@b.col title="姓名" property="std.user.name" width="8%"]
        [@b.a href="!info?id="+transferApply.id]${transferApply.std.user.name}[/@]
      [/@]
      [@b.col title="转出院系" property="std.fromDepart.name"  width="10%"]
        ${(transferApply.fromDepart.shortName)!transferApply.fromDepart.name}
      [/@]
      [@b.col title="转出专业" width="15%" property="fromMajor.name"]
      <span style="font-size:0.8em">${(transferApply.fromMajor.name)!}</span>
      [/@]
      [@b.col title="申请转入" property="option.major.name" width="30%"]
        ${transferApply.option.major.name} ${(transferApply.option.direction.name)!}
      [/@]
      [@b.col title="服从调剂" width="9%" property="adjustable"]
        ${transferApply.adjustable?string('是','否')}
      [/@]
      [@b.col title="状态" width="10%" property="state"/]
    [/@]
  [/@]
[@b.foot/]
