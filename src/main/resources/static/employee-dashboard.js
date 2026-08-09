// =========================================
// Employee Dashboard (JWT Version)
// =========================================

const API = "http://localhost:9090/api/requisitions";

const token = localStorage.getItem("token");
const employeeId = localStorage.getItem("employeeId");
const employeeName = localStorage.getItem("employeeName");

// Check Login
if (!token) {
    alert("Please login first.");
    window.location.href = "login.html";
}

window.onload = function () {

    if (employeeName) {
        document.getElementById("welcomeText").innerHTML =
            "Welcome, " + employeeName;
    }

    loadDashboard();

};

// =========================================
// Load Dashboard
// =========================================

async function loadDashboard() {

    try {

        const response = await fetch(API + "/employee/" + employeeId, {

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

        let total = data.length;
        let pending = 0;
        let approved = 0;
        let rejected = 0;

        let rows = "";

        data.forEach(req => {

            if (req.status === "PENDING")
                pending++;

            else if (req.status.includes("APPROVED"))
                approved++;

            else if (req.status === "REJECTED")
                rejected++;

            rows += `

            <tr>

                <td>${req.requestId}</td>

                <td>${req.itemName}</td>

                <td>${req.quantity}</td>

                <td>₹ ${req.estimatedCost}</td>

                <td>${req.status}</td>

                <td>${req.createdDate}</td>

            </tr>

            `;

        });

        document.getElementById("myRequests").innerHTML = total;
        document.getElementById("pendingRequests").innerHTML = pending;
        document.getElementById("approvedRequests").innerHTML = approved;
        document.getElementById("rejectedRequests").innerHTML = rejected;
        document.getElementById("requestTable").innerHTML = rows;

    }
    catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// =========================================
// Logout
// =========================================

function logout() {

    if (confirm("Are you sure you want to logout?")) {

        localStorage.clear();
        window.location.href = "login.html";

    }

}

