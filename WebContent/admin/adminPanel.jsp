<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
<head>
<meta charset="utf-8" />
<title>Page Admin</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>

<body>
	<h1 class="pageTitle">Admin Panel</h1>


	<fieldset>
		<legend>Ajouter un enseigant</legend>
		<form method="post" action="admin">
			<label for="addFirstnameTeacher">Prénom : </label> <input type="text" id="addFirstnameTeacher" name="addFirstnameTeacher" placeholder="Jean" size="32" maxlength="64" /> <br /> <label for="addLastname">Nom : </label> <input type="text" id="addLastnameTeacher" name="addLastnameTeacher" placeholder="Dupond" size="32" maxlength="64" /> <br />
			<label for="addClasse">Classe(s) : </label> <input type="text" id="addClasseTeacher" name="addClasseTeacher" placeholder="YE-S1a, YE-S1b, ..." size="32" maxlength="64" /> <br /> <input type="submit" name="addTeacher" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset>
		<legend>Supprimer un enseigant</legend>
		<form method="post" action="admin">
			<label for="deletFirstnameTeacger">Prénom : </label> <input type="text" id="deleteFirstnameTeacher" name="deleteFirstnameTeacher" value="" size="32" maxlength="64" />
			<br /> <label for="deletLastname">Nom : </label> <input type="text" id="deleteLastnameTeacher" name="deleteLastnameTeacher" value="" size="32" maxlength="64" /> <br /> <input type="submit" name="deleteTeacher" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	
	<fieldset>
		<legend>Liste des enseigants</legend>
		<form method="post" action="admin">
			<table>
				<tr>
					<th>Nom</th>
					<th>Prénom</th>
					<th>E-mail</th>
					<th>Classes</th>
					<th>Actions</th>
				</tr>
				<c:forEach items="${teachers}" var="i">
					<tr>
						<td>${i.lastname}</td>
						<td>${i.firstname}</td>
						<td>${i.email}</td>
						<td><input type="text" id="classes" name="classes" value="${i.classe}" placeholder="YE-S1a, YE-S1b, ..." size="32" maxlength="64" /></td>
						<td><input type="submit" id="${i.id}" name="${i.id}" value="Changer les classes" class="sansLabelNoSpace" /></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</fieldset>
	<br />
	
	<fieldset class="adminField">
		<legend>Créer un admin</legend>
		<form method="post" action="admin">
			<label for="addFirstnameAdmin">Prénom : </label> <input type="text"
				id="addFirstnameAdmin" name="addFirstnameAdmin" value="" size="32"
				maxlength="64" /> <br /> <label for="addLastnameAdmin">Nom
				: </label> <input type="text" id="addLastnameAdmin" name="addLastnameAdmin"
				value="" size="32" maxlength="64" /> <br /> <input type="submit"
				name="addAdmin" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset class="adminField">
		<legend>Supprimer un admin</legend>
		<form method="post" action="admin">
			<label for="deleteFirstnameAdmin">Prénom : </label> <input
				type="text" id="deleteFirstnameAdmin" name="deleteFirstnameAdmin"
				value="" size="32" maxlength="64" /> <br /> <label
				for="deleteLastnameAdmin">Nom : </label> <input type="text"
				id="deleteLastnameAdmin" name="deleteLastnameAdmin" value=""
				size="32" maxlength="64" /> <br /> <input type="submit"
				name="deleteAdmin" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>	
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>