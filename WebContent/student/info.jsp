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
    
    <fieldset>
		<legend>${sessionScope.userSession.lastname} ${sessionScope.userSession.firstname}</legend>
		<p>Différence : ${sessionScope.userSession.timeDiff}</p>
		<p>E-mail : ${sessionScope.userSession.email}</p>
		<form method="get" action="/Timbreuse/logout">
			<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
		</form>
	</fieldset>
    </body>
</html>