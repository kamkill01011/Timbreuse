<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Mot De Passe Oublié</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>

<body>
	<h1 class="pageTitle">Mot de passe oublié</h1>
	<form method="post" action="passwordforgotten">
		<fieldset>
			<legend></legend>
			<label>Nom d'utilisateur <span class="requis">*</span></label>
			<input type="text" name="usernamePasswordForgotten" value="" size="20" maxlength="60"/>
			<input type="submit" name="sendNewPassword" value="Recevoir nouveau mot de passe" class="sansLabelNoSpace" />
		</fieldset>
	</form>
</body>

</html>
<%@include file="footer.jsp"%>
