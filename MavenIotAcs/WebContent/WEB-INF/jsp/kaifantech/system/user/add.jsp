<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="frame">

<head>
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
</head>

<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/user/addEntity.shtml">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<div class="col-sm-3">
					<label class="control-label">用户名</label>
				</div>
				<div class="col-sm-9">
					<input type="text" class="form-control associating-input"
						autocomplete="off" placeholder="请输入用户名"
						name="userFormMap.userName" id="userName">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" autocomplete="off"
						placeholder="请输入账号" name="userFormMap.accountName"
						id="accountName">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label"></label>
				<div class="col-sm-9" style="color: red;">默认密码为（随机生成）:</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">密码</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="123456789"
						name="userFormMap.password" id="password" value="">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="selRole" data-url="/role/selRole.shtml"></div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否可用</label>
				<div class="col-sm-9">
					<select class="form-control jsSelect" data-patten="ISOK"
						data-initval="1" name="userFormMap.locked" autocomplete="off"></select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入账号描述"
						name="userFormMap.description" id="description">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> </section>
	</form>
	<script type="text/javascript"
		src="${ctx_static}/notebook/notebook_files/bootstrap-filestyle.min.js"></script>
	<script type="module" src="/s/j/system/user/add.js"></script>
</body>

</html>