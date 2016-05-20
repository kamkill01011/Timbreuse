<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link type="text/css" rel="stylesheet" href="form.css" />
<html>
<head>
<ul class="menu">
	<li><a class="homeActive" href="/Timbreuse/connecting.jsp">CPNV</a></li>
	<c:if test="${sessionScope.userSession.permissionLevel == 2}"><li><a href="/Timbreuse/setholydays">Paramétrer les congés (IF)</a></li></c:if>
	<li><a href="/Timbreuse/changepassword">Changer Mot de Passe</a></li>
	<li><a href="/Timbreuse/logout">Déconnexion</a></li>
</ul>
</head>
<body>
</body>
</html>