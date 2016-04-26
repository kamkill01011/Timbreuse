<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Changer de mot de passe</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="changepassword">
            <fieldset>
                <legend>Changez de mot de passe</legend>
                <label for="email">Ancien mot de passe <span class="requis">*</span></label>
                <input type="password" name="old" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
                <span class="error">${form.errors['old']}</span>
                <br />
                <label for="password">Nouveau mot de passe <span class="requis">*</span></label>
                <input type="password" name="new" value="" size="20" maxlength="20" />
                <br />
                <label for="password">Confimrer le mot de passe <span class="requis">*</span></label>
                <input type="password" name="confirm" value="" size="20" maxlength="20" />
                <span class="error">${form.errors['confirm']}</span>
                <br />
                <input type="submit" name="change" value="changer" class="sansLabel" />
                <br />
            </fieldset>
        </form>
    </body>
</html>