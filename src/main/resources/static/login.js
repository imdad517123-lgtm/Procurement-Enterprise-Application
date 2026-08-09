const API_URL = "http://localhost:9090/api/auth/login";

async function login() {

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;

    const message = document.getElementById("message");
    message.innerHTML = "";

    if (email === "" || password === "" || role === "") {
        message.style.color = "red";
        message.innerHTML = "Please fill all fields.";
        return;
    }

    try {

        const response = await fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                email: email,
                password: password,
                role: role
            })

        });

        const data = await response.json();
		console.log("Login Response:", data);
		alert(JSON.stringify(data));
        if (!response.ok) {
            message.style.color = "red";
            message.innerHTML = data.message || "Invalid Email or Password";
            return;
        }

        // Clear old session
        localStorage.clear();

        // Common data
        localStorage.setItem("token", data.token);
        localStorage.setItem("email", data.email);
        localStorage.setItem("role", data.role);

        // Employee Login
        if (data.role !== "SUPPLIER") {

            localStorage.setItem("employeeId", data.employeeId);
            localStorage.setItem("employeeCompanyId", data.employeeCompanyId);
            localStorage.setItem("employeeName", data.employeeName);

        }

        // Supplier Login
        if (data.role === "SUPPLIER") {

            localStorage.setItem("supplierId", data.supplierId);
			alert("Saved Supplier ID = " + localStorage.getItem("supplierId"));
            localStorage.setItem("supplierName", data.supplierName);

        }

        message.style.color = "green";
        message.innerHTML = "Login Successful...";

        setTimeout(() => {

            switch (data.role) {

                case "EMPLOYEE":
                    window.location.href = "employee-dashboard.html";
                    break;

                case "MANAGER":
                    window.location.href = "manager-dashboard.html";
                    break;

                case "PROCUREMENT_OFFICER":
                    window.location.href = "procurement.html";
                    break;

                case "FINANCE":
                    window.location.href = "finance.html";
                    break;

                case "SUPPLIER":
                    window.location.href = "supplier.html";
                    break;

                default:
                    message.style.color = "red";
                    message.innerHTML = "Invalid Role";
                    break;
            }

        }, 1000);

    } catch (error) {

        console.error(error);

        message.style.color = "red";
        message.innerHTML = "Unable to connect to the server.";

    }
}