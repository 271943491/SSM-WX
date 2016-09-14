<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>

<title>第二张图表</title>

<script type="text/javascript" src="/js/jscharts.js"></script>

</head>
<body>

<div id="graph">Loading graph...</div>

<script type="text/javascript">
	<%
		String data = (String)request.getAttribute("data");
	%>

	<%
		int maxCount = (Integer)request.getAttribute("maxCount") + 1;
	%>
	var myData = new Array(<%=data%>);
	var myChart = new JSChart('graph', 'bar');
	myChart.setDataArray(myData);
	myChart.setTitle('8月销售报表');
	myChart.setTitleColor('#000000');
	myChart.setTitleFontSize(20); 
	myChart.setAxisNameX('店名');
	myChart.setAxisNameY('销售数目');
	myChart.setAxisColor('#c6c6c6');
	myChart.setAxisWidth(1);
	myChart.setAxisNameColor('#9a9a9a');
	myChart.setAxisValuesColor('#939393');
	myChart.setAxisPaddingTop(60);
	myChart.setAxisPaddingBottom(60);
	myChart.setTextPaddingBottom(20);
	myChart.setTextPaddingLeft(10);
	myChart.setAxisNameFontSize(12);
	myChart.setAxisValuesFontSize(9);
	myChart.setAxisPaddingLeft(100);
	myChart.setBarValuesFontSize(9);
	myChart.setBarColor('#44A622');
	myChart.setBarBorderWidth(0);
	myChart.setBarSpacingRatio(20);
	myChart.setBarValuesColor('#737373');
	myChart.setGrid(false);
	myChart.setSize(1000, 900);
	myChart.setAxisReversed(true);
	myChart.draw();
</script>
</body>
</html>