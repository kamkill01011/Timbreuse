<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="connection">
            <fieldset>
                <legend>Connexion</legend>
                <label for="email">Adresse email <span class="requis">*</span></label>
                <input type="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
                <span class="error">${form.errors['email']}</span>
                <br />

                <label for="password">Mot de passe <span class="requis">*</span></label>
                <input type="password" name="password" value="" size="20" maxlength="20" />
                <span class="error">${form.errors['password']}</span>
                <br />

                <input type="submit" value="Connexion" class="sansLabel" />
                <br />
                
                <p class="${empty form.errors ? 'succes' : 'error'}">${form.result}</p>
            </fieldset>
        </form>
    </body>
</html>