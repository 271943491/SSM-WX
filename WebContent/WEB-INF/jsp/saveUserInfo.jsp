<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="text/javascript">
	function add() {
		var userinfo = {
			"id" : 2,
			"userName" : $("#username").val(),
			"password" : $("#pwd").val(),
			"age" : $("#age").val(),
		};
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			url : "/admin/addData.json",
			dataType : "json",
			data : JSON.stringify(userinfo),
			async : false,
			success : function(data) {
				if (data.state == 1) {
					alert("保存成功!");
				} else {
					alert(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<form id="form">
		<div>
			请输入用户名:<input id="username" type="text"><br>
			请输入密码:<input id="pwd" type="text"><br>
			请输入年龄:<input id="age" type="text"><br>
			<input type="submit" value="提交" onclick="add()">
		</div>
	</form>
</body>
</html>