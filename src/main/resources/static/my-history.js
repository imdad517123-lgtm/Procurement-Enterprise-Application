// =========================================
// Employee Approval History
// =========================================

// API
const API =
"http://localhost:9090/api/approval-history/employee/"
   + employeeId;


// JWT
const token =
    localStorage.getItem("token");


// Employee ID
const employeeId =
    localStorage.getItem("employeeId");


// =========================================
// CHECK LOGIN
// =========================================

if (!token || !employeeId) {

    alert("Please login first.");

    window.location.href = "login.html";

}


// =========================================
// PAGE LOAD
// =========================================

window.onload = function () {

    console.log("Approval History JS Loaded");

    console.log("Employee ID:", employeeId);

    console.log(
        "API:",
        API + "/employee/" + employeeId
    );

    loadHistory();

};


// =========================================
// LOAD HISTORY
// =========================================

async function loadHistory() {

    try {

        const url =
            API + "/employee/" + employeeId;


        console.log("Calling API:", url);


        const response = await fetch(
            url,
            {
                method: "GET",

                headers: {

                    "Authorization":
                        "Bearer " + token,

                    "Content-Type":
                        "application/json"

                }
            }
        );


        // =====================================
        // STATUS
        // =====================================

        console.log(
            "HTTP STATUS:",
            response.status
        );


        if (response.status === 401) {

            alert("Session Expired");

            localStorage.clear();

            window.location.href =
                "login.html";

            return;

        }


        if (!response.ok) {

            const errorText =
                await response.text();

            console.error(
                "API ERROR:",
                errorText
            );

            throw new Error(
                "HTTP ERROR: " +
                response.status
            );

        }


        // =====================================
        // JSON DATA
        // =====================================

        const data =
            await response.json();


        console.log(
            "========== API DATA =========="
        );

        console.log(data);

        console.log(
            "=============================="
        );


        // =====================================
        // CHECK DATA
        // =====================================

        if (!Array.isArray(data)) {

            console.error(
                "API did not return an array"
            );

            return;

        }


        console.log(
            "Number of records:",
            data.length
        );


        let rows = "";


        // =====================================
        // LOOP
        // =====================================

        data.forEach(function (history) {


            console.log(
                "========== HISTORY =========="
            );

            console.log(
                "History ID:",
                history.historyId
            );

            console.log(
                "Request ID:",
                history.requestId
            );

            console.log(
                "Employee:",
                history.employeeName
            );

            console.log(
                "Approved By:",
                history.approverName
            );

            console.log(
                "Role:",
                history.approverRole
            );

            console.log(
                "Action:",
                history.action
            );

            console.log(
                "Comments:",
                history.remarks
            );

            console.log(
                "Action Date:",
                history.actionDate
            );


            // =================================
            // CREATE ROW
            // =================================

            rows += `

                <tr>

                    <td>
                        ${history.historyId ?? "-"}
                    </td>

                    <td>
                        ${history.requestId ?? "-"}
                    </td>

                    <td>
                        ${history.employeeName ?? "-"}
                    </td>

                    <td>
                        ${history.approverName ?? "-"}
                    </td>

                    <td>
                        ${history.approverRole ?? "-"}
                    </td>

                    <td>
                        ${history.action ?? "-"}
                    </td>

                    <td>
                        ${history.remarks ?? "-"}
                    </td>

                    <td>
                        ${formatDate(history.actionDate)}
                    </td>

                </tr>

            `;

        });


        // =====================================
        // DISPLAY
        // =====================================

        const table =
            document.getElementById(
                "historyTable"
            );


        if (!table) {

            console.error(
                "historyTable element not found"
            );

            return;

        }


        table.innerHTML = rows;

    }


    catch (error) {

        console.error(
            "APPROVAL HISTORY ERROR:",
            error
        );

        alert(error.message);

    }

}


// =========================================
// FORMAT DATE
// =========================================

function formatDate(date) {

    if (!date) {

        return "-";

    }

    return new Date(date)
        .toLocaleString();

}


// =========================================
// LOGOUT
// =========================================

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";

}