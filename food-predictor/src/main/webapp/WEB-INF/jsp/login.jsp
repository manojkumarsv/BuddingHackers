<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Food Predictor</title>
<!-- <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">  -->
<link href="../../css/style.css" rel="stylesheet">
<link href="../../css/layout.css" rel="stylesheet">
<meta charset="utf-8">
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="../../js/tms-0.3.js" type="text/javascript"></script>
<script src="../../js/tms_presets.js" type="text/javascript"></script>
<script src="../../js/jquery.easing.1.3.js" type="text/javascript"></script>
<script src="../../js/login.js"></script>
</head>
<body id="page1">
	<!--==============================header=================================-->
	<header>
		<div class="row-top">
			<div class="main">
				<div class="wrapper">
					<h1>
						<a href="index.html" class="springy-text">Budding Hackers</a>
					</h1>
					<a class="button-1 signOutBtn" id="signOutBtn" href="#">Sign
						out</a>
				</div>
			</div>
		</div>
		<div class="row-bot">
			<div class="row-bot-bg">
				<div class="main">
					<div id="loginPageContainer">
						<div class="slider-wrapper">
							<div class="slider">
								<ul class="items">
									<li><img src="../../images/slider-img1.jpg" alt="" /></li>
									<li><img src="../../images/slider-img2.jpg" alt="" /></li>
									<li><img src="../../images/slider-img3.jpg" alt="" /></li>
								</ul>
							</div>
						</div>
						<div class="column-1">
							<div class="indent-left">
								<div class="maxheight indent-bot">
									<div id="loginContainer">
										<div class="loginTitle">Login</div>
										<table class="loginTable">
											<tr>
												<td><input class="loginInput" placeholder="Username"
													type="text" id="loginusername" name="userName" required></td>
											</tr>
											<tr>
												<td><input class="loginInput" id="loginpassword"
													placeholder="Password" type="password" name="pass" required></td>
											</tr>
										</table>
										<a id="register" href="#" class="regLink">Register</a> <a
											class="button-1" id="login" href="#">Sign In</a>
									</div>
									<div id="registerContainer" style="display: none">
										<div class="loginTitle">Sign up</div>
										<table class="loginTable">
											<tr>
												<td><input class="loginInput" id="usernamesignup"
													name="usernamesignup" required="required" type="text"
													placeholder="Username" /></td>
												<td><input id="emailsignup" name="emailsignup"
													required="required" type="email" placeholder="Email"
													class="loginInput" /></td>
											</tr>
											<tr>
												<td><input id="firstname" name="firstname"
													required="required" type="text" placeholder="Firstname"
													class="loginInput" /></td>
												<td><input id="lastname" name="lastname"
													required="required" type="text" placeholder="Lastname"
													class="loginInput" /></td>
											</tr>
											<tr>
												<td><input id="organization" name="organization"
													required="required" type="text" placeholder="Organization"
													class="loginInput" /></td>
												<td><input id="contact" name="contact"
													required="required" type="tel" placeholder="Contact"
													class="loginInput" /></td>

											</tr>
											<tr>
												<td><input id="passwordsignup" name="passwordsignup"
													required="required" type="password" placeholder="Password"
													class="loginInput" /></td>
												<td><input id="passwordsignup_confirm"
													name="passwordsignup_confirm" required="required"
													type="password" placeholder="Confirm Password"
													class="loginInput" /></td>
											</tr>
											<tr>
												<td><a id="goToLogin" href="#" class="regLink">Already
														a member?</a></td>
												<td><a class="button-1 regSubmitBtn" id="registerUser"
													href="#">Submit</a></td>
											</tr>
										</table>
									</div>
								</div>
								<div id="aboutContainer">
									<article class="column-2">
										<div class="maxheight indent-bot">
											<h3 class="loginTitle">About</h3>
											<marquee behavior="scroll" direction="up" scrollamount="1.5"
												class="aboutClass">
												<ul class="list-1">
													<li>Prediction of required food (in persons) on a
														given day based on the historical data for organizations
														like Schools, Colleges, Corporates etc</li>
													<li>Minimizing the food wastage.</li>
													<li>Platform for NGO's to subscribe the excessive food</li>
												</ul>
											</marquee>
										</div>
									</article>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="foodPredictionContainer" style="display: none">
					<img class="predictimg" src="../../images/predictedFoodPerson.png"
						alt="" />
					<div class="column-1">
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
								<h1 id="foodPredictionCount" class="predictresult">Predicted
									No. of Meals</h1>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<script type="text/javascript">
		Cufon.refresh();
		Cufon.now();
	</script>
	<script type="text/javascript">
		$(window).load(function() {
			$('.slider')._TMS({
				duration : 1000,
				easing : 'easeOutQuint',
				preset : 'slideDown',
				slideshow : 7000,
				banners : false,
				pauseOnHover : true,
				pagination : true,
				pagNums : false
			});
		});
	</script>
</body>
</html>