[#ftl/]
[@b.messages slash="4"/]
<table class="infoTable">
    <tr>
      <td class="title" width="10%">学年学期：</td>
      <td width="23%">${scheme.semester.schoolYear} 学年度 ${scheme.semester.name}学期</td>
      <td class="title" width="10%">名称：</td>
      <td width="23%">${scheme.name}</td>
      <td class="title" width="10%">是否发布</td>
      <td>${(scheme.published!false)?string("是","否")}</td>
    </tr>
    <tr>
      <td class="title">院系制定计划：</td>
      <td>${scheme.editBeginAt?string("yyyy-MM-dd HH:mm")}~${scheme.editEndAt?string("yyyy-MM-dd HH:mm")}</td>
      <td class="title">学生提交申请：</td>
      <td>${scheme.applyBeginAt?string("yyyy-MM-dd HH:mm")}~${scheme.applyEndAt?string("yyyy-MM-dd HH:mm")}</td>
      <td class="title">院系面试审核：</td>
      <td>${scheme.auditBeginAt?string("yyyy-MM-dd HH:mm")}~${scheme.auditEndAt?string("yyyy-MM-dd HH:mm")}</td>
    </tr>
    <tr>
      <td class="title">专业数目：</td>
      <td>${scheme.options?size}</td>
      <td class="title">总体计划人数：</td>
      <td>[#assign total=0][#list scheme.options as o][#assign total=total+o.planCount][/#list]${total}</td>
      <td class="title">总体申请人数：</td>
      <td>[#assign total=0][#list scheme.options as o][#assign total=total+o.currentCount][/#list]${total}</td>
    </tr>
  </table>

  [@b.grid items=scheme.options?sort_by(["major","code"]) sortable="false" var="option" ]
      [@b.row]
          [@b.col title="序号" width="5%"]${option_index+1}[/@]
          [@b.col property="depart.name" title="院系" width="25%"/]
          [@b.col property="major.name"  title="专业" width="25%"/]
          [@b.col property="direction.name" title="方向" width="25%"/]
          [@b.col property="currentCount"  title="已申请人数" width="10%"/]
          [@b.col property="planCount"  title="计划人数" width="10%"/]
      [/@]
  [/@]