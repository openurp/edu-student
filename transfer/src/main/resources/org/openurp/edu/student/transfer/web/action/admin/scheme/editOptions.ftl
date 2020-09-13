[@b.toolbar title="修改转专业计划人数"]
   bar.addItem("保存","saveOption()");
   function saveOption(){
     bg.form.submit(document.schemeOptionEditForm);
   }
[/@]
[@b.form name="schemeOptionEditForm" action="!saveOptions"]
  [@b.grid items=options sortable="false" var="option" ]
      [@b.row]
          [@b.col title="序号" width="5%"]${option_index+1}[/@]
          [@b.col property="depart.name" title="院系" width="25%"/]
          [@b.col property="major.name"  title="专业" width="25%"/]
          [@b.col property="direction.name"  title="方向" width="25%"/]
          [@b.col property="currentCount"  title="已申请人数" width="10%"/]
          [@b.col property="planCount"  title="计划人数" width="10%"]
            [#if option.id??]
              <input name="option${option_index+1}_id"  type="hidden" value="${option.id}"/>
              <input name="option${option_index+1}_count" value="${option.planCount}" style="width:50px"/>
            [#else]
              <input name="option${option_index+1}_name"  type="hidden"  value="${option.depart.id}_${option.major.id}_${(option.direction.id)!'null'}"/>
              <input name="${option.depart.id}_${option.major.id}_${(option.direction.id)!'null'}" value="" style="width:50px"/>
            [/#if]
          [/@]
      [/@]
  [/@]
  <input name="transferScheme.id" value="${scheme.id}" type="hidden">
  <input name="optionCount" value="${options?size}" type="hidden">
[/@]