<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
<html>
<head>
<meta charset="utf-8" />
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
				<td>
					Ajouter un congé : 
				</td>
				<td>
					<input type="text" id="addSingleHolyday" name="addSingleHolyday" value="" size="16" maxlength="16" />
				</td>
				<td>
					<input type="submit" name="addSingleHolydayButton" value="Ajouter" class="sansLabelNoSpace" />
				</td>
			</tr>
			<tr>
				<td>
					Ajouter une plage de congés : Du
				</td>
				<td>
					<input type="text" id="addHolydaysGapA" name="addHolydaysGapA" value="" size="16" maxlength="16" />	
				</td>
				<td class="centre">
					Au
				</td>
				<td>
					<input type="text" id="addHolydaysGapB" name="addHolydaysGapB" value="" size="16" maxlength="16" />	
				</td>
				<td> 
					<input type="submit" name="addHolydayGapButton" value="Ajouter" class="sansLabelNoSpace" />
				</td>
			</tr>
		</table>	
		</form>
	</fieldset>
</body>
</html>