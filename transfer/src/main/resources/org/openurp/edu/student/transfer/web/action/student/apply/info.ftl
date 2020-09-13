[#ftl/]
[@b.messages slash="4"/]
[#assign scheme= apply.option.scheme/]
<table class="infoTable">
    <tr>
      <td class="title" width="10%">学年学期：</td>
      <td width="23%">${scheme.semester.schoolYear} 学年度 ${scheme.semester.name}学期</td>
      <td class="title" width="10%">学号姓名：</td>
      <td width="23%">${apply.std.user.code} ${apply.std.user.name}</td>
      <td class="title" width="10%">是否服从调剂</td>
      <td>${(apply.adjustable)?string("是","否")}</td>
    </tr>
    <tr>
      <td class="title">转入年级：</td>
      <td>${apply.toGrade}</td>
      <td class="title">转入院系：</td>
      <td>${apply.option.depart.name}</td>
      <td class="title">转入专业：</td>
      <td>${apply.option.major.name} ${(apply.option.direction.name)!}</td>
    </tr>
    <tr>
      <td class="title">联系电话：</td>
      <td>${apply.mobile}</td>
      <td class="title">联系邮箱：</td>
      <td>${apply.email}</td>
      <td class="title">状态：</td>
      <td>${apply.state} ${apply.updatedAt?string("yyyy-MM-dd HH:mm")}</td>
    </tr>
    <tr>
      <td class="title">转入理由：</td>
      <td>${apply.reason!}</td>
    </tr>
  </table>