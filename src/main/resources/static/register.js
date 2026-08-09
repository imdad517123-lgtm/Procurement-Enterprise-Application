const API_URL = "http://localhost:9090/api/employees";

function checkRegistrationRole() {

    const role = document.getElementById("role").value;

    if (role === "SUPPLIER") {

        window.location.href = "supplier-registration.html";

    }
}

async function registerEmployee() {

    const employeeCompanyId = document.getElementById("employeeCompanyId").value.trim();
    const employeeName = document.getElementById("employeeName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const department = document.getElementById("department").value.trim();
    const role = document.getElementById("role").value;

    const message = document.getElementById("message");
    message.innerHTML = "";

    if (
        employeeCompanyId === "" ||
        employeeName === "" ||
        email === "" ||
        password === "" ||
        department === "" ||
        role === ""
    ) {
        message.style.color = "red";
        message.innerHTML = "Please fill all fields.";
        return;
    }

    const employee = {
        employeeCompanyId: employeeCompanyId,
        employeeName: employeeName,
        email: email,
        password: password,
        department: department,
        role: role
    };

    try {

        const response = await fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(employee)

        });

        const result = await response.text();

        if (response.ok) {

            message.style.color = "green";
            message.innerHTML = result;

            document.getElementById("employeeCompanyId").value = "";
            document.getElementById("employeeName").value = "";
            document.getElementById("email").value = "";
            document.getElementById("password").value = "";
            document.getElementById("department").value = "";
            document.getElementById("role").value = "";

            setTimeout(function () {
                window.location.href = "login.html";
            }, 1500);

        } else {

            message.style.color = "red";
            message.innerHTML = result;

        }

    } catch (error) {

        console.error(error);

        message.style.color = "red";
        message.innerHTML = "Unable to connect to server.";

    }
}