<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Manage élève</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    <h1>Management des élèves</h1>
    
    <form method="post" action="managestudents">
		<input type="submit" value="Recherche" class="sansLabel" />
		<input type="text" id="research" name="research" value="" size="32" maxlength="64" />
	</form>
    
    <p>Nom : ${researchStudent.lastname}</p>
    <p>Différence : ${researchStudent.timeDiff}</p>
    
    </body>
</html>
