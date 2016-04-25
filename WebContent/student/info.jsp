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
		<legend>${sessionScope.currentStudent.lastname} ${sessionScope.currentStudent.firstname}</legend>
		<p>Différence : ${sessionScope.currentStudent.timeDiff}</p>
		<p>E-mail : ${sessionScope.currentStudent.email}</p>
	</fieldset>
	<form method="get" action="/Timbreuse/logout">
		<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
	</form>
    </body>
</html>