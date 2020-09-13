[#ftl]
[@b.head/]
[@b.toolbar title="转专业申请"/]
	[@b.form name="transferApplyForm" action=b.rest.save(transferApply) theme="list" onsubmit="checkApply"]
	  [@b.field label="学年学期"]${scheme.semester.schoolYear}学年度${scheme.semester.name}学期[/@]
	  [@b.field label="学号姓名"]${std.user.code} ${std.user.name}[/@]
    [@b.field label="现专业"]${std.state.major.name} ${(std.state.direction.name)!}[/@]
    [@b.textfield label="转入年级" name="transferApply.toGrade" value=transferApply.toGrade maxlength="10" required="true"/]
    [@b.select label="申请转入" id="applyOption" name="transferApply.option.id" style="width:400px" items=options option=r"${item.depart.name} ${item.major.name} ${(item.direction.name)!}" value=transferApply.option required="true" empty="..."/]
    [@b.radios label="是否服从调剂" name="transferApply.adjustable" value=transferApply.adjustable /]
    [@b.textfield label="联系电话" name="transferApply.mobile" value=transferApply.mobile maxlength="11" required="true"/]
    [@b.textfield label="联系邮箱" name="transferApply.email" value=transferApply.email maxlength="100" required="true"/]
    [@b.textarea label="申请理由" name="transferApply.reason" value=transferApply.reason! rows="7" cols="70" required="true" maxlength="300"  comment="200字以内"/]

    [@b.formfoot]
      <input type="hidden" name="scheme.id" value="${scheme.id}"/>
      [@b.submit value="提交"/]
    [/@]
  [/@]
  <script>
    var valided=false;
    function checkApply(form){
      if(valided){
         return false;
      }
      var adjustMsg="不服从调剂";
      $("input[name='transferApply.adjustable']").each(function(i,e){if(e.checked && e.value=="1"){adjustMsg="服从调剂";}})
      var answer= confirm("确定申请转入【"+$("#applyOption").find("option:selected").text()+"】,并且"+adjustMsg+"?");
      if(answer){valided=true;}
      return answer;
    }
  </script>
[@b.foot/]