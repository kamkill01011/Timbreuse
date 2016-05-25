<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
<head>
<meta charset="utf-8" />
<title>Manage élève</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>

<body>

	<h1 class="pageTitle">Gestion des élèves</h1>

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
				<table class="noBorder">
					<tr>
						<td>
							<table>
								<tr>
									<th><input type="checkbox" id="selectAllID" name="selectAllID" onClick='checkAll(document.forms.students,this)' <c:if test="${param.selectAllID != null}">checked="checked"</c:if> /></th>
									<th>Nom</th>
									<th>Prénom</th>
									<th>Différence</th>
									<th>Status</th>
									<th>Dernier timbrage (date)</th>
									<th>Dernier timbrage (heure)</th>
									<th>E-mail</th>
									<th>Logs</th>
								</tr>
								<c:forEach items="${studentsInClass}" var="i">
									<tr>
										<td><input type="checkbox" id="id${i.id}" name="id${i.id}" <c:if test="${selectedStudents.get(studentsInClass.indexOf(i))}">checked="checked"</c:if> /></td>
										<td>${i.lastname}</td>
										<td>${i.firstname}</td>
										<td>${i.timeDiff}</td>
										<td class="${i.status}" >${i.status}</td>
										<td>${i.lastCheckDate}</td>
										<td>${i.lastCheckTime}</td>
										<td>${i.email}</td>
										<td><input type="submit" id="logs${i.id}" name="logs${i.id}" value="Afficher les logs" class="sansLabelNoSpace" /></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<input type="text" name="modifyTimeDiff" placeholder="HH:MM:SS" size="32" maxlength="64" />
							<input type="submit" name="addTime" value="Ajouter du temps" class="sansLabelNoSpace" />
							<input type="submit" name="newStatus" value="Timbrer" class="sansLabelNoSpace" />
							<br />
						</td>
				</table>
			</form>
		</fieldset>
	</c:if>
	
	<c:if test="${!empty logs}">
		<fieldset>
		<legend>Logs</legend>
					<table>
						<tr>
							<th>Username</th>
							<th>Date</th>
							<th>Time</th>
							<th>Status</th>
						</tr>
						<c:forEach items="${logs}" var="i" end="30">
							<tr>
								<td>${i.username}</td>
								<td>${i.date}</td>
								<td>${i.time}</td>
								<td class="${i.status}" >${i.status}</td>
							</tr>
						</c:forEach>
					</table>
		</fieldset>
	</c:if>
	
	<fieldset>
		<legend>Ajouter un élève</legend>
		<form method="post" action="managestudents">
			<label for="addClass">Classe : </label>
			<input type="text" id="addClass" name="addClass" placeholder="YE-S1a" size="32" maxlength="64" />
			<br />
			<label for="addFirstname">Prénom : </label>
			<input type="text" id="addFirstname" name="addFirstname" placeholder="Jean" size="32" maxlength="64" />
			<br />
			<label for="addLastname">Nom : </label>
			<input type="text" id="addLastname" name="addLastname" placeholder="Dupond" size="32" maxlength="64" />
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
<%@include file="/WEB-INF/footer.jsp"%>