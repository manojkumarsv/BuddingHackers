    $(document).ready(function(){
    	$("#register").click(function(){
    		$.ajax({
                url: '/register',
                method: "GET",
                success: function(response) {
                    $("#first").html( response );
                }
            });
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
                    }
                },
                complete: function (data) {
                	$.ajax({
                        url: '/predict',
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

        $("#logout").click(function() {
            $("form")[0].reset();
            $("#first").show();
            $("#second").hide();
        });
    });