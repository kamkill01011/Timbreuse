<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="refresh" content="5;display" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
	<h1 class="pageTitle">Timbreuse</h1>
	<c:choose>
		<c:when test="${taggedStudent.status=='DEP'}">
			<p class="showStatus" style="color: #FF0000">DEPART</p>
		</c:when>
		<c:when test="${taggedStudent.status=='ARR'}">
			<p class="showStatus" style="color: #31B404">ARRIVEE</p>
		</c:when>
		<c:otherwise>
			<p class="showStatus">ERREUR</p>
		</c:otherwise>
	</c:choose>
	<form method="get" action="display">
		<div class="center">
			<input type="password" autofocus name="tagText" class="tagText" size="8" maxlength="11" />
		</div>
	</form>
	<c:choose>
		<c:when test="${taggedStudent.status=='DEP' or taggedStudent.status=='ARR'}">
			<fieldset>
				<legend>${taggedStudent.firstname} ${taggedStudent.lastname}</legend>
				<p class="showStatus">${taggedStudent.timeDiff}</p>
			</fieldset>
		</c:when>
	</c:choose>
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>