<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>

    <body>
        <p>Ceci est une page générée depuis une JSP.</p>
        <p>
            ${test}
            ${param.auteur}
    	</p>
        <p>
            Récupération du bean :
            ${name.nom}
            ${name.diff}
        </p>
        <p>
			<table>
				<tr>
					<th>Valeur</th>
				</tr>
			<c:forEach var="i" begin="0" end="3" step="1">
				<tr>
					<td>${liste.get(i)}</td>
					</tr>
			</c:forEach>
			</table>
		</p>
		<p>
			Récupération du jour du mois :
			<c:choose>
				<c:when test="${jourDuMois % 2 == 0}">Jour pair : 
					<c:out value="${requestScope.jour}" />
				</c:when>
				<c:otherwise>Jour impair : 
					<c:out value="${requestScope.jour}" />
				</c:otherwise>
			</c:choose>
		</p>
    </body>
</html>