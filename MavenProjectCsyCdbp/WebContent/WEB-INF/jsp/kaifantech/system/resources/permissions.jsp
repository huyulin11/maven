<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
</head>
<style type="text/css">
#mytable {
	max-width: 660px;
	width: 100%;
	padding: 0;
	margin: 0 auto;
}

caption {
	padding: 0 0 5px 0;
	width: 660px;
	font: italic 13px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	text-align: right;
}

th {
	font: bold 13px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #4f6b72;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	letter-spacing: 2px;
	text-transform: uppercase;
	text-align: left;
	padding: 6px 6px 6px 12px;
}

th.nobg {
	border-top: 0;
	border-left: 0;
	border-right: 1px solid #C1DAD7;
	system: none;
}

#mytable td {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	system: #fff;
	font-size: 11px;
	padding: 6px 6px 6px 12px;
	color: #4f6b72;
}

.calculatedfun_bb {
	border-bottom: 1px solid #C1DAD7;
}

td.alt {
	system: #F5FAFA;
	color: #797268;
}

th.spec {
	border-left: 1px solid #C1DAD7;
	border-top: 0;
	system: #fff;
	font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
}

th.specalt {
	border-left: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	system: #f5fafa;
	font: bold 13px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #797268;
}
/*---------for IE 5.x bug*/
html>body td {
	font-size: 13px;
}

.doc-buttons {
	text-align: center;
	position: fixed;
	width: 100%;
	top: 96%;
}
</style>
<body>
	<form method="post" id="from" name="form">
		<input id='userId' name="userId" type="hidden" value="${param.userId}">
		<input id='roleId' name="roleId" type="hidden" value="${param.roleId}">
		<table id="mytable" cellspacing="0"
			summary="The technical specifications of the Apple PowerMac G5 series">
			<c:forEach items="${permissions}" var="k">
				<tr>
					<th scope="row" abbr="L2 Cache" class="specalt" title="一级菜单"><input
						type="checkbox" name="resId" id="menu" _key="menu_${k.id}"
						class="smenu" value="${k.id}"> ${k.name}</th>
					<th scope="row" abbr="L2 Cache" class="specalt" title="二级菜单">
						<table id="mytable" cellspacing="0"
							summary="The technical specifications of the Apple PowerMac G5 series"
							style="width: 100%; height: 100%;">
							<c:forEach items="${k.children}" var="kc">
								<tr>
									<th scope="row" abbr="L2 Cache" class="specalt"><input
										type="checkbox" name="resId" id="menu" _key="menu_1_${k.id}"
										_key_1="menu_1_1_${kc.id}" class="menu_1" data-kid="${k.id}"
										value="${kc.id}"> ${kc.name}</th>
									<c:if test="${not empty kc.children}">
										<th title="按扭">
											<table id="mytable" cellspacing="0"
												summary="The technical specifications of the Apple PowerMac G5 series"
												style="width: 100%; height: 100%;">
												<c:forEach items="${kc.children}" var="kcc">
													<tr>
														<th scope="row" abbr="L2 Cache" class="specalt"><input
															type="checkbox" name="resId" id="menu" data-kid="${k.id}"
															data-kcid="${kc.id}" _key="menu_1_1_${k.id}"
															_key_2="menu_1_1_${kc.id}" value="${kcc.id}">
															${kcc.name}</th>
													</tr>
												</c:forEach>
											</table>
										</th>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</th>
				</tr>
			</c:forEach>
		</table>
		<br>
		<div class="doc-buttons">
			<a href="#" class="btn btn-s-md btn-success btn-rounded" id="sub">保存</a>
			<a href="#" class="btn btn-s-md btn-info btn-rounded" id="closeWin">关闭</a>
		</div>
		<br>
	</form>
</body>
<script type="module" src="/s/j/system/resources/permission.js"></script>
</html>