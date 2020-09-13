[#ftl]
[@b.head/]
  [@b.grid items=unpaidTuitions var="unpaidTuition"]
    [@b.gridbar]
      bar.addItem("删除", action.remove());
      bar.addItem("清空导入",action.method("reset",null,null));
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="学号" property="std.user.code" width="13%"/]
      [@b.col title="姓名" property="std.user.name" width="8%"]
        ${unpaidTuition.std.user.name}
      [/@]
      [@b.col title="年级" property="std.state.grade" width="13%"/]
      [@b.col title="院系" property="std.state.department.name"  width="10%"]
        ${(unpaidTuition.std.state.department.shortName)!unpaidTuition.std.state.department.name}
      [/@]
      [@b.col title="专业" width="15%" property="std.state.major.name"]
      <span style="font-size:0.8em">${(unpaidTuition.fromMajor.name)!}</span>
      [/@]
    [/@]
  [/@]
[@b.foot/]
