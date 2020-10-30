[#ftl]
[@b.head/]

<style>
  .reportTable{
    border:0.5px solid #006CB2;
  }
  .reportTable td{
    border:0.5px solid #006CB2;
    text-align:center;
  }
  .reportTable .infoRow{
    height:11mm;
  }
  .reportTable .commentRow{
   height:25mm;
  }
</style>
[#list applies as apply]
  [#assign std=apply.std/]
 <div style="margin:auto;padding:5mm 10mm 0mm 10mm" >
  <h4 style="text-align:center">${apply.std.project.school.name}全日制本科生转专业（方向）申请表</h4>
  <table style="width:100%;border:0px" class="reportTable">
    <tr style="height:0px">
      <td width="16.7%" style="border:0px"></td>
      <td width="16.7%" style="border:0px"></td>
      <td width="9.2%" style="border:0px"></td>
      <td width="9.2%" style="border:0px"></td>
      <td width="9.2%" style="border:0px"></td>
      <td width="8.1%" style="border:0px"></td>
      <td width="5%" style="border:0px"></td>
      <td width="9.2%" style="border:0px"></td>
      <td width="16.7%" style="border:0px"></td>
    </tr>
    <tr class="infoRow">
      <td>姓名</td>
      <td>${std.user.name}</td>
      <td>性别</td>
      <td>${std.person.gender.name}</td>
      <td>学号</td>
      <td colspan="2">${std.user.code}</td>
      <td>班级</td>
      <td>
      [#if ((apply.fromSquad.name)!"")?length > 6]
      <span style="font-size:0.8em">${(std.state.squad.name)!}</span>
      [#else]
      ${(std.state.squad.name)!}
      [/#if]
      </td>
    </tr>
    <tr class="infoRow">
      <td>手机</td>
      <td>${apply.mobile}</td>
      <td colspan="2">宿舍电话</td>
      <td colspan="2"></td>
      <td colspan="2">住址电话</td>
      <td></td>
    </tr>
    <tr class="infoRow">
      <td>所在学院</td>
      <td>${(apply.fromDepart.name)!}</td>
      <td colspan="2">所学专业</td>
      <td colspan="2">${(apply.fromMajor.name)!}</td>
      <td colspan="2">专业方向</td>
      <td>${(apply.fromDirection.name)!}</td>
    </tr>
    <tr class="infoRow">
      <td>拟转入学院</td>
      <td>${(apply.toDepart.name)!}</td>
      <td colspan="2">拟转入专业</td>
      <td colspan="2">${(apply.toMajor.name)!}</td>
      <td colspan="2">拟转入方向</td>
      <td>${(apply.toDirection.name)!}</td>
    </tr>
    <tr class="infoRow">
      <td colspan="9">大一第一学期相应学分平均绩点（学校填写）</td>
    </tr>
    <tr class="infoRow">
      <td>总绩点</td>
      <td>${apply.gpa?string("#.00")}</td>
      <td colspan="2">专业课成绩绩点</td>
      <td colspan="2">${apply.majorGpa?string("#.00000")}</td>
      <td colspan="2">专业课以外成绩绩点</td>
      <td>${apply.otherGpa?string("#.00000")}</td>
    </tr>
    <tr class="infoRow">
      <td colspan="8" style="text-align:left">&nbsp;&nbsp;是否愿意调剂</td>
      <td>${apply.adjustable?string("是","否")}</td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      转专业（方向）理由，并提供相应材料（附后）：<br>
      ${apply.reason!}
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">申请人签名</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      学生所在学院推荐意见：<br>
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">签名（盖章）：</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      拟转入学院面试意见：<br>
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">面试教师签名：</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      拟转入学院意见：<br>
      </div>
      <div style="float:left;margin: 0px 0px 0px 30px;">
      <br><br> 编入新班级：
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">签名（盖章）：</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      教务处意见：<br>
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">签名（盖章）：</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
    <tr class="commentRow">
      <td colspan="9">
      <div style="width:100%;text-align:left;padding: 0px 0px 0px 30px;">
      学校审批意见：<br>
      </div>
      <div style="float:right"><br>
      <span style="margin-right:100px">签名（盖章）：</span><br>
      年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
      </div>
      </td>
    </tr>
  </table>
 </div>
  [#if apply_has_next]<div style="PAGE-BREAK-AFTER: always"></div>[/#if]
[/#list]
<script>
   document.body.style.padding="0px 0px";
</script>
[@b.foot/]