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
    
    <h1>Gestion des élèves</h1>
    
    <form method="post" action="managestudents">
		<input type="submit" name="research" value="Recherche" />
		<input type="text" name="researchLastname" value="" size="32" maxlength="64" />
	</form>
    <br/>
    <fieldset>
		<legend>${researchStudent.lastname} ${researchStudent.firstname}</legend>
		<p>Différence : ${researchStudent.timeDiff}</p>
		<p>E-mail : ${researchStudent.email}</p>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Liste des classes</legend>
		<form method="post" action="managestudents">
			<input type="submit" name="classe" value="YE-Test" class="sansLabel" />
			<br/>
			<input type="submit" name="classe" value="YE-S1a" class="sansLabel" />
			<br/>
			<p>${param.classe}</p>
			
			<p>${studentsTest[0].getLastname()}</p>
			<c:forEach items="${studentsTest}" var="i">
				<p>${studentsTest[i].lastname}</p>
			</c:forEach>
		</form>
	</fieldset>
	<fieldset>
		<legend>Ajouter un élève</legend>
		<form method="post" action="managestudents">
			<label for="addClass">Classe : </label>
			<input type="text" id="addClass" name="addClass" value="" size="32" maxlength="64" />
			<br/>
			<label for="addFirstname">Prénom : </label>
			<input type="text" id="addFirstname" name="addFirstname" value="" size="32" maxlength="64" />
			<br/>
			<label for="addLastname">Nom : </label>
			<input type="text" id="addLastname" name="addLastname" value="" size="32" maxlength="64" />
			<br/>
			<input type="submit" name="add" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Supprimer un élève</legend>
		<form method="post" action="managestudents">
			<label for="deletFirstname">Prénom : </label>
			<input type="text" id="deletFirstname" name="deletFirstname" value="" size="32" maxlength="64" />
			<br/>
			<label for="deletLastname">Nom : </label>
			<input type="text" id="deletLastname" name="deletLastname" value="" size="32" maxlength="64" />
			<br/>
			<input type="submit" name="delete" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Modifier un élève</legend>
		<form method="post" action="managestudents">
			<input type="text" name="modifyTimeDiff" value="" size="32" maxlength="64" />
			<input type="submit" name="modify" value="Modifier la différence de temps" />
		</form>
	</fieldset>
    <form method="get" action="/Timbreuse/logout">
		<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
	</form>
    </body>
</html>