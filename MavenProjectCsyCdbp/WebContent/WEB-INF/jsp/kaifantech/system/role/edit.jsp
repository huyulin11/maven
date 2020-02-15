<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/role/edit.js"></script>
<style type="text/css">
.col-sm-3 {
	width: 15%;
	float: left;
}

.col-sm-9 {
	width: 85%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/role/editEntity.shtml">
		<input type="hidden" class="form-control checkacc" value="${role.id}"
			name="roleFormMap.id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">角色名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkRole"
						placeholder="请输入角色名" name="roleFormMap.rolename" id="rolename"
						value="${role.rolename}" />
				</div>
			</div>
			<!-- 			<div class="line line-dashed line-lg pull-in"></div> -->
			<!-- 			<div class="form-group"> -->
			<!-- 				<label class="col-sm-3 control-label">roleKey</label> -->
			<!-- 				<div class="col-sm-9"> -->
			<!-- 					<input type="text" class="form-control checkacc" -->
			<!-- 						placeholder="请输入roleKey" name="roleFormMap.roleKey" id="roleKey" -->
			<!-- 						value=${role.roleKey}> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入角色描述"
						name="roleFormMap.description" id="description"
						value="${role.description }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否可用</label>
				<div class="col-sm-9">
					<select class="form-control jsSelect" data-patten="ISOK"
						name="role.state" data-initval="${role.state}" autocomplete="off"></select>
				</div>
			</div>
		</div>
		<div class="line line-dashed line-lg pull-in"></div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> </section>
	</form>
</body>
</html>