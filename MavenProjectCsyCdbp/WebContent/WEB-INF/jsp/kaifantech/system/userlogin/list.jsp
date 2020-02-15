<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/userlogin/list.js"></script>

<header class="panel-heading">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="search-group">
			<label> <span>账号:</span> <input id="accountName"
				name="userLoginFormMap.accountName">
			</label> <label> <a href="javascript:void(0)" class="btn btn-default"
				id="search">查询</a></label>
		</div>
	</form>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>
