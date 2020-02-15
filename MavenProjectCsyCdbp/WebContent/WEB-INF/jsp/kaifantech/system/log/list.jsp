<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/log/list.js"></script>

<header class="panel-heading">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="search-group">
			<label> <span>用户名:</span> <input id="userName"
				name="logFormMap.userName">
			</label> <label> <span>账号:</span> <input id="accountName"
				name="logFormMap.accountName">
			</label> <label> <span>模块:</span> <input id="module"
				name="logFormMap.module">
			</label> <label> <span>方法:</span> <input id="methods"
				name="logFormMap.methods">
			</label> <label> <span>IP:</span> <input id="userIP"
				name="logFormMap.userIP">
			</label> <label> <a href="javascript:void(0)" class="btn btn-default"
				id="search">查询</a></label>
		</div>
	</form>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>
