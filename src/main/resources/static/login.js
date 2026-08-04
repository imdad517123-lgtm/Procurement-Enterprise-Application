	const API = "http://localhost:8080/api/auth/login";
	
	function login() {
	
	    const loginData = {
	
	        email: document.getElementById("email").value,
	
	        password: document.getElementById("password").value,
	
	        role: document.getElementById("role").value
	
	    };
	
	    fetch(API,{
	
	        method:"POST",
	
	        headers:{
	            "Content-Type":"application/json"
	        },
	
	        body:JSON.stringify(loginData)
	
	    })
	
		.then(async response => {
	
		    let data;
	
		    const contentType = response.headers.get("content-type");
	
		    if (contentType && contentType.includes("application/json")) {
	
		        data = await response.json();
	
		    } else {
	
		        data = {
		            message: await response.text()
		        };
	
		    }
	
	
		    if (response.ok) {
	
		        document.getElementById("message").style.color = "green";
		        document.getElementById("message").innerHTML = data.message;
	
	
		        localStorage.setItem("employeeId", data.employeeId);
		        localStorage.setItem("employeeCompanyId", data.employeeCompanyId);
		        localStorage.setItem("employeeName", data.employeeName);
		        localStorage.setItem("email", data.email);
		        localStorage.setItem("role", data.role);
	
	
				setTimeout(() => {

				    if (data.role === "PROCUREMENT_OFFICER") {

				        window.location.href = "dashboard.html";

				    } else if (data.role === "MANAGER") {

				        window.location.href = "manager-dashboard.html";

				    } else if (data.role === "EMPLOYEE") {

				        window.location.href = "employee.html";

				    } else {

				        document.getElementById("message").style.color = "red";
				        document.getElementById("message").innerHTML = "Invalid Role";

				    }

				}, 1000);
	
	
		    } else {
	
		        document.getElementById("message").style.color = "red";
		        document.getElementById("message").innerHTML = data.message;
	
		    }
	
		})
	    .catch(error=>{
	
	        document.getElementById("message").style.color="red";
	        document.getElementById("message").innerHTML="Unable to connect to server.";
	
	        console.log(error);
	
	    });
	
	}