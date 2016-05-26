<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
<head>
<title>Manage Congés</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
	<h1 class="pageTitle">Gestion des congés</h1>

	<fieldset>
		<legend>Ajouter des congés</legend>
		<form method="post" action="setholydays">
			<table class="noBorder">
				<tr>
					<td>Ajouter un congé :</td>
					<td><input type="text" id="addSingleHolyday"
						name="addSingleHolyday" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td><input type="submit" name="addSingleHolydayButton"
						value="Ajouter" class="sansLabelNoSpace" /></td>
				</tr>
				<tr>
					<td>Ajouter une plage de congés : Du</td>
					<td><input type="text" id="addHolydaysGapA"
						name="addHolydaysGapA" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td class="centre">Au</td>
					<td><input type="text" id="addHolydaysGapB"
						name="addHolydaysGapB" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td><input type="submit" name="addHolydayGapButton"
						value="Ajouter" class="sansLabelNoSpace" /></td>
				</tr>
			</table>
		</form>
	</fieldset>
	<fieldset>
		<legend>Supprimer des congés</legend>
		<form method="post" action="setholydays">
			<table class="noBorder">
				<tr>
					<td>Supprimer un congé</td>
					<td><input type="text" id="deleteSingleHolyday"
						name="deleteSingleHolyday" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td><input type="submit" name="deleteSingleHolydayButton"
						value="Supprimer" class="sansLabelNoSpace" /></td>
				</tr>
				<tr>
					<td>Supprimer une plage de congés: Du</td>
					<td><input type="text" id="deleteHolydaysGapA"
						name="deleteHolydaysGapA" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td class="centre">Au</td>
					<td><input type="text" id="deleteHolydaysGapB"
						name="deleteHolydaysGapB" placeholder="JJ-MM-AAAA" size="16" maxlength="16" /></td>
					<td><input type="submit" name="deleteHolydaysGapButton"
						value="Supprimer" class="sansLabelNoSpace" /></td>
				</tr>
			</table>
		</form>
	</fieldset>
	<c:if test="${!empty holydays}">
	<fieldset>
		<legend>Jours de congés</legend>
		<table>
			<c:forEach items="${holydays}" var="i">
				<tr>
					<td>${i.date}</td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
	</c:if>
</body>
</html>
<%@include file="/WEB-INF/footer.jsp"%>