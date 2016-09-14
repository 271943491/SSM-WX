<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>

<title>第一张图表</title>

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
		var myChart = new JSChart('graph', 'line');
		myChart.setDataArray(myData, 'red');
<!--		myChart.patchMbString(); -->
		myChart.setFontFamily("Consolas");
		myChart.setBackgroundColor('#efe')
		myChart.setLineColor('#00CC00');
		myChart.setTitle('8月通顺店销售统计');
		myChart.setIntervalStartY(0);
		myChart.setIntervalEndY(<%=maxCount%>);
 		myChart.setIntervalStartX(0);
		myChart.setIntervalEndX(31); 
		myChart.setTitleColor('#000000');
 		myChart.setTitleFontSize(20); 
		myChart.setTextPaddingTop(30);
		myChart.setGridColor('#FF00FF');
		myChart.setAxisNameX('日期');
		myChart.setAxisNameY('订单数');
		myChart.setAxisColor('#FF00FF');
		myChart.setAxisNameFontSize(13);
		myChart.setAxisWidth(2);
		myChart.setAxisValuesFontSize(10);
		myChart.setAxisPaddingTop(60);
		myChart.setAxisPaddingLeft(60);
		myChart.setAxisPaddingBottom(50);
		myChart.setTextPaddingBottom(10);
		myChart.setTextPaddingLeft(15);
		myChart.setAxisValuesNumberX(32);
		myChart.setAxisValuesNumberY(19);
		/* myChart.setAxisAlignLastX(true); */
		<c:forEach var="item" items="${map}"> 
       		myChart.setTooltip([${item.key}, '${item.value}']);
	    </c:forEach>
		myChart.setSize(1000, 700);
		myChart.draw();
	</script>
</body>
</html>