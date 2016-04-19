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
		<input type="submit" value="Recherche" />
		<input type="text" id="researchLastname" name="researchLastname" value="" size="32" maxlength="64" />
	</form>
    <br/>
    <fieldset>
		<legend>${researchStudent.lastname} ${researchStudent.firstname}</legend>
		<p>Différence : ${researchStudent.timeDiff}</p>
		<p>E-mail : ${researchStudent.email}</p>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Ajouter un élève</legend>
		<form method="post" action="managestudents">
			<label>Classe : </label>
			<input type="text" id="addClass" name="addClass" value="" size="32" maxlength="64" />
			<br/>
			<label>Prénom : </label>
			<input type="text" id="addFirstname" name="addFirstname" value="" size="32" maxlength="64" />
			<br/>
			<label>Nom : </label>
			<input type="text" id="addLastname" name="addLastname" value="" size="32" maxlength="64" />
			<br/>
			<label>E-mail : </label>
			<input type="text" id="addEmail" name="addEmail" value="" size="32" maxlength="64" />
			<br/>
			<input type="submit" value="Ajuter" class="sansLabel" />
		</form>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Supprimer un élève</legend>
		<form method="post" action="managestudents">
			<input type="text" id="deletByLastname" name="deletByLastname" value="" size="32" maxlength="64" />
			<input type="submit" value="Supprimer" />
		</form>
	</fieldset>
	<br/>
	<fieldset>
		<legend>Modifier un élève</legend>
		<form method="post" action="managestudents">
			<input type="text" id="modifyTimeDiff" name="modifyTimeDiff" value="" size="32" maxlength="64" />
			<input type="submit" value="Modifier la différence de temps" />
		</form>
	</fieldset>
    </body>
</html>
