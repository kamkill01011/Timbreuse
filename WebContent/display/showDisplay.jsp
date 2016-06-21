<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
	<h1 class="pageTitle">Timbreuse</h1>
	<c:choose>
		<c:when test="${taggedStudent.status=='DEP'}">
			<p class="showStatus" style="color:#FF0000">DEPART</p>
		</c:when>
		<c:when test="${taggedStudent.status=='ARR'}">
			<p class="showStatus" style="color:#31B404">ARRIVEE</p>
		</c:when>
		<c:when test="${taggedStudent.status=='INI'}">
			<p class="showStatus" style="color:#31B404">BONJOUR</p>
		</c:when>
		<c:otherwise>
			<p class="showStatus">ERREUR</p>
		</c:otherwise>
	</c:choose>
	<fieldset>
		<legend></legend>
		<p>Prénom: ${taggedStudent.firstname}</p>
		<p>Nom: ${taggedStudent.lastname}</p>
		<p>Différence: ${taggedStudent.timeDiff}</p>
	</fieldset>
	<form method="post" action="showDisplay">
		<div class="center">
			<input type="password" autofocus name="tagText" class="tagText"
				size="8" maxlength="11" />
		</div>
	</form>
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>