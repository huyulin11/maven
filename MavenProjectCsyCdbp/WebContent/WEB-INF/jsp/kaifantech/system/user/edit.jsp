<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="frame">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/user/edit.js"></script>
</head>

<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/user/editEntity.shtml">
		<input type="hidden" class="form-control checkacc" value="${user.id}"
			name="userFormMap.id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">用户名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入用户名"
						value="${user.userName}" name="userFormMap.userName" id="userName"
						readonly="readonly">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入账号" value="${user.accountName}"
						name="userFormMap.accountName" id="accountName"
						readonly="readonly">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否重置密码</label>
				<div class="col-sm-9">
					<input type="checkbox" name="userFormMap.isResetPwd"
						id="isResetPwd" class="ace ace-switch ace-switch-5" value=0>
				</div>
			</div>
			<div class="hidden" id="pwdDiv">
				<div class="form-group">
					<label class="col-sm-3 control-label">密码</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="123456789"
							name="userFormMap.password" id="password">
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<c:if test="${isSelf==false}">
				<div id="selGroup"
					data-url="/role/selRole.shtml?roleFormMap.userId=${user.id}"></div>
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label">是否可用</label>
					<div class="col-sm-9">
						<select class="form-control jsSelect" data-patten="ISOK"
							name="userFormMap.locked" data-initval="${user.locked}"
							autocomplete="off"></select>
					</div>
				</div>
			</c:if>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入账号描述"
						value="${user.description}" name="userFormMap.description"
						id="description">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="module">
		import { gf } from "/s/buss/g/j/g.f.js";
		gf.onloadurl();
		var flag = 0;
		$("input[type='checkbox']#isResetPwd").on("click", function () {
			if (flag % 2 == 0) {
				$("div#pwdDiv").removeClass("hidden");
				$("#password").val(gf.randomString(9));
			} else {
				$("div#pwdDiv").addClass("hidden");
			}
			$(this).val(flag++);
		});

	</script>
</body>

</html>