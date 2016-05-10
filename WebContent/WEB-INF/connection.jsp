<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
    	<h1 class="pageTitle">Timbreuse</h1>
        <form method="post" action="connection">
            <fieldset>
                <legend>Connexion</legend>
                <label for="username">Adresse email <span class="requis">*</span></label>
                <input type="text" name="username" value="<c:out value="${user.username}"/>" size="20" maxlength="60" />
                <span class="error">${form.errors['username']}</span>
                <br />
                <label for="password">Mot de passe <span class="requis">*</span></label>
                <input type="password" name="password" value="" size="20" maxlength="20" />
                <span class="error">${form.errors['password']}</span>
                <br />
                <input type="submit" name="connection" value="Connexion" class="login" />
                <br />
                
                <p class="${empty form.errors ? 'succes' : 'error'}">${form.result}</p>
                
                <c:if test="${!empty sessionScope.userSession}">
                	<p class="succes">Vous êtes connecté en tant que : ${sessionScope.userSession.username}</p>
                </c:if>
            </fieldset>
        </form>
        
        <c:if test="${!empty sessionScope.userSession}">
			<form method="get" action="logout">
				<input type="submit" name="logout" value="Déconnexion" class="sansLabel" />
			</form>
		</c:if>
    </body>
    <footer>
    	<div class="footerLogo">
			<div class="cpnv-logo"></div>
			<div class="mct-logo"></div>
		</div>
    </footer>
</html>