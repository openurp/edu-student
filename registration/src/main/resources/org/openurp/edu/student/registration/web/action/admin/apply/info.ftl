[#ftl/]
[@b.toolbar title="${transferApply.std.user.name}转专业信息"]
   bar.addBack();
[/@]
<table class="infoTable">
    <tr>
      <td class="title" width="10%">学年学期：</td>
      <td width="23%">${transferApply.option.scheme.semester.schoolYear} 学年度 ${transferApply.option.scheme.semester.name}学期</td>
      <td class="title" width="10%">学号姓名：</td>
      <td width="23%">${transferApply.std.user.code} ${transferApply.std.user.name}</td>
      <td class="title" width="12%">更新时间</td>
      <td>${transferApply.updatedAt?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
    <tr>
      <td class="title">原专业：</td>
      <td colspan="5">
       ${transferApply.fromGrade}级 ${transferApply.fromDepart.name} ${transferApply.fromMajor.name} ${(transferApply.fromDirection.name)!} ${(transferApply.fromSquad.name)!}
      </td>
    </tr>
    <tr>
      <td class="title">申请转入：</td>
      <td colspan="5">
        ${transferApply.toGrade}级 ${transferApply.option.depart.name} ${transferApply.option.major.name} ${(transferApply.option.direction.name)!}
      </td>
    </tr>
    [#if (transferApply.passed!false)]
    <tr>
      <td class="title">同意转入：</td>
      <td colspan="5">
        ${transferApply.toGrade}级 ${transferApply.toDepart.name} ${transferApply.toMajor.name} ${(transferApply.toDirection.name)!} ${(transferApply.toSquad.name)!}
      </td>
    </tr>
    [/#if]
    <tr>
      <td class="title">申请理由：</td>
      <td colspan="5">
        ${transferApply.reason!}
      </td>
    </tr>
    <tr>
      <td class="title">服从调剂：</td>
      <td>${transferApply.adjustable?string("服从","不服从")} </td>
      <td class="title">联系电话：</td>
      <td>${transferApply.mobile} </td>
      <td class="title">联系邮箱：</td>
      <td>${transferApply.email} </td>
    </tr>
    <tr>
      <td class="title">平均绩点：</td>
      <td>${transferApply.gpa?string("#.00")} </td>
      <td class="title">专业课绩点：</td>
      <td>${transferApply.majorGpa?string("#.00000")} </td>
      <td class="title">专业课外绩点：</td>
      <td>${transferApply.otherGpa?string("#.00000")}</td>
    </tr>
    <tr>
      <td class="title">状态：</td>
      <td>
        ${transferApply.state!}
      </td>
      <td class="title">申请表：</td>
      <td colspan="3">
        [@b.a href="!download?id="+ transferApply.id target="_blank"]下载[/@]
      </td>
    </tr>
  </table>

  [@b.grid items=logs?sort_by("logAt")?reverse var="log" sortable="false"]
      [@b.row]
        [@b.col title="操作" property="action" width="13%"/]
        [@b.col title="专业" property="content" width="50%"/]
        [@b.col title="IP" property="ip"  width="10%"/]
        [@b.col title="时间" width="15%" property="logAt"]
          ${log.logAt?string("yyyy-MM-dd HH:mm:ss")}
        [/@]
      [/@]
    [/@]