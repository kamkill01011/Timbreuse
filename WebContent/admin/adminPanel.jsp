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
    
	
	<fieldset>
		<legend>Ajouter un enseigant</legend>
		<form method="post" action="admin">
			<label for="addFirstnameTeacher">Prénom : </label>
			<input type="text" id="addFirstnameTeacher" name="addFirstnameTeacher" value="" size="32" maxlength="64" />
			<br />
			<label for="addLastname">Nom : </label>
			<input type="text" id="addLastnameTeacher" name="addLastnameTeacher" value="" size="32" maxlength="64" />
			<br />
			<label for="addClasse">Classe(s) : </label>
			<input type="text" id="addClasseTeacher" name="addClasseTeacher" value="" size="32" maxlength="64" />
			<br />
				<input type="submit" name="addTeacher" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset>
		<legend>Supprimer un enseigant</legend>
		<form method="post" action="admin">
			<label for="deletFirstnameTeacger">Prénom : </label>
			<input type="text" id="deleteFirstnameTeacher" name="deleteFirstnameTeacher" value="" size="32" maxlength="64" />
			<br />
			<label for="deletLastname">Nom : </label>
			<input type="text" id="deleteLastnameTeacher" name="deleteLastnameTeacher" value="" size="32" maxlength="64" />
			<br />
			<input type="submit" name="deleteTeacher" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset class="adminField">
	<legend>Créer un admin</legend>
	<form method="post" action="admin">
	<label for="addFirstnameAdmin">Prénom : </label>
			<input type="text" id="addFirstnameAdmin" name="addFirstnameAdmin" value="" size="32" maxlength="64" />
			<br />
			<label for="addLastnameAdmin">Nom : </label>
			<input type="text" id="addLastnameAdmin" name="addLastnameAdmin" value="" size="32" maxlength="64" />
			<br />
			<input type="submit" name="addAdmin" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset class="adminField">
	<legend>Supprimer un admin</legend>
	<form method="post" action="admin">
	<label for="deleteFirstnameAdmin">Prénom : </label>
			<input type="text" id="deleteFirstnameAdmin" name="deleteFirstnameAdmin" value="" size="32" maxlength="64" />
			<br />
			<label for="deleteLastnameAdmin">Nom : </label>
			<input type="text" id="deleteLastnameAdmin" name="deleteLastnameAdmin" value="" size="32" maxlength="64" />
			<br />
			<input type="submit" name="deleteAdmin" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<form method="get" action="/Timbreuse/logout" class="DecoPwd">
		<input type="submit" name="logout" value="Déconnexion" class="logout" />
	</form>
	<form method="get" action="/Timbreuse/changepassword" class="DecoPwd">
		<input type="submit" value="Changer de mot de passe" class="changePassword" />
	</form>
    </body>
</html>