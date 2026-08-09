// ===========================================
// Manager Dashboard JavaScript (JWT Version)
// ===========================================

const BASE_URL = "http://localhost:9090/api/workflow";

// Check Login
const token = localStorage.getItem("token");

if (!token) {
    alert("Please login first.");
    window.location.href = "login.html";
}

// ===========================================
// Load Pending Requests
// ===========================================

async function loadPendingRequests() {

    try {

        const response = await fetch(`${BASE_URL}/pending`, {

            method: "GET",

            headers: {
                "Authorization": "Bearer " + token
            }

        });

        if (response.status === 401) {

            alert("Session Expired");

            localStorage.clear();

            window.location.href = "login.html";

            return;
        }

        if (!response.ok) {
            throw new Error(await response.text());
        }

        const data = await response.json();

        console.log(data);

        const table = document.getElementById("requestTable");

        table.innerHTML = "";

        let total = data.length;
        let pending = 0;
        let approved = 0;
        let rejected = 0;

        data.forEach(workflow => {

            const status = workflow.workflowStatus;

            if (status === "PENDING") pending++;
            if (status === "APPROVED") approved++;
            if (status === "REJECTED") rejected++;

            let statusClass = "";

            switch (status) {

                case "PENDING":
                    statusClass = "status-pending";
                    break;

                case "APPROVED":
                    statusClass = "status-approved";
                    break;

                case "REJECTED":
                    statusClass = "status-rejected";
                    break;
            }

            const row = document.createElement("tr");

            row.innerHTML = `

            <td>${workflow.requisition.requestId}</td>

            <td>${workflow.requisition.employee.employeeName}</td>

            <td>${workflow.requisition.itemName}</td>

            <td>${workflow.requisition.quantity}</td>

            <td>₹ ${workflow.requisition.estimatedCost}</td>

            <td class="${statusClass}">
                ${status}
            </td>

            <td>

                <button
                    class="approve-btn"
                    onclick="approveRequest(${workflow.requisition.requestId})">

                    Approve

                </button>

                <button
                    class="reject-btn"
                    onclick="rejectRequest(${workflow.requisition.requestId})">

                    Reject

                </button>

            </td>

            `;

            table.appendChild(row);

        });

        document.getElementById("totalRequests").innerText = total;
        document.getElementById("pendingRequests").innerText = pending;
        document.getElementById("approvedRequests").innerText = approved;
        document.getElementById("rejectedRequests").innerText = rejected;

    }
    catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// ===========================================
// Approve Request
// ===========================================

async function approveRequest(requestId) {

    if (!confirm("Approve this request?")) {
        return;
    }

    try {

        const response = await fetch(`${BASE_URL}/approve/${requestId}`, {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify({

                remarks: "Approved By Manager"

            })

        });

        if (response.status === 401) {

            alert("Session Expired");

            localStorage.clear();

            window.location.href = "login.html";

            return;
        }

        const message = await response.text();

        if (!response.ok) {
            throw new Error(message);
        }

        alert(message);

        loadPendingRequests();

    }
    catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// ===========================================
// Reject Request
// ===========================================

async function rejectRequest(requestId) {

    const remarks = prompt("Enter rejection remarks:");

    if (remarks === null) {
        return;
    }

    try {

        const response = await fetch(`${BASE_URL}/reject/${requestId}`, {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify({

                remarks: remarks

            })

        });

        if (response.status === 401) {

            alert("Session Expired");

            localStorage.clear();

            window.location.href = "login.html";

            return;
        }

        const message = await response.text();

        if (!response.ok) {
            throw new Error(message);
        }

        alert(message);

        loadPendingRequests();

    }
    catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// ===========================================
// Logout
// ===========================================

function logout() {

    if (confirm("Are you sure you want to logout?")) {

        localStorage.clear();

        window.location.href = "login.html";

    }

}

// ===========================================
// Auto Load
// ===========================================

window.onload = function () {

    loadPendingRequests();

};