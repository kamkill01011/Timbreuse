<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Informations</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    <h1>Admin panel</h1>
    <form method="get" action="/Timbreuse/logout">
		<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
	</form>
	<form method="get" action="/Timbreuse/changepassword">
		<input type="submit" value="Changer de mot de passe" class="sansLabel" />
	</form>
    </body>
</html>