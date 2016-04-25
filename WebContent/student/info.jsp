<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Informations</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>

    <body>
    
    <fieldset>
		<legend>${sessionScope.currentStudent.lastname} ${sessionScope.currentStudent.firstname}</legend>
		<p>Classe : ${sessionScope.currentStudent.classe}</p>
		<p>Différence : ${sessionScope.currentStudent.timeDiff}</p>
		<p>E-mail : ${sessionScope.currentStudent.email}</p>
		<p>Password : ${sessionScope.currentStudent.password}</p>
		<p>Username : ${sessionScope.currentStudent.username}</p>
		<p>Status : ${sessionScope.currentStudent.status}</p>
		<p>Date d'inscription : ${sessionScope.currentStudent.startDate}</p>
		<p>Dernier timbrage : ${sessionScope.currentStudent.lastCheck}</p>
		<p>Niveau de permission : ${sessionScope.currentStudent.permissionLevel}</p>
		<p>Heures effectuées aujourd'hui : ${sessionScope.currentStudent.todayTime}</p>
	</fieldset>
	<fieldset>
		<legend>Horaire</legend>
		<p>Lund : ${sessionScope.currentStudent.monday}</p>
		<p>Mardi : ${sessionScope.currentStudent.tuesday}</p>
		<p>Mercrei : ${sessionScope.currentStudent.wednesday}</p>
		<p>Jeudi : ${sessionScope.currentStudent.thursday}</p>
		<p>Vendredi : ${sessionScope.currentStudent.friday}</p>
		<p>Samedi : ${sessionScope.currentStudent.saturday}</p>
		<p>Dimanche : ${sessionScope.currentStudent.sunnday}</p>
	</fieldset>
	<form method="get" action="/Timbreuse/logout">
		<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
	</form>
    </body>
</html>