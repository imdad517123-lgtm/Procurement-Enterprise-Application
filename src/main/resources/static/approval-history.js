// =========// ============================================
// APPROVAL HISTORY
// ============================================
const employeeId = localStorage.getItem("employeeId");
const token = localStorage.getItem("token");

const API = `http://localhost:9090/api/approval-history/employee/${employeeId}`;

console.log(employeeId);
console.log(API);
// ============================================
// PAGE LOAD
// ============================================

window.addEventListener("load", function () {

    console.log("======================================");
    console.log("APPROVAL HISTORY JS LOADED");
    console.log("Employee ID:", employeeId);
    console.log("Token exists:", !!token);
    console.log("API:", API);
    console.log("======================================");

    loadHistory();

});


// ============================================
// LOAD HISTORY
// ============================================

async function loadHistory() {

    try {

        const response = await fetch(API, {

            method: "GET",

            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }

        });


        console.log("HTTP STATUS:", response.status);


        if (!response.ok) {

            const errorText = await response.text();

            console.error("SERVER ERROR:", errorText);

            throw new Error(
                "HTTP ERROR: " + response.status
            );

        }


        const data = await response.json();


        // ========================================
        // MOST IMPORTANT DEBUG
        // ========================================

        console.log("======================================");
        console.log("BROWSER API RESPONSE");
        console.log(data);
        console.log("======================================");

        console.log("IS ARRAY:", Array.isArray(data));

        console.log("NUMBER OF RECORDS:", data.length);


        if (data.length > 0) {

            console.log("FIRST OBJECT:");
            console.log(data[0]);

            console.log("historyId:",
                data[0].historyId);

            console.log("requestId:",
                data[0].requestId);

            console.log("employeeName:",
                data[0].employeeName);

            console.log("approverName:",
                data[0].approverName);

            console.log("approverRole:",
                data[0].approverRole);

            console.log("action:",
                data[0].action);

            console.log("remarks:",
                data[0].remarks);

            console.log("actionDate:",
                data[0].actionDate);
        }


        // ========================================
        // TABLE
        // ========================================

        const table =
            document.getElementById("historyTable");


        if (!table) {

            console.error(
                "historyTable NOT FOUND"
            );

            return;

        }


        table.innerHTML = "";


        if (!Array.isArray(data) || data.length === 0) {

            table.innerHTML = `
                <tr>
                    <td colspan="8">
                        No Approval History Found
                    </td>
                </tr>
            `;

            return;

        }


        // ========================================
        // LOOP
        // ========================================

        data.forEach(function (history) {

            console.log("CURRENT HISTORY:", history);


            // ====================================
            // EXACT DTO FIELD NAMES
            // ====================================

            const historyId =
                history.historyId;

            const requestId =
                history.requestId;

            const employeeName =
                history.employeeName;

            const approvedBy =
                history.approverName;

            const role =
                history.approverRole;

            const action =
                history.action;

            const comments =
                history.remarks;

            const actionDate =
                history.actionDate;


            // ====================================
            // CREATE ROW
            // ====================================

            table.innerHTML += `

                <tr>

                    <td>${historyId}</td>

                    <td>${requestId}</td>

                    <td>${employeeName}</td>

                    <td>${approvedBy}</td>

                    <td>${role}</td>

                    <td>${action}</td>

                    <td>${comments}</td>

                    <td>${formatDate(actionDate)}</td>

                </tr>

            `;

        });

    }


    catch (error) {

        console.error(
            "APPROVAL HISTORY ERROR:",
            error
        );

    }

}


// ============================================
// DATE
// ============================================

function formatDate(date) {

    if (!date) {

        return "-";

    }

    return new Date(date).toLocaleString();

}


// ============================================
// LOGOUT
// ============================================

function logout() {

    if (confirm("Are you sure you want to logout?")) {

        localStorage.clear();

        window.location.href = "login.html";

    }

}