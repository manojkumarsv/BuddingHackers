    $(document).ready(function(){
    	$("#register").click(function(){
    		var registerData={
            	userName: $('#loginusername').val(),
            	password: $('#loginpassword').val(),
            	confirmPassword: $('#confirmpassword').val(),
            	firstName: $('#firstname').val(),
            	lastName: $('#lastname').val(),
            	organization: $('#organization').val(),
            	emailAddress: $('#email').val(),
            	contactNumber: $('#contact').val()
            };
    		$.ajax({
                url: '/register',
                method: "PUT",
                data: JSON.stringify(registerData),
                contentType: "application/json; charset=utf-8",
                success: function(response) {
                	alert("Registration is successful! You can log in now.");
                },
                complete: function (data) {
                	$.ajax({
                        url: '/',
                        method: "GET",
                        success: function(response) {
                            $("#first").html( response );
                        }
                    });
                },
    			error: function(textStatus, errorThrown) {
    				alert(textStatus);
    			}
            });
    	});
    });