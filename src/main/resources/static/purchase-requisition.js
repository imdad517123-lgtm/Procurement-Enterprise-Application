
const API_URL = "http://localhost:8080/api/requisitions";

window.onload = function () {
    loadRequisitions();
};

// Load all requisitions
function loadRequisitions() {

    fetch(API_URL)
        .then(response => response.json())
        .then(data => {

            let table = document.getElementById("requisitionTable");
            table.innerHTML = "";

            data.forEach(req => {

                table.innerHTML += `
                <tr>
                    <td>${req.requestId}</td>
                    <td>${req.employee.employeeId}</td>
                    <td>${req.itemName}</td>
                    <td>${req.quantity}</td>
                    <td>${req.estimatedCost}</td>
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

        })
        .catch(error => console.log(error));

}

// Save (Create / Update)
function saveRequisition() {

    const id = document.getElementById("requisitionId").value;

    const requisition = {

        employeeId: document.getElementById("employeeId").value,

        itemName: document.getElementById("itemName").value,

        quantity: document.getElementById("quantity").value,

        estimatedCost: document.getElementById("estimatedCost").value,

        justification: document.getElementById("justification").value

    };

    let url = API_URL;
    let method = "POST";

    if (id != "") {
        url += "/" + id;
        method = "PUT";
    }

    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(requisition)

    })

    .then(async response => {

        const message = await response.text();

        document.getElementById("message").innerHTML = message;

        if (response.ok) {

            clearForm();

            loadRequisitions();

        }

    })

    .catch(error => {

        document.getElementById("message").innerHTML = error.message;

    });

}

// Edit
function editRequisition(id) {

    fetch(API_URL + "/" + id)

        .then(response => response.json())

        .then(req => {

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

        });

}

// Delete
function deleteRequisition(id) {

    if (!confirm("Delete this Purchase Requisition?"))
        return;

    fetch(API_URL + "/" + id, {

        method: "DELETE"

    })

    .then(async response => {

        const message = await response.text();

        alert(message);

        loadRequisitions();

    });

}

// Clear Form
function clearForm() {

    document.getElementById("requisitionId").value = "";

    document.getElementById("employeeId").value = "";

    document.getElementById("itemName").value = "";

    document.getElementById("quantity").value = "";

    document.getElementById("estimatedCost").value = "";

    document.getElementById("justification").value = "";

    document.getElementById("message").innerHTML = "";
}