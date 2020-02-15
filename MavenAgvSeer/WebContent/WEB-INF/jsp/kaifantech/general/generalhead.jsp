<%-- JSP and JAVA import --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- JS import --%>
<script src="/s/j/general/Column.js"></script>
<script src="/s/j/general/ColumnsDetail.js"></script>   
<script src="/s/j/general/FormMgr.js"></script>
<script src="/s/j/general/PageMgr.js"></script>
<script src="/s/j/general/SearchMgr.js"></script>
<script src="/s/j/general/DataMgr.js"></script>


<%-- CSS import --%>
<link rel="stylesheet" href="${ctx_css}/main.css?fv=22" 
	type="text/css"></link>
<link rel="stylesheet" href="${ctx_css}/general.css"
	type="text/css"></link>
<%-- --%>

<%-- OTHER import --%>
