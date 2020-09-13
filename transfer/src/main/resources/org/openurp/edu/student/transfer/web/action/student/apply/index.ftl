[#ftl]
[@b.head/]
[#macro panel title]
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">${title}</h3>
  </div>
  [#nested/]
</div>
[/#macro]

<div class="container" style="width:95%">

<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-book"></span>转专业申请</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
    [#list schemes  as scheme]
        <li>
        [@b.form class="navbar-form navbar-left" role="search" action="!editNew"]
            [@b.a class="btn btn-sm btn-info" href="!editNew?scheme.id="+scheme.id]<span class='glyphicon glyphicon-plus'></span>申请[/@]
        [/@]
        </li>
     [/#list]
    </ul>
    </div>
</nav>


  [#list applies as apply]
  [@b.form name="removeApplyForm_"+apply.id action="!remove?id="+apply.id+"&_method=delete" ][/@]
  [#assign scheme= apply.option.scheme/]
  [#assign title]
     <span class="glyphicon glyphicon-bookmark"></span>${apply.option.scheme.name}<span style="font-size:0.8em">(${scheme.applyBeginAt?string("yyyy-MM-dd HH:mm")}~${scheme.applyEndAt?string("yyyy-MM-dd HH:mm")})</span>
       <div class="btn-group">
     [#if scheme.canApply]
       [@b.a href="!edit?id="+apply.id+"&scheme.id="+scheme.id class="btn btn-sm btn-info"]<span class="glyphicon glyphicon-edit"></span>修改[/@]
     [/#if]
       [@b.a href="!download?id="+apply.id class="btn btn-sm btn-info"]<span class="glyphicon glyphicon-download"></span>下载[/@]
       </div>
     [#if scheme.canApply]
       [#if apply.state!="通过"]
       <a href="#" onclick="return removeApply('${apply.id}');" class="btn btn-sm btn-warning"><span class="glyphicon glyphicon-remove"></span>删除</a>
       [/#if]
     [/#if]

  [/#assign]
  [@panel title=title]
    [@b.div id="scheme_info"][#include "info.ftl"/][/@]
  [/@]
  [/#list]
</div>
<script>
   function removeApply(id){
       if(confirm("确定删除?")){
         return bg.form.submit(document.getElementById("removeApplyForm_"+id));
       }else{
         return false;
       }
   }
</script>
</div>
[@b.foot/]