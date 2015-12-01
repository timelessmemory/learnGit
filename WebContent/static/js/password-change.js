function hideTip() {
	 setTimeout(function() {
         document.getElementById("flashMessageDiv").style.display = "none";
         window.location.reload();
       },3000);
}

$(function() {
        $("#firstPassword").blur(function() {
        if ($(this).val() == "") {
            $("#tipMessage").html("Password is required!");
            $("#tipDialog").show(1000);
            $("#mask").show();
        } else {
            $("#tipDialog").hide(300);
            $("#mask").hide();
        }
   });
   
   $("#secondPassword").blur(function() {
        if ($(this).val() == "") {
            $("#tipMessage").html("Password is required!");
            $("#tipDialog").show(1000);
            $("#mask").show();
        } else {
            $("#tipDialog").hide(300);
            $("#mask").hide();
            }
       });
});
  
function hideTipDialog() {
   $("#tipDialog").hide(300);
   $("#mask").hide();
}

$(function() {
          $("#submit").click(function() {
              var flag = true;
              if (!$("#firstPassword").val()) {
                  $("#tipMessage").html("Password is required!");
                 $("#tipDialog").show(1000);
                  $("#mask").show();
                  flag = false;
              }
              if (!$("#secondPassword").val()) {
                  if(flag == true) {
                      $("#tipMessage").html("Password is required!");
                      $("#tipDialog").show(1000);
                      $("#mask").show();
                      flag = false;
                  }
              }

              if($("#firstPassword").val().trim() != $("#secondPassword").val().trim()) {
                  $("#tipMessage").html("Two input is inconsistent!");
                  $("#tipDialog").show(1000);
                  $("#mask").show();
                  flag = false;
              } else {
                  if ($("#firstPassword").val()) {
                      $("#tipMessage").html("Password is required!");
                      $("#tipDialog").hide(300);
                      $("#mask").hide();
                  }
              }
              if (flag) {
                  $("#form").submit();
              }
          });
});