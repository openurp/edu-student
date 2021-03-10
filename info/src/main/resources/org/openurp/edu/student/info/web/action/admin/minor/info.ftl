[#ftl]
[@b.head/]
[@b.toolbar title="辅修学生信息"]
	bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
	<tr>
		<td class="title" width="20%">学号:</td>
		<td class="content">${minor.std.user.code}</td>
		<td class="title" width="20%">姓名:</td>
		<td class="content">${minor.std.user.name}</td>
	</tr>
	<tr>
		<td class="title" width="20%">主修学校:</td>
		<td class="content">${(minor.school.name)!}</td>
	</tr>
	<tr>
		<td class="title" width="20%">主修专业:</td>
		<td class="content">${minor.majorName!}</td>
		<td class="title" width="20%">主修专业英文名:</td>
		<td class="content">${minor.enMajorName!}</td>
	</tr>
	<tr>
		<td class="title" width="20%">主修专业学科门类:</td>
		<td class="content">${(minor.majorCategory.name)!}</td>
	</tr>
</table>

[@b.foot/]
