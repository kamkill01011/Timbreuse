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
	<p class="showStatus" style="color:#31B404">BONJOUR</p>
	<form method="get" action="display">
		<div class="center">
			<input type="password" autofocus name="tagText" class="tagText" size="8" maxlength="11" />
		</div>
	</form>
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>