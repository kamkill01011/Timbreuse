<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
<head>
<title>Horaires</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>

<body>

	<h1 class="pageTitle">Gestion des horaires des classes</h1>
		<fieldset>
			<legend>${selectedClasse}</legend>
			<form method="post" action="settimetables" id="timetables">
				<table>
					<tr>
						<th>Classe</th>
						<th>Lundi</th>
						<th>Mardi</th>
						<th>Mercredi</th>
						<th>Jeudi</th>
						<th>Vendredi</th>
						<th>Samedi</th>
						<th>Dimanche</th>
						<th>Valider</th>
					</tr>
					<c:forEach items="${timeTables}" var="i">
						<tr>
							<c:forEach items="${i}" var="j" varStatus="k">
								<td>
									<c:choose>
										<c:when test="${k.first}">
											${j}
										</c:when>    
										<c:otherwise> 
   											<input type="text" name="${i[0]}${k.index}" value="${j}" placeholder="HH:MM:SS" size="8" maxlength="8" />
										</c:otherwise>
									</c:choose>
								</td>
							</c:forEach>
							<td><input type="submit" id="${i[0]}" name="${i[0]}" value="Changer les horaires" class="sansLabelNoSpace" /></td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</fieldset>
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>