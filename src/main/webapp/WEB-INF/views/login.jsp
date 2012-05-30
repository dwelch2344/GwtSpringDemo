<%-- @taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>
<%-- @taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
	<head> 
		<link rel="stylesheet" type="text/css" media="screen, projection" href="resources/tsp.css" /> 
		<link rel="shortcut icon" type="image/ico" href="/favicon.ico" />
		<script type="text/javascript" src="resources/ixf/scripts/jquery-1.4.2.min.js"></script> 
	</head> 
	<body>
		<form action="doLogin" method="post" > 
			<label id="j_usernameLabel" for="j_username">Username: </label> 
			<input type="text"  name="j_username" id="j_username" tabindex="1" autocomplete="off" />
			<br/>
			<label id="j_passwordLabel" for="j_password">Password: </label></dt> 
			<input type="password" name="j_password" id="j_password" tabindex="2" autocomplete="off" />
			<br/>
			<input id="j_submitButton" type="submit" value="Submit"/> 					
		</form>	
	</body> 
</html>