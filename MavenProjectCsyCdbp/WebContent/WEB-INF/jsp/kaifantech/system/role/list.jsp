<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script type="module" src="/s/j/system/role/list.js"></script>
<header class="panel-heading">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="form-group">
			<label class="control-label"> <span
				class="font-thin v-middle">角色名:</span></label> <input
				class="input-medium ui-autocomplete-input" id="rolename"
				name="roleFormMap.rolename"> <a href="javascript:void(0)"
				class="btn btn-default" id="search">查询</a>
		</div>
	</form>
</header>
<div class="doc-buttons">
	<c:forEach items="${res}" var="key">
		${key.description}
	</c:forEach>
</div>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>
