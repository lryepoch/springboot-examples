<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style="width:500px;margin:20px auto;text-align: center">
	<h3>展示记录</h3>
	<table align='center' border='1' cellspacing='0'>
		<tr>
			<td>id</td>
			<td>name</td>
			<td>操作</td>
			<td>操作</td>
		</tr>
		<c:forEach items = "${page.list }" var = "c" varStatus="st">
			<tr>
				<td>${c.id }</td>
				<td>${c.name }</td>
				<td><a href="getCategory?id=${c.id }">修改</a></td>
				<td><a href="deleteCategory?id=${c.id }">删除</a></td>			
			</tr>
		</c:forEach>	
	</table>

	<br>
	<div>
       <a href="listCategory?start=1">[首  页]</a>
       <a href="?start=${page.pageNum-1}">[上一页]</a>
       <a href="?start=${page.pageNum+1}">[下一页]</a>
       <a href="?start=${page.pages}">[末  页]</a>
    </div>
    <div>当前第${page.pageNum }/${page.pages }页，共${page.total }条记录</div>
    
	<br>
	<h3>添加记录</h3>
	<form action="addCategory" method="post">
		name <input name="name" /><br>
		<button type="submit">提交</button>
		<input type="reset">
	</form>
	
</div>

</body>
</html>