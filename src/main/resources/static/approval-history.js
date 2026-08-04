const API_URL = "http://localhost:8080/api/workflow";

async function loadHistory() {

    const requestId = document.getElementById("requestId").value.trim();
    const table = document.getElementById("historyTable");
    const message = document.getElementById("message");

    table.innerHTML = "";
    message.innerHTML = "";

    if (requestId === "") {
        message.innerHTML = "Please enter Purchase Requisition ID.";
        message.className = "error";
        return;
    }

    try {

        const response = await fetch(`${API_URL}/history/${requestId}`);

        if (!response.ok) {
            throw new Error("Unable to fetch approval history.");
        }

        const historyList = await response.json();

        console.log(historyList);

        if (historyList.length === 0) {
            message.innerHTML = "No approval history found.";
            message.className = "error";
            return;
        }

        historyList.forEach(history => {

            let approverId = "";
            let approverName = "";

            // If approver is returned as an object
            if (history.approver) {
                approverId = history.approver.employeeId;
                approverName = history.approver.employeeName;
            }
            // If approverId and approverName are returned directly
            else {
                approverId = history.approverId;
                approverName = history.approverName;
            }

            const row = `
                <tr>
                    <td>${history.historyId}</td>
                    <td>${approverId}</td>
                    <td>${approverName}</td>
                    <td>${history.action}</td>
                    <td>${history.remarks}</td>
                    <td>${history.actionDate}</td>
                </tr>
            `;

            table.innerHTML += row;
        });

        message.innerHTML = historyList.length + " record(s) found.";
        message.className = "success";

    } catch (error) {
        console.error(error);
        message.innerHTML = error.message;
        message.className = "error";
    }
}

function clearHistory() {

    document.getElementById("requestId").value = "";
    document.getElementById("historyTable").innerHTML = "";
    document.getElementById("message").innerHTML = "";
}