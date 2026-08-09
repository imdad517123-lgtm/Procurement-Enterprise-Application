
// =========================================
// Purchase Requisition (JWT Version)
// =========================================

const API_URL = "http://localhost:9090/api/requisitions";

const token = localStorage.getItem("token");

// Check Login
if (!token) {

    alert("Please login first.");

    window.location.href = "login.html";

}

window.onload = function () {

    loadRequisitions();

};

// =========================================
// Load All Requisitions
// =========================================

async function loadRequisitions() {

    try {

        const response = await fetch(API_URL, {

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

        let table = document.getElementById("requisitionTable");

        table.innerHTML = "";

        data.forEach(req => {

            table.innerHTML += `

            <tr>

                <td>${req.requestId}</td>

                <td>${req.employee.employeeId}</td>

                <td>${req.itemName}</td>

                <td>${req.quantity}</td>

                <td>₹ ${req.estimatedCost}</td>

                <td>${req.status}</td>

                <td>${req.createdDate}</td>

                <td>

                    <button onclick="editRequisition(${req.requestId})">

                        Edit

                    </button>

                </td>

                <td>

                    <button onclick="deleteRequisition(${req.requestId})">

                        Delete

                    </button>

                </td>

            </tr>

            `;

        });

    }
    catch (error) {

        console.error(error);

        alert(error.message);

    }

}

// =========================================
// Save (Create / Update)
// =========================================

async function saveRequisition() {

    const id = document.getElementById("requestId").value;

    const requisition = {

        employeeId: document.getElementById("employeeId").value,

        itemName: document.getElementById("itemName").value,

        quantity: document.getElementById("quantity").value,

        estimatedCost: document.getElementById("estimatedCost").value,

        justification: document.getElementById("justification").value

    };

    let url = API_URL;
    let method = "POST";

    if (id !== "") {

        url += "/" + id;

        method = "PUT";

    }

    try {

        const response = await fetch(url, {

            method: method,

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(requisition)

        });

        if (response.status === 401) {

            alert("Session Expired");

            localStorage.clear();

            window.location.href = "login.html";

            return;

        }

        const message = await response.text();

        document.getElementById("message").innerHTML = message;

        if (response.ok) {

            clearForm();

            loadRequisitions();

        }

    }
    catch (error) {

        document.getElementById("message").innerHTML = error.message;

    }

}

// =========================================
// Edit Requisition
// =========================================

async function editRequisition(id) {

    try {

        const response = await fetch(API_URL + "/" + id, {

            method: "GET",

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error(await response.text());

        }

        const req = await response.json();

        document.getElementById("requisitionId").value = req.requestId;

        document.getElementById("employeeId").value =
            req.employee.employeeId;

        document.getElementById("itemName").value =
            req.itemName;

        document.getElementById("quantity").value =
            req.quantity;

        document.getElementById("estimatedCost").value =
            req.estimatedCost;

        document.getElementById("justification").value =
            req.justification;

    }
    catch (error) {

        alert(error.message);

    }

}

// =========================================
// Delete Requisition
// =========================================

async function deleteRequisition(id) {

    if (!confirm("Delete this Purchase Requisition?")) {

        return;

    }

    try {

        const response = await fetch(API_URL + "/" + id, {

            method: "DELETE",

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error(await response.text());

        }

        const message = await response.text();

        alert(message);

        loadRequisitions();

    }
    catch (error) {

        alert(error.message);

    }

}

// =========================================
// Clear Form
// =========================================

function clearForm() {

    document.getElementById("requestId").value = "";

    document.getElementById("employeeId").value = "";

    document.getElementById("itemName").value = "";

    document.getElementById("quantity").value = "";

    document.getElementById("estimatedCost").value = "";

    document.getElementById("justification").value = "";

    document.getElementById("message").innerHTML = "";

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