<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html lang="en" class="app">

<head>
<title data-autokey='client.shortname' data-autofill></title>
<%@include file="/WEB-INF/jsp/common/common.jspf"%>
<script src="/s/notebook/notebook_files/app.v1.js"></script>
<script src="/s/notebook/notebook_files/app.plugin.js"></script>
</head>

<body style="display: none;">
	<section class="vbox">
		<header class="bg-dark dk header navbar navbar-fixed-top-xs">
			<div class="navbar-header aside-md">
				<a class="btn btn-link visible-xs"
					data-toggle="class:nav-off-screen,open" data-target="#nav,html">
					<i class="fa fa-bars"></i>
				</a> <a class="navbar-brand" data-tip="首页,首页,/s/buss/g/h/welcome.html"
					style="width: 300px"><img
					src="/s/notebook/notebook_files/logo.png" class="m-r-sm"><span
					data-autofill data-autokey='client.shortname'></span><span>-</span><span
					data-autofill data-autokey='projectName'></span></a> <a
					class="btn btn-link visible-xs" data-toggle="dropdown"
					data-target=".nav-user"> <i class="fa fa-cog"></i>
				</a>
			</div>
			<ul class="nav navbar-nav navbar-right m-n hidden-xs nav-user">
				<li class="hidden-xs"><a href="index.shtml#"
					class="dropdown-toggle dk" data-toggle="dropdown"> <i
						class="fa fa-bell"></i> <span
						class="badge badge-sm up bg-danger m-l-n-sm count"
						style="display: inline-block;">3</span>
				</a>
					<section class="dropdown-menu aside-xl">
						<section class="panel bg-white">
							<header class="panel-heading b-light bg-light">
								<strong>You have <span class="count"
									style="display: inline;">3</span> notifications
								</strong>
							</header>
							<div class="list-group list-group-alt animated fadeInRight">
								<a href="index.shtml#" class="media list-group-item"
									style="display: block;"><span
									class="pull-left thumb-sm text-center"><i
										class="fa fa-envelope-o fa-2x text-success"></i></span><span
									class="media-body block m-b-none">Sophi sent you a email<br>
										<small class="text-muted">1 minutes ago</small></span></a> <a
									href="index.shtml#" class="media list-group-item"> <span
									class="pull-left thumb-sm"> <img
										src="/s/notebook/notebook_files/avatar.jpg" alt="John said"
										class="img-circle">
								</span> <span class="media-body block m-b-none"> Use awesome
										animate.css<br> <small class="text-muted">10
											minutes ago</small>
								</span>
								</a> <a href="index.shtml#" class="media list-group-item"> <span
									class="media-body block m-b-none"> 1.0 initial released<br>
										<small class="text-muted">1 hour ago</small>
								</span>
								</a>
							</div>
							<footer class="panel-footer text-sm">
								<a href="index.shtml#" class="pull-right"><i
									class="fa fa-cog"></i></a> <a href="index.shtml#notes"
									data-toggle="class:show animated fadeInRight">See all the
									notifications</a>
							</footer>
						</section>
					</section></li>
				<li class="dropdown hidden-xs"><a href="index.shtml#"
					class="dropdown-toggle dker" data-toggle="dropdown"><i
						class="fa fa-fw fa-search"></i></a>
					<section class="dropdown-menu aside-xl animated fadeInUp">
						<section class="panel bg-white">
							<form role="search">
								<div class="form-group wrapper m-b-none">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Search">
										<span class="input-group-btn">
											<button type="submit" class="btn btn-info btn-icon">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</div>
							</form>
						</section>
					</section></li>
				<li class="dropdown"><a href="index.shtml#"
					class="dropdown-toggle" data-toggle="dropdown"> <span
						class="thumb-sm avatar pull-left"> <img
							src="/s/notebook/notebook_files/avatar.jpg">
					</span> ${sessionScope.userSession.userName} <b class="caret"></b>
				</a>
					<ul class="dropdown-menu animated fadeInRight">
						<span class="arrow top"></span>
						<li><a id="editUI" href="#">个人信息</a></li>
						<li><a href="tips.shtml"> <span
								class="badge bg-danger pull-right">3</span>提醒
						</a></li>
						<li><a href="docs.shtml">Help</a></li>
						<li class="divider"></li>
						<li><a href="logout.shtml">Logout</a></li>
					</ul></li>
			</ul>
		</header>
		<section>
			<section class="hbox stretch">
				<!-- .aside -->
				<aside class="bg-dark lter aside-md hidden-print hidden-xs" id="nav">
					<section class="vbox">
						<section class="w-f scrollable">
							<div class="slim-scroll" data-height="auto"
								data-disable-fade-out="true" data-distance="0" data-size="5px"
								data-color="#333333">
								<nav class="nav-primary hidden-xs">
									<ul id="menuContainer" class="nav">
<%-- 										<jsp:include page="manager/nav.jsp"></jsp:include> --%>
									</ul>
								</nav>
							</div>
						</section>
					</section>
				</aside>
				<!-- /.aside -->
				<section id="content">
					<section id="id_vbox" class="vbox">
						<ul class="breadcrumb no-border no-radius b-b b-light" id="topli">
						</ul>
						<section class="scrollable" id="frames"
							style="margin-top: 30px; background-color: #FFF;"></section>
					</section>
				</section>
				<aside class="bg-light lter b-l aside-md hide" id="notes">
					<div class="wrapper">Notification</div>
				</aside>
			</section>
		</section>
	</section>

	<script type="module">
		import "/s/buss/g/j/manager.js"
	</script>

</body>

</html>