	const API_URL = "http://localhost:8080/api/workflow";
	
	window.onload = function () {
	    loadAllWorkflows();
	};
	
	// =============================
	// Load All Workflows
	// =============================
	function loadAllWorkflows() {
	
	    fetch(API_URL)
	        .then(response => response.json())
	        .then(data => {
	            console.log("Workflows:", data);
	            displayWorkflows(data);
	        })
	        .catch(error => console.error(error));
	
	}
	// =============================
	// Load Pending
	// =============================
	function loadPendingWorkflows() {
	
	    fetch(API_URL + "/pending")
	        .then(response => response.json())
	        .then(data => displayWorkflows(data))
	        .catch(error => console.error(error));
	}
	
	// =============================
	// Load Approved
	// =============================
	function loadApprovedWorkflows() {
	
	    fetch(API_URL + "/approved")
	        .then(response => response.json())
	        .then(data => displayWorkflows(data))
	        .catch(error => console.error(error));
	}
	
	// =============================
	// Load Rejected
	// =============================
	function loadRejectedWorkflows() {
	
	    fetch(API_URL + "/rejected")
	        .then(response => response.json())
	        .then(data => displayWorkflows(data))
	        .catch(error => console.error(error));
	}
	// =============================
	// Display Table
	// =============================
	function displayWorkflows(workflows) {

	    const table = document.getElementById("workflowTable");
	    table.innerHTML = "";

	    workflows.forEach(workflow => {

	        let actionButtons = "";

	        if (workflow.workflowStatus === "PENDING") {

	            actionButtons = `
	                <td>
	                    <button onclick="selectRequest(${workflow.requisition.requestId})">
	                        Approve
	                    </button>
	                </td>

	                <td>
	                    <button onclick="selectRequest(${workflow.requisition.requestId})">
	                        Reject
	                    </button>
	                </td>
	            `;

	        } else {

	            actionButtons = `
	                <td>-</td>
	                <td>-</td>
	            `;

	        }

	        table.innerHTML += `
	            <tr>

	                <td>${workflow.workflowId}</td>

	                <td>${workflow.requisition.requestId}</td>

	                <td>${workflow.currentLevel}</td>

	                <td>${workflow.currentApprover.employeeName}</td>

	                <td>${workflow.workflowStatus}</td>

	                ${actionButtons}

	            </tr>
	        `;

	    });

	}
	// =============================
	// Select Request
	// =============================
	function selectRequest(requestId) {
	
	    document.getElementById("requestId").value = requestId;
	
	}
	
	// =============================
	// Approve
	// =============================
	function approveRequest() {
	
	    const requestId = document.getElementById("requestId").value;
	
	    if (requestId == "") {
	
	        alert("Select a request first.");
	
	        return;
	    }
	
	    const request = {
	
	        approverId: Number(document.getElementById("approverId").value),
	
	        remarks: document.getElementById("remarks").value
	
	    };
	
	    fetch(API_URL + "/approve/" + requestId, {
	
	        method: "PUT",
	
	        headers: {
	
	            "Content-Type": "application/json"
	
	        },
	
	        body: JSON.stringify(request)
	
	    })
	
	    .then(async response => {
	
	        const message = await response.text();
	
	        document.getElementById("message").innerHTML = message;
	
	        if (response.ok) {
	
	            clearWorkflowForm();
	
	            loadAllWorkflows();
	
	        }
	
	    })
	
	    .catch(error => {
	
	        document.getElementById("message").innerHTML = error.message;
	
	    });
	
	}
	
	// =============================
	// Reject
	// =============================
	function rejectRequest() {
	
	    const requestId = document.getElementById("requestId").value;
	
	    if (requestId == "") {
	
	        alert("Select a request first.");
	
	        return;
	    }
	
	    const request = {
	
	        approverId: Number(document.getElementById("approverId").value),
	
	        remarks: document.getElementById("remarks").value
	
	    };
	
	    fetch(API_URL + "/reject/" + requestId, {
	
	        method: "PUT",
	
	        headers: {
	
	            "Content-Type": "application/json"
	
	        },
	
	        body: JSON.stringify(request)
	
	    })
	
	    .then(async response => {
	
	        const message = await response.text();
	
	        document.getElementById("message").innerHTML = message;
	
	        if (response.ok) {
	
	            clearWorkflowForm();
	
	            loadAllWorkflows();
	
	        }
	
	    })
	
	    .catch(error => {
	
	        document.getElementById("message").innerHTML = error.message;
	
	    });
	
	}
	
	// =============================
	// Clear Form
	// =============================
	function clearWorkflowForm() {
	
	    document.getElementById("requestId").value = "";
	
	    document.getElementById("approverId").value = "";
	
	    document.getElementById("remarks").value = "";
	
	    document.getElementById("message").innerHTML = "";

}