$(document).ready(function(){	
    $("#register").click(function(){
	   	 	document.getElementById("loginusername").reset;
	   	 	document.getElementById("loginpassword").reset;
    		$("#loginContainer").hide();
    		$("#aboutContainer").hide();
    		$("#registerContainer").show();    		
    	});
    	$("#registerUser").click(function(){
		var registerData={
    		userName: $('#usernamesignup').val(),
    		password: $('#passwordsignup').val(),
    		emailAddress: $('#emailsignup').val(),
    		contactNumber: $('#contact').val(),
    		organization: $("#organization").val(),
    		firstName: $('#firstname').val(),
    		lastName: $('#lastname').val(),
    		
    	};
		if(registerData.userName==null || registerData.password==null ||
				registerData.emailAddress ==null || $('#passwordsignup_confirm').val()==null || registerData.contactNumber ==null
				|| registerData.organization == null || registerData.firstName == null || registerData.lastName == null){
			 alert("Please enter all values");
		}else if(registerData.password!== $('#passwordsignup_confirm').val()){
			alert("Password and Re-type password mismatched.");
		}else {
			$.ajax({
	           url: '/register',
	           method: "POST",
	           data: JSON.stringify(registerData),
	           contentType: "application/json; charset=utf-8",
	           success: function(data) {        	   
	        	 alert("Registered Successfully. Please login to proceed.");
	        	 document.getElementById("usernamesignup").reset;
	        	 document.getElementById("passwordsignup").reset;
	        	 document.getElementById("emailsignup").reset;
	        	 document.getElementById("contact").reset;
	        	 document.getElementById("organization").reset;
	        	 document.getElementById("firstname").reset;
	        	 document.getElementById("lastname").reset;
	        	 document.getElementById("passwordsignup_confirm").reset;
	        	 
	        	 $("#loginContainer").show();
	      		 $("#aboutContainer").show();
	      		 $("#registerContainer").hide();
	           },
	           error: function(){        	   
	           }   		
	       });
		}
   		
   	});
    $("#login").click(function(){
    	var loginData={
    		userName: $('#loginusername').val(),
    		password: $('#loginpassword').val()
    	};
        $.ajax({
            url: '/login',
            method: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            success:function(data){
                if(data != "OK")
                {
                    alert("Login failed. Please try again");
                } else {
               	 	document.getElementById("loginusername").reset;
               	 	document.getElementById("loginpassword").reset;
                	$("#loginPageContainer").hide();
                	$("#aboutContainer").hide();
                	$("#signOutBtn").show();
                	$("#foodPredictionContainer").show();
                }
            },
            error: function(textStatus, errorThrown) {
            	alert(textStatus);
            }
        });
    });

    $("#signOutBtn").click(function() {        	
    	 $("#signOutBtn").hide();
    	 $("#foodPredictionContainer").hide();
    	 $("#aboutContainer").show();
    	 $("#loginPageContainer").show();
    });
    
    $("#goToLogin").click(function() {
	 document.getElementById("usernamesignup").reset;
	 document.getElementById("passwordsignup").reset;
	 document.getElementById("emailsignup").reset;
	 document.getElementById("passwordsignup_confirm").reset;
	 $("#registerContainer").hide();
	 $("#loginContainer").show();
	 $("#aboutContainer").show();
   });
    
    $("#predictFood").click(function() { 
		var foodPredictionData={
			usageDate: $('#usageDate').val(),
			attendance: $('#attendance').val(),
			occasion: $('#occasion').val(),
			foodType: $('#foodType').val()
    	};
		if(foodPredictionData.usageDate==null || foodPredictionData.attendance==null || foodPredictionData.occasion ==null){
			 alert("Please enter all values");
		}else {
			$.ajax({
	           url: '/predict',
	           method: "POST",
	           data: JSON.stringify(foodPredictionData),
	           contentType: "application/json; charset=utf-8",
	           success: function(response) {
	        	   var r = JSON.parse(response);
	        	 alert("Food Prediction was successful.");
	        	 $("#usageDate").show();
	        	 document.getElementById("attendance").reset;
	        	 document.getElementById("occasion").reset;
	        	 document.getElementById("foodType").reset;
	        	 $("#loginContainer").hide();
	      		 $("#aboutContainer").hide();
	      		 $("#registerContainer").hide();
	      		 $('#foodPredictionResultContainer').show();
	      		 $('#foodPredictionCount').html('Predicted No. of Meals '.concat(r));
	           },
	           error: function(){ 
	        	   alert("Food Prediction failed.");
	           }   		
	       });
		}
    });
});