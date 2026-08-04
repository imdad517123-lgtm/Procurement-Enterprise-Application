const EMPLOYEE_API = "http://localhost:8080/api/employees";
const REQUISITION_API = "http://localhost:8080/api/requisitions";
const WORKFLOW_API = "http://localhost:8080/api/workflow";

window.onload = function () {

    loadDashboard();

};

// ==========================
// Load Dashboard
// ==========================
function loadDashboard() {

    loadEmployees();

    loadRequisitions();

    loadPending();

    loadApproved();

    loadRejected();

}

// ==========================
// Employee Count
// ==========================
function loadEmployees() {

    fetch(EMPLOYEE_API)

        .then(response => response.json())

        .then(data => {

            document.getElementById("totalEmployees").innerHTML = data.length;

        })

        .catch(error => console.log(error));

}

// ==========================
// Requisition Count
// ==========================
function loadRequisitions() {

    fetch(REQUISITION_API)

        .then(response => response.json())

        .then(data => {

            document.getElementById("totalRequisitions").innerHTML = data.length;

            loadRecentRequisitions(data);

        })

        .catch(error => console.log(error));

}

// ==========================
// Recent Requisitions Table
// ==========================
function loadRecentRequisitions(data) {

    const table = document.getElementById("requisitionTable");

    table.innerHTML = "";

    data.forEach(req => {

        table.innerHTML += `
            <tr>

                <td>${req.requestId}</td>

                <td>${req.employee.employeeName}</td>

                <td>${req.itemName}</td>

                <td>${req.quantity}</td>

                <td>${req.estimatedCost}</td>

                <td>${req.status}</td>

                <td>${req.createdDate}</td>

            </tr>
        `;

    });

}

// ==========================
// Pending Count
// ==========================
function loadPending() {
    fetch(WORKFLOW_API + "/pending")
        .then(response => response.json())
        .then(data => {
            document.getElementById("pendingRequests").innerHTML = data.length;
        })
        .catch(error => console.log(error));
}
// ==========================
// Approved Count
// ==========================
function loadApproved() {

    fetch(WORKFLOW_API + "/approved")

        .then(response => response.json())

        .then(data => {

            document.getElementById("approvedRequests").innerHTML = data.length;

        })

        .catch(error => console.log(error));

}

// ==========================
// Rejected Count
// ==========================
function loadRejected() {

    fetch(WORKFLOW_API + "/rejected")

        .then(response => response.json())

        .then(data => {

            document.getElementById("rejectedRequests").innerHTML = data.length;

        })

        .catch(error => console.log(error));

}