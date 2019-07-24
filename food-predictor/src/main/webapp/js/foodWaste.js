function submitForm(){
	var reqParam={
		date: $('#usageDate').val()	
	};
	 $.ajax({
		 url: "test/restApi",
		 method:"POST",
		 dataType: "json",
		 data: reqParam,
		 success: function(result){
		 },
		 failure: function(error){
		 }
	 });
}