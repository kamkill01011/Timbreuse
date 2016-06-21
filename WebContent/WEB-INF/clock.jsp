<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="WebContent/clockScript.js">initLocalClocks()</script>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<footer>
	<article class="clock">
		<script type="text/javascript" src="WebContent/clockScript.js">initLocalClocks()</script>
		<div class="hours-container">
			<div class="hours"></div>
		</div>
		<div class="minutes-container">
			<div class="minutes"></div>
		</div>
		<div class="seconds-container">
			<div class="seconds"></div>
		</div>
	</article>
</footer>
</html>