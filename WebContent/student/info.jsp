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
    <h1 class="pageTitle">Informations Personnelles</h1>
    <fieldset>
		<legend>${currentStudent.lastname} ${currentStudent.firstname}</legend>
		<p>Classe : ${currentStudent.classe}</p>
		<p>Différence : ${currentStudent.timeDiff}</p>
		<p>E-mail : ${currentStudent.email}</p>
		<p>Status : ${currentStudent.status}</p>
		<p>Date d'inscription : ${currentStudent.startDate}</p>
		<p>Dernier timbrage : ${currentStudent.lastCheck}</p>
		<p>Niveau de permission : ${currentStudent.permissionLevel}</p>
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
	<form method="get" action="/Timbreuse/logout" class="DecoPwd">
		<input type="submit" name="logout" value="Déconnexion" class="logout" />
	</form>
	<form method="get" action="/Timbreuse/changepassword" class="DecoPwd">
		<input type="submit" value="Changer de mot de passe" class="changePassword" />
	</form>
    </body>
    <footer>
    	<div class="footerLogo">
			<div class="cpnv-logo"></div>
			<div class="mct-logo"></div>
		</div>
    </footer>
</html>