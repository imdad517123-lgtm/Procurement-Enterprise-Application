// ============================================
// Workflow JavaScript (JWT Version)
// ============================================

const API_URL = "http://localhost:9090/api/workflow";

const token = localStorage.getItem("token");

if (!token) {
    alert("Please login first.");
    window.location.href = "login.html";
}

// ============================================
// Load Pending Workflows
// ============================================

window.onload = function () {
    loadPendingWorkflows();
};

async function loadPendingWorkflows() {

    try {

        const response = await fetch(API_URL + "/pending", {

            method: "GET",

            headers: {
                "Authorization": "Bearer " + token
            }

        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        const workflows = await response.json();

        displayWorkflows(workflows);

    } catch (error) {

        console.error(error);
        alert(error.message);

    }

}

// ============================================
// Display Workflows
// ============================================

function displayWorkflows(workflows) {

    const table = document.getElementById("workflowTable");

    table.innerHTML = "";

    workflows.forEach(workflow => {

        table.innerHTML += `

        <tr>

            <td>${workflow.workflowId}</td>

            <td>${workflow.requisition.requestId}</td>

            <td>${workflow.currentLevel}</td>

            <td>${workflow.currentApprover.employeeName}</td>

            <td>${workflow.workflowStatus}</td>

            <td>

                <button onclick="approveRequest(${workflow.requisition.requestId})">
                    Approve
                </button>

            </td>

            <td>

                <button onclick="rejectRequest(${workflow.requisition.requestId})">
                    Reject
                </button>

            </td>

        </tr>

        `;

    });

}

// ============================================
// Approve
// ============================================

async function approveRequest(requestId) {

    const remarks = prompt("Enter approval remarks");

    if (remarks == null)
        return;

    try {

        const response = await fetch(API_URL + "/approve/" + requestId, {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify({

                remarks: remarks

            })

        });

        const message = await response.text();

        alert(message);

        loadPendingWorkflows();

    } catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// ============================================
// Reject
// ============================================

async function rejectRequest(requestId) {

    const remarks = prompt("Enter rejection remarks");

    if (remarks == null)
        return;

    try {

        const response = await fetch(API_URL + "/reject/" + requestId, {

            method: "PUT",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify({

                remarks: remarks

            })

        });

        const message = await response.text();

        alert(message);

        loadPendingWorkflows();

    } catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// ============================================
// Logout
// ============================================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";

}