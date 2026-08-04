const API_URL = "http://localhost:8080/api/employees";

// Load employees when page loads
window.onload = function () {
    loadEmployees();
};

// =============================
// Load All Employees
// =============================
function loadEmployees() {

    fetch(API_URL)
        .then(response => response.json())
        .then(data => {

            let rows = "";

            data.forEach(emp => {

                rows += `
                <tr>
                    <td>${emp.employeeId}</td>
                    <td>${emp.employeeCompanyId}</td>
                    <td>${emp.employeeName}</td>
                    <td>${emp.email}</td>
                    <td>${emp.department}</td>
                    <td>${emp.role}</td>

                </tr>
                `;
            });

            document.getElementById("employeeTable").innerHTML = rows;

        })
        .catch(error => console.log(error));
}

// =============================
// Save / Update Employee
// =============================
function saveEmployee() {

    const id = document.getElementById("employeeId").value;

	const employee = {

	    employeeCompanyId: document.getElementById("employeeCompanyId").value,
	    employeeName: document.getElementById("employeeName").value,
	    email: document.getElementById("email").value,
	    password: document.getElementById("password").value,
	    department: document.getElementById("department").value,
	    role: document.getElementById("role").value

	};

    let url = API_URL;
    let method = "POST";

    if (id !== "") {
        url = API_URL + "/" + id;
        method = "PUT";
    }

    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(employee)

    })

    .then(async response => {

        const message = await response.text();

        document.getElementById("message").innerHTML = message;

        if (response.ok) {
            document.getElementById("message").style.color = "green";
            clearForm();
            loadEmployees();
        } else {
            document.getElementById("message").style.color = "red";
        }

    })

    .catch(error => {

        console.log(error);

        document.getElementById("message").innerHTML = error;

        document.getElementById("message").style.color = "red";

    });

}

/// =============================
// Edit Employee
// =============================
function editEmployee(id) {

    fetch(API_URL + "/" + id)

        .then(response => response.json())

        .then(emp => {

            document.getElementById("employeeId").value = emp.employeeId;
            document.getElementById("employeeCompanyId").value = emp.employeeCompanyId;
            document.getElementById("employeeName").value = emp.employeeName;
            document.getElementById("email").value = emp.email;
            document.getElementById("password").value = ""; // Keep password blank
            document.getElementById("department").value = emp.department;
            document.getElementById("role").value = emp.role;

        })

        .catch(error => {
            console.log(error);
        });

}

// =============================
// Delete Employee
// =============================
function deleteEmployee(id) {

    if (!confirm("Are you sure you want to delete this employee?")) {
        return;
    }

    fetch(API_URL + "/" + id, {

        method: "DELETE"

    })

    .then(response => response.text())

    .then(message => {

        alert(message);

        loadEmployees();

    })

    .catch(error => console.log(error));

}

// =============================
// Clear Form
// =============================
function clearForm() {

    document.getElementById("employeeId").value = "";
    document.getElementById("employeeCompanyId").value = "";
    document.getElementById("employeeName").value = "";
	document.getElementById("password").value = "";
    document.getElementById("email").value = "";
    document.getElementById("department").value = "";
    document.getElementById("role").value = "";

    document.getElementById("message").innerHTML = "";

}