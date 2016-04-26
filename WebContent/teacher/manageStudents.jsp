<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
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
		<input type="submit" name="research" value="Recherche" /> <input
			type="text" name="researchLastname" value="" size="32" maxlength="64" />
	</form>
	<br />
	<fieldset>
		<legend>${researchStudent.lastname}
			${researchStudent.firstname}</legend>
		<p>Différence : ${researchStudent.timeDiff}</p>
		<p>E-mail : ${researchStudent.email}</p>
	</fieldset>
	<br />
	<fieldset>
		<legend>Liste des classes</legend>
		<form method="post" action="managestudents">
			<select name="classe" id="classe" tabindex="30" onchange='if(this.value != 0) { this.form.submit(); }'>
				<c:if test="${selectedClasse == null}">
					<option value="0">---</option>
				</c:if>
				<c:forEach items="${currentTeacher.classe}" var="i">
					<c:choose>
						<c:when test="${i == classe}">
							<option value="${i}" selected="selected">${i}</option>
						</c:when>
						<c:otherwise>
							<option value="${i}">${i}</option>
						</c:otherwise>
					</c:choose>
					<br />
				</c:forEach>
			</select>
			<p>${param.classe}</p>
		</form>
	</fieldset>
	<c:if test="${!empty param.classe}">
		<fieldset>
			<legend>${param.classe}</legend>
			<table>
  				<tr>
    				<th>Nom</th>
    				<th>Prénom</th>
    				<th>E-mail</th>
  				</tr>
				<c:forEach items="${Test}" var="i">
  					<tr>
    					<td>${i.lastname}</td>
    					<td>${i.firstname}</td>
    					<td>${i.email}</td>
  					</tr>
				</c:forEach>
			</table>
		</fieldset>
	</c:if>
	<fieldset>
		<legend>Ajouter un élève</legend>
		<form method="post" action="managestudents">
			<label for="addClass">Classe : </label>
			<input type="text" id="addClass" name="addClass" value="" size="32" maxlength="64" />
			<br />
			<label for="addFirstname">Prénom : </label>
			<input type="text" id="addFirstname" name="addFirstname" value="" size="32" maxlength="64" />
			<br />
			<label for="addLastname">Nom : </label>
			<input type="text" id="addLastname" name="addLastname" value="" size="32" maxlength="64" />
			<br />
				<input type="submit" name="add" value="Ajouter" class="sansLabel" />
		</form>
	</fieldset>
	<br />
	<fieldset>
		<legend>Supprimer un élève</legend>
		<form method="post" action="managestudents">
			<label for="deletFirstname">Prénom : </label>
			<input type="text" id="deletFirstname" name="deletFirstname" value="" size="32" maxlength="64" />
			<br />
			<label for="deletLastname">Nom : </label>
			<input type="text" id="deletLastname" name="deletLastname" value="" size="32" maxlength="64" />
			<br />
			<input type="submit" name="delete" value="Supprimer" class="sansLabel" />
		</form>
	</fieldset>
	<br />
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
	<form method="get" action="/Timbreuse/changepassword">
		<input type="submit" value="Changer de mot de passe" class="sansLabel" />
	</form>
</body>
</html>