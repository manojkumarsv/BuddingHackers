$(document).ready(function(){	
    $("#register").click(function(){
	    	$('#loginusername')[0].value=null;
	   	 	$('#loginpassword')[0].value=null;
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
	        	 $('#usernamesignup')[0].value=null;
	        	 $('#passwordsignup')[0].value=null;
	        	 $('#emailsignup')[0].value=null;
	        	 $('#contact')[0].value=null;
	        	 $('#organization')[0].value=null;
	        	 $('#firstname')[0].value=null;
	        	 $('#lastname')[0].value=null;
	        	 $('#passwordsignup_confirm')[0].value=null;
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
                	$('#loginusername')[0].value=null;
               	 	$('#loginpassword')[0].value=null;
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
	 $('#usernamesignup')[0].value=null;
	 $('#passwordsignup')[0].value=null;
	 $('#emailsignup')[0].value=null;
	 $('#passwordsignup_confirm')[0].value=null;
	 $("#registerContainer").hide();
	 $("#loginContainer").show();
	 $("#aboutContainer").show();
   });
    
    $("#predictFood").click(function() { 
		var foodPredictionData={
			usageDate: $('#usageDate').val(),
			attendance: $('#attendance').val(),
			occasion: $('#occasion').val(),
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
	        	 $('#attendance')[0].value=null;
	        	 $('#occasion')[0].value=null;
	        	 $("#loginContainer").hide();
	      		 $("#aboutContainer").hide();
	      		 $("#registerContainer").hide();
	      		 $('#foodPredictionResultContainer').show();
	      		 $('#foodPredictionCount').html('Predicted Quantity of food '.concat(r));
	           },
	           error: function(){ 
	        	   alert("Food Prediction failed.");
	           }   		
	       });
		}
    });
});