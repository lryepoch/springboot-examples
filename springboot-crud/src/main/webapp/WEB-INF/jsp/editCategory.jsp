<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="updateCategory" method="post">
		<input name="name" value="${category.name }"/><br>
		<input name="id" type="hidden" value="${category.id}">
		<button type="submit">提交</button>
	</form>

</body>
</html>