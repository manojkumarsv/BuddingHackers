<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Food Predictor</title>
</head>
<body>
	<div id="foodPredictionContainer" style="display: none">
		<div class="row-top">
			<div class="main">
				<div class="wrapper">
					<h1>
						<a href="index.html">Budding Hackers</a>
					</h1>
					<a class="button-1 signOutBtn" id="signOutBtn" href="#">Sign
						out</a>
				</div>
			</div>
		</div>
		<div class="row-bot">
			<div class="row-bot-bg">
				<div class="main">
					<img class="predictimg" src="../../images/predict.jpg" alt="" />
					<div class="predictcolumn-2">
						<div class="indent-left">
							<div class="maxheight indent-bot">
								Welcome!
								<table>
									<tr>
										<td>Date</td>
										<td><input type="date" id="usageDate" name="event-date"
											min="2018-01-01" max="2019-12-31" class="loginInput" required></td>
									</tr>
									<tr>
										<td>Attendance</td>
										<td><input id="attendance" type="number"
											name="attendance" class="loginInput" required></td>
									</tr>
									<tr>
										<td>Occasion</td>
										<td><select id="occasion" class="combo">
												<option value="Workingday">Working Day</option>
												<option value="Weekend">Weekend</option>
												<option value="Holiday">Holiday</option>
										</select></td>
									</tr>
									<tr>
										<td>Food Type</td>
										<td><select id="foodType" class="combo">
												<option value="Veg">Veg</option>
												<option value="NonVeg">Non-Veg</option>
										</select></td>
									</tr>
									<tr>
										<td></td>
										<td><a class="button-1 regSubmitBtn" id="predictFood"
											href="#">Predict</a></td>
									</tr>
								</table>
							</div>

							<div id="foodPredictionResultContainer" style="display: none">
								<blink id="foodPredictionCount" class="predictresult">
									Predicted No. of Meals : None</blink>
							</div>
							<div class="graphimg">
								<img id="graphimg" src="../../images/predictedFoodPerson.png" alt="" />
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>