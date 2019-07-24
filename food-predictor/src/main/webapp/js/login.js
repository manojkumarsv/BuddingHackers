    $(document).ready(function(){
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
                	debugger;
                    if(data == "OK")
                    {
                        $("#first").hide();
                        $("#second").show();
                    }
                    else
                    {
                        alert("Please try again");
                    }
                }
            });
        });

        $("#logout").click(function() {
            $("form")[0].reset();
            $("#first").show();
            $("#second").hide();
        });
    });