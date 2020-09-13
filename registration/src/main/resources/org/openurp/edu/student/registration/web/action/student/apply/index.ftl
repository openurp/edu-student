[#ftl]
[@b.head/]
[#macro panel title]
<div class="card card-info card-primary card-outline">
  <div class="card-header">
    <h3 class="card-title">${title}</h3>
  </div>
  [#nested/]
</div>
[/#macro]

<div class="container" style="width:95%">

<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-book"></span>注册报到</a>
    </div>
  </div>
</nav>

  [#list registers as register]
  [#assign title]
     <span class="glyphicon glyphicon-bookmark"></span>完成${register.semester.schoolYear}学年${register.semester.name}学期注册 <span style="font-size:0.8em">(${register.registerAt?string("yyyy-MM-dd")})</span>
  [/#assign]
  [@panel title=title]
    [@b.div id="session_info"][#include "info.ftl"/][/@]
  [/@]
  [/#list]
</div>
</div>
[@b.foot/]
