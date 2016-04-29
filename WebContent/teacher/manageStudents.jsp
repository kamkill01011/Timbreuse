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

	<h1 class="pageTitle">Gestion des élèves</h1>

	<form method="post" action="managestudents">
		<input type="submit" name="research" value="Recherche" class="sansLabelNoSpace"/>
		<input type="text" name="researchLastname" value="" size="32" maxlength="64" />
	</form>
	<br />
	<fieldset>
		<legend>${researchStudent.lastname} ${researchStudent.firstname}</legend>
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
						<c:when test="${i == selectedClasse}">
							<option value="${i}" selected="selected">${i}</option>
						</c:when>
						<c:otherwise>
							<option value="${i}">${i}</option>
						</c:otherwise>
					</c:choose>
					<br />
				</c:forEach>
			</select>
		</form>
	</fieldset>
	<c:if test="${!empty studentsInClass}">
		<fieldset>
			<legend>${selectedClasse}</legend>
			<form method="post" action="managestudents" id="students">
				<table>
					<tr>
						<td>
							<table>
								<tr>
									<th><input type="checkbox" id="selectAllID" name="selectAllID" onClick='checkAll(document.forms.students,this)' /></th> <!-- checked="${param.selectAllID}" -->
									<th>Nom</th>
									<th>Prénom</th>
									<th>Différence</th>
									<th>Status</th>
									<th>Dernier timbrage</th>
									<th>E-mail</th>
								</tr>
								<c:forEach items="${studentsInClass}" var="i">
									<tr>
										<td><input type="checkbox" id="id${i.id}" name="id${i.id}" <c:if test="${selectedStudents.get(studentsInClass.indexOf(i))}">checked="checked"</c:if> /></td>
										<td>${i.lastname}</td>
										<td>${i.firstname}</td>
										<td>${i.timeDiff}</td>
										<td>${i.status}</td>
										<td>${i.lastCheck}</td>
										<td>${i.email}</td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<input type="text" name="modifyTimeDiff" value="HH:MM:SS" size="32" maxlength="64" />
							<input type="submit" name="addTime" value="Ajouter du temps" />
							<br />
						</td>
				</table>
			</form>
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
			<input type="submit" name="modify" value="Modifier la différence de temps" class="sansLabelNoSpace"/>
		</form>
	</fieldset>
	<form method="get" action="/Timbreuse/logout" class="DecoPwd">
		<input type="submit" name="logout" value="Déconnexion" class="logout" />
	</form>
	<form method="get" action="/Timbreuse/changepassword" class="DecoPwd">
		<input type="submit" value="Changer de mot de passe" class="changePassword" />
	</form>

	<!--http://p2p.wrox.com/asp-forms/18230-how-put-check-all-checkbox.html-->
	<script type='text/javascript'>
		function checkAll(form, cbox) {
			var ct;
			if (cbox.checked == true)
				ct = false;
			else if (cbox.checked == false)
				ct = true;
			for (var i = 1; i < form.length; i++) {
				if (form[i].type == 'checkbox')
					form[i].checked = cbox.checked;
			}
		}
	</script>

</body>
</html>