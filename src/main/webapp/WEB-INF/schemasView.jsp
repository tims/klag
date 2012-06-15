<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schemas</title>
</head>
<body>
<h2>Create Schema</h2>
<form method="POST" action="/schemas/submit ">
  <p>Id:
    <input name="id" style="width: 100px;" type="text" value="users">
  </p>
  <p>Fields: 
    <input name="fields" style="width: 400px;" type="text" value="date:string,response_time:int">
  </p>
  <input type="submit" name="submit" value="create" />
  <input type="submit" name="submit" value="delete" />
</form>


<h2>Current Schemas</h1>
  <table border="1">
    <tr>
      <td>Schema Id</td>
      <td>Schema Fields</td>
    </tr>
  <c:forEach var="schema" items="${schemas}">
    <tr>
      <td>${schema.id}</td>
      <c:forEach var="field" items="${schema.fields}"><td>${field.name}:${field.type.name}</td></c:forEach>
    </tr>
  </c:forEach>
  </table>
</body>
</html>
