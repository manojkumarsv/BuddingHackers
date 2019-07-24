<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Food Predictor</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="../../css/login.css" rel="stylesheet">
</head>
<body>
	<div id="first" class="login">
		<form action="" method="post">
			<input type="text" id="loginusername" placeholder="Username" /> <input
				type="password" id="loginpassword" placeholder="Password" /> <input
				type="button" id="login" value="Sign In" />
		</form>
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<script src="../../js/login.js"></script>
		<script src="../../js/foodWaste.js"></script>
	</div>
	<div id="second" style="display:none">
		<h2>Food Usage Details</h2>
		<table>
			<tr>
				<td>Date :</td>
				<td><input type="date" id="usageDate" name="event-date"
					min="2018-01-01" max="2019-12-31" required></td>
			</tr>
			<tr>
				<td>Attendance :</td>
				<td><input id="attendance" type="number" name="attendance"
					required></td>
			</tr>
			<tr>
				<td>Occasion :</td>
				<td><select id="occasion">
						<option value="workingDay">Working Day</option>
						<option value="holiday">Holiday</option>
				</select></td>
			</tr>
		</table>

		<p>
			<button onclick="submitForm()">SUBMIT</button>
		</p>
	</div>
</body>
</html>