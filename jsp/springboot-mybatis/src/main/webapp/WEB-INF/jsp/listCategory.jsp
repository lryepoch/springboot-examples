<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>springboot与mybatis综合应用</title>
</head>
<body>
	<h2 style="text-align:center">一个苍白的表格</h2>
	<table align='center' border='1' cellspacing='0' width=300px>
		<tr>
			<td>id</td>
			<td>name</td>
		</tr>		
		<c:forEach items="${list }" var="cs">
		<tr>
			<td>${cs.id }</td>
			<td>${cs.name }</td>
		</tr>
		</c:forEach>
	</table>
	<div align='center' border='1'>
    	<a href="/download"}>文件下载</a>
	</div>
</body>
</html>