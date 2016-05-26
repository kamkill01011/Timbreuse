<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
    <head>
        <title>Informations</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    <h1 class="pageTitle">Informations Personnelles</h1>
    <form method="post" action="info">
    	<input type="submit" name="newStatus" value="Timbrer" class="timbrage" />
    </form>
    <fieldset>
		<legend>${currentStudent.lastname} ${currentStudent.firstname}</legend>
		<p>Différence : ${currentStudent.timeDiff}</p>
		<p>Temps effectué aujourd'hui : ${currentStudent.todayTime}</p>
		<p>Status : ${currentStudent.status}</p>
		<p>Dernier timbrage : ${currentStudent.lastCheckDate} / ${currentStudent.lastCheckTime}</p>
		<p>Classe : ${currentStudent.classe}</p>
		<p>E-mail : ${currentStudent.email}</p>
		<p>Date d'inscription : ${currentStudent.startDate}</p>
	</fieldset>
	<fieldset>
		<legend>Horaire</legend>
		<p>Lundi : ${currentStudent.monday}</p>
		<p>Mardi : ${currentStudent.tuesday}</p>
		<p>Mercredi : ${currentStudent.wednesday}</p>
		<p>Jeudi : ${currentStudent.thursday}</p>
		<p>Vendredi : ${currentStudent.friday}</p>
		<p>Samedi : ${currentStudent.saturday}</p>
		<p>Dimanche : ${currentStudent.sunday}</p>
	</fieldset>
	<fieldset>
		<legend>Logs</legend>
		<table>
			<tr>
				<th>Nom d'utilisateur</th>
				<th>Date</th>
				<th>Heure</th>
				<th>Statut</th>
				<td>Légende: DEP=Départ, ARR=Arrivée, ADD=Temps Ajouté, ERR=Erreur</td>
			</tr>
			<c:forEach items="${logs}" var="i" end="10">
				<tr>
					<td>${i.username}</td>
					<td>${i.date}</td>
					<td>${i.time}</td>
					<td class="${i.status}" >${i.status}</td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
    </body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>