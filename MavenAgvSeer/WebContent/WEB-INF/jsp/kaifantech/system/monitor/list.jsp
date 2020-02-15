<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/monitor/list.js"></script>
<div class="m-b-md">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="form-group">
			<label class="control-label"> <span
				class="font-thin v-middle">账号:</span></label> <input
				class="input-medium ui-autocomplete-input" id="accountName"
				name="serverInfoFormMap.accountName"> <a
				href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		</div>
	</form>
</div>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>
