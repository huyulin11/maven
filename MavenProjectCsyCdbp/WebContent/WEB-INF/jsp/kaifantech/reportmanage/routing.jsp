<div id='queryDiv'>
	<jsp:include page="pages/${param.type}.jsp"></jsp:include>
</div>
<div id='loadhtml'></div>
<script type="module">
	import "/s/buss/g/j/g.p.js";
	import "/s/buss/g/j/g.v.js";
</script>
<script>
	var type = '${param.type}';
</script>