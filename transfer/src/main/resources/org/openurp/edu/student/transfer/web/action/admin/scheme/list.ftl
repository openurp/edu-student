[#ftl]
[@b.head/]
[@b.form name="transferSchemeListForm" target="contentDiv" action="!search"/]
	[@b.grid items=transferSchemes var="transferScheme"]
		[@b.gridbar]
			bar.addItem("统计","stat()");
			bar.addItem("统计专业计划提交情况",action.single("statUnsubmitMajors"));
			bar.addItem("${b.text('action.info')}",action.info());
			bar.addItem("${b.text('action.add')}",action.add()); 
			bar.addItem("${b.text('action.edit')}",action.edit());
			bar.addItem("${b.text('action.delete')}",action.remove());
			bar.addItem("导出详细信息","exportData()");
			var menu = bar.addMenu("期号通知管理");
			menu.addItem("上传","upload()");
			menu.addItem("下载","download()");
			menu.addItem("删除通知",action.multi("removeNotice"));
			
			function upload(){
				var form =document.transferSchemeListForm;
				var id = bg.input.getCheckBoxValues("transferScheme.id");
				if(!id || id.indexOf(",")>-1){
					alert("请只选择一条记录");
					return;
				}
				bg.form.addInput(form,"transferScheme.id",id);
				bg.form.submit(form,"transferScheme!importForm.action","_blank");	
			}
			
			function download(){
				var form =document.transferSchemeListForm;
				var id = bg.input.getCheckBoxValues("transferScheme.id");
				if(!id || id.indexOf(",")>-1){
					alert("请只选择一条记录");
					return;
				}
				bg.form.addInput(form,"transferScheme.id",id);
				bg.form.submit(form,"transferScheme!download.action","_blank");	
			}
			
			function exportData(){
				var form =document.transferSchemeListForm;
				var id = bg.input.getCheckBoxValues("transferScheme.id");
				if(!id || id.indexOf(",")>-1){
					alert("请只选择一条记录");
					return;
				}
				bg.form.addInput(form,"fileName","转专业期号详细信息导出");	
				bg.form.addInput(form,"template","template/excel/transferSchemeInfoExport.xls");
				bg.form.addInput(form,"transferScheme.id",id);
				bg.form.submit(form,"transferScheme!export.action","_self");	
			}
			
			function stat(){
				var form =document.transferSchemeListForm;
				var id = bg.input.getCheckBoxValues("transferScheme.id");
				if(!id || id.indexOf(",")>-1){
					alert("请选择一条记录");
					return;
				}
				bg.form.addInput(form,"transferScheme.id",id);
				bg.form.submit(form,"transferScheme!stat.action");
			}
		[/@]
		[@b.row]
			[@b.boxcol/]
			[@b.col property="name" title="名称"/]
			[@b.col property="semester.code" title="学期"]${transferScheme.semester.schoolYear}学年${transferScheme.semester.name?replace("0","第")}学期[/@]
			[@b.col title="转入专业数量"]${(transferScheme.changeMajorApplyMajors?size)?default(0)}[/@]
			[@b.col property="applyBeginAt" title="学生申请开始时间"]${transferScheme.applyBeginAt?string("yyyy-MM-dd HH:mm")}[/@]
			[@b.col property="applyEndAt" title="学生申请结束时间"]${transferScheme.applyEndAt?string("yyyy-MM-dd HH:mm")}[/@]
			[@b.col property="editBeginAt" title="院系提交计划开始"]${transferScheme.editBeginAt?string("yyyy-MM-dd HH:mm")}[/@]
			[@b.col property="editEndAt" title="院系提交计划截止"]${transferScheme.editEndAt?string("yyyy-MM-dd HH:mm")}[/@]
			[@b.col property="auditBeginAt" title="院系审核开始"]${transferScheme.auditBeginAt?string("yyyy-MM-dd HH:mm")}[/@]
			[@b.col property="auditEndAt" title="院系审核截止"]${transferScheme.auditEndAt?string("yyyy-MM-dd HH:mm")}[/@]
		[/@]
	[/@]
[@b.foot/]