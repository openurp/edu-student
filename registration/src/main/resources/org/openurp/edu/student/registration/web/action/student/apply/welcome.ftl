[#ftl]
[@b.head/]

<div class="container" style="width:95%">

<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-book"></span>报到注册</a>
    </div>
  </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <h2>报到注册</h2>
        [#if sessions?size>0]
        [#list sessions as session]
        <p>欢迎进入${session.semester.schoolYear}学年${session.semester.name} 新学期，请确认你的学籍信息。</p>
        [#if unpaidTutions?size=0]
        <p>
         [@b.a class="btn btn-lg btn-info" role="button" href="!editNew?session.id="+session.id]
          <span class='glyphicon glyphicon-plus'>开始注册</span><small>(${session.semester.schoolYear}学年${session.semester.name}学期)</small>[/@]
        </p>
        [#else]
           <a class="btn btn-lg btn-info disabled" role="button" href="#"><span class='glyphicon glyphicon-plus'>尚未完成学费缴纳,暂时不能注册</span><small>(${session.semester.schoolYear}学年${session.semester.name}学期)</small></a>
        [/#if]
        [/#list]
        [#else]
         <p>欢迎进入注册报到结果查询，现在还不能注册。</p>
        [/#if]
    </div>
</div>
</div>
[@b.foot/]
