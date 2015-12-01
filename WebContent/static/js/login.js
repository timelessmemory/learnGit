      function verify() {
        var username = document.getElementById("userName").value;
        var password = document.getElementById("password").value;
        var ps_err_icon = document.getElementById("passwordErrorIcon");
        var user_error_icon = document.getElementById("userNameErrorIcon");
        var err_mes = document.getElementById("errorMes");

        ps_err_icon.style.display = "none";
        user_error_icon.style.display = "none";
        
        if (!username && !password) {
        	err_mes.innerHTML = "Username and password are required!";
        	err_mes.style.visibility = "visible";
            ps_err_icon.style.display = "block";
            user_error_icon.style.display = "block";
            return;
        }
        if (!username) {
        	err_mes.innerHTML = "Username is required!";
        	err_mes.style.visibility = "visible";
            user_error_icon.style.display = "block";
            return;
        }
        if (!password) {
        	err_mes.innerHTML = "Password is required!";
        	err_mes.style.visibility = "visible";
            ps_err_icon.style.display = "block";
            return;
        }
        document.getElementById("loginForm").submit();
      }

      window.onload = function() {
    	  document.getElementById("param").value = location.hash;
      }
      
      function login(event) {
          if (event.keyCode==13) {
              verify();
          }
       }