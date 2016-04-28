<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Page Admin</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    <h1>Admin Panel</h1>
    <form method="get" action="/Timbreuse/logout">
		<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
	</form>
	<form method="get" action="/Timbreuse/changepassword">
		<input type="submit" value="Changer de mot de passe" class="sansLabel" />
	</form>
	<fieldset>
		<legend>Ajouter un enseigant</legend>
		<form method="post" action="admin">
			<label for="addFirstname">Prénom : </label>
			<input type="text" id="addFirstname" name="addFirstname" value="" size="32" maxlength="64" />
			<br />
			<label for="addLastname">Nom : </label>
			<input type="text" id="addLastname" name="addLastname" value="" size="32" maxlength="64" />
			<br />
			<label for="addClasse">Classe(s) : </label>
			<input type="text" id="addClasse" name="addClasse" value="" size="32" maxlength="64" />
			<br />
				<input type="submit" name="add" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset>
		<legend>Supprimer un enseigant</legend>
		<form method="post" action="admin">
			<label for="deletFirstname">Prénom : </label>
			<input type="text" id="deleteFirstname" name="deleteFirstname" value="" size="32" maxlength="64" />
			<br />
			<label for="deletLastname">Nom : </label>
			<input type="text" id="deleteLastname" name="deleteLastname" value="" size="32" maxlength="64" />
			<br />
			<input type="submit" name="delete" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br />
    </body>
</html>