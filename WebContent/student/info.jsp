<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Informations</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    <h1 class="pageTitle">Informations Personnelles</h1>
    <fieldset>
		<legend>${currentStudent.lastname} ${currentStudent.firstname}</legend>
		<p>Classe : ${currentStudent.classe}</p>
		<p>Différence : ${currentStudent.timeDiff}</p>
		<p>E-mail : ${currentStudent.email}</p>
		<p>Status : ${currentStudent.status}</p>
		<p>Date d'inscription : ${currentStudent.startDate}</p>
		<p>Date dernier timbrage : ${currentStudent.lastCheckDate}</p>
		<p>Heure dernier timbrage : ${currentStudent.lastCheckTime}</p>
		<p>Heures effectuées aujourd'hui : ${currentStudent.todayTime}</p>
	</fieldset>
	<fieldset>
		<legend>Horaire</legend>
		<p>Lund : ${currentStudent.monday}</p>
		<p>Mardi : ${currentStudent.tuesday}</p>
		<p>Mercrei : ${currentStudent.wednesday}</p>
		<p>Jeudi : ${currentStudent.thursday}</p>
		<p>Vendredi : ${currentStudent.friday}</p>
		<p>Samedi : ${currentStudent.saturday}</p>
		<p>Dimanche : ${currentStudent.sunday}</p>
	</fieldset>
	<fieldset>
		<legend>Compte utilisateur</legend>
		<p>Username : ${sessionScope.userSession.username}</p>
		<p>Password : ${sessionScope.userSession.password}</p>
	</fieldset>
	
	<fieldset>
		<legend>Logs</legend>
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<th>Username</th>
							<th>Date</th>
							<th>Time</th>
							<th>Status</th>
						</tr>
						<c:forEach items="${logs}" var="i">
							<tr>
								<td>${i.username}</td>
								<td>${i.date}</td>
								<td>${i.time}</td>
								<td>${i.status}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
		</table>
	</fieldset>
    </body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>