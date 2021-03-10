[#ftl]
[@b.head/]
[@b.toolbar title="辅修学生信息维护"/]
<div class="search-container">
    <div class="search-panel">
        [@b.form name="minorSearchForm" action="!search" target="minorlist" title="ui.searchForm" theme="search"]
            [@b.textfields names="minor.std.user.code;学号"/]
            [@b.textfields names="minor.std.user.name;姓名"/]
            [@b.select style="width:100px" name="minor.school.id" label="主修学校" items=schools option="id,name" empty="..." /]
            <input type="hidden" name="orderBy" value="minor.std.user.code"/>
        [/@]
    </div>
    <div class="search-list">[@b.div id="minorlist" href="!search?orderBy=minor.std.user.code asc"/]
  </div>
</div>
[@b.foot/]
