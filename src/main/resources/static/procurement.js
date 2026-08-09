// =======================================
// PROCUREMENT OFFICER DASHBOARD JS
// =======================================

const API = "http://localhost:9090/api/procurement";
const SUPPLIER_API = "http://localhost:9090/api/suppliers";
const PO_API = "http://localhost:9090/api/purchase-orders";
const DELIVERY_API = "http://localhost:9090/api/deliveries";
// =======================================
// PAGE LOAD
// =======================================

window.onload = function () {

    loadApprovedRequests();

    loadSuppliers();

    loadDeliveryTracking();

};

// =======================================
// LOAD PROCUREMENT REQUESTS
// =======================================

function loadApprovedRequests() {

    fetch(API + "/pending")
        .then(response => response.json())
        .then(data => {

            let rows = "";

            data.forEach(req => {

                let action = "";

                if (req.status === "MANAGER_APPROVED") {

                    action = `
                        <button class="btn btn-success btn-sm"
                                onclick="approveRequest(${req.requestId})">
                            Approve
                        </button>

                        <button class="btn btn-danger btn-sm"
                                onclick="rejectRequest(${req.requestId})">
                            Reject
                        </button>
                    `;

                } else if (req.status === "PROCUREMENT_APPROVED") {

                    action = `
                        <button class="btn btn-primary btn-sm"
                                onclick="openSupplier(${req.requestId})">
                            Create PO
                        </button>
                    `;
                }

                rows += `
                <tr>
                    <td>${req.requestId}</td>
                    <td>${req.employee.employeeName}</td>
                    <td>${req.itemName}</td>
                    <td>${req.quantity}</td>
                    <td>₹ ${req.estimatedCost}</td>
                    <td>${req.status}</td>
                    <td>${action}</td>
                </tr>`;
            });

            document.getElementById("requestTable").innerHTML = rows;

        })
        .catch(error => console.log(error));
}

// =======================================
// LOAD VERIFIED SUPPLIERS
// =======================================

function loadSuppliers() {

    fetch(SUPPLIER_API + "/verified")
        .then(response => response.json())
        .then(data => {

            let rows = "";

            let dropdown = document.getElementById("supplierId");

            dropdown.innerHTML =
                "<option value=''>Select Supplier</option>";

            data.forEach(supplier => {

                rows += `
                <tr>
                    <td>${supplier.supplierId}</td>
                    <td>${supplier.supplierName}</td>
                    <td>${supplier.email}</td>
                    <td>${supplier.phone}</td>
                    <td>${supplier.city}</td>
                    <td>${supplier.gstNumber}</td>
                    <td>${supplier.status}</td>
                </tr>`;

                dropdown.innerHTML += `
                <option value="${supplier.supplierId}">
                    ${supplier.supplierName}
                </option>`;
            });

            document.getElementById("supplierTable").innerHTML = rows;

        })
        .catch(error => console.log(error));
}

// =======================================
// PROCUREMENT APPROVE
// =======================================

function approveRequest(id) {

    fetch(API + "/approve/" + id, {
        method: "PUT"
    })
        .then(response => response.text())
        .then(message => {

            alert(message);

            loadApprovedRequests();

        });
}

// =======================================
// PROCUREMENT REJECT
// =======================================

function rejectRequest(id) {

    fetch(API + "/reject/" + id, {
        method: "PUT"
    })
        .then(response => response.text())
        .then(message => {

            alert(message);

            loadApprovedRequests();

        });
}

// =======================================
// OPEN SUPPLIER MODAL
// =======================================

function openSupplier(requestId) {

    document.getElementById("requestId").value = requestId;

    document.getElementById("supplierId").value = "";
    document.getElementById("supplierName").value = "";
    document.getElementById("supplierEmail").value = "";

    let modal = new bootstrap.Modal(
        document.getElementById("supplierModal")
    );

    modal.show();
}

// =======================================
// SHOW SUPPLIER DETAILS
// =======================================

function showSupplierEmail() {

    let supplierId =
        document.getElementById("supplierId").value;

    if (supplierId === "") {

        document.getElementById("supplierName").value = "";
        document.getElementById("supplierEmail").value = "";

        return;
    }

    fetch(SUPPLIER_API + "/" + supplierId)
        .then(response => response.json())
        .then(data => {

            document.getElementById("supplierName").value =
                data.supplierName;

            document.getElementById("supplierEmail").value =
                data.email;

        })
        .catch(error => console.log(error));
}

// =======================================
// CREATE PURCHASE ORDER
// =======================================

function createPO() {


let requestId =
document.getElementById("requestId").value;


let supplierId =
document.getElementById("supplierId").value;



if(requestId === "" || supplierId === ""){

alert("Please select supplier");

return;

}



let data = {

requestId:Number(requestId),

supplierId:Number(supplierId)

};



fetch(PO_API + "/create",{

method:"POST",

headers:{

"Content-Type":"application/json"

},

body:JSON.stringify(data)

})

.then(response=>response.text())

.then(message=>{


alert(message);



bootstrap.Modal
.getInstance(
document.getElementById("supplierModal")
)
.hide();



loadApprovedRequests();


})

.catch(error=>{


console.log(error);

alert("PO creation failed");


});


}
// =======================================
// LOAD DELIVERY TRACKING
// =======================================

function loadDeliveryTracking() {

    fetch(DELIVERY_API)

        .then(response => {

            if (!response.ok) {
                throw new Error("Unable to load deliveries");
            }

            return response.json();

        })

        .then(data => {

            console.log("Delivery Data:", data);

            let rows = "";

            if (!data || data.length === 0) {

                rows = `
                    <tr>
                        <td colspan="5"
                            class="text-center text-danger">

                            No Delivery Information Available

                        </td>
                    </tr>
                `;

            } else {

                data.forEach(delivery => {

                    rows += `
                        <tr>

                            <td>
                                ${delivery.deliveryId}
                            </td>

                            <td>
                                ${delivery.purchaseOrder
                                    ? delivery.purchaseOrder.poId
                                    : "-"}
                            </td>

                            <td>
                                <span class="badge bg-primary">
                                    ${delivery.deliveryStatus}
                                </span>
                            </td>

                            <td>
                                ${delivery.receivedQuantity || 0}
                            </td>

                            <td>
                                ${delivery.remarks || "-"}
                            </td>

                        </tr>
                    `;

                });

            }

            document.getElementById("deliveryTable").innerHTML =
                rows;

        })

        .catch(error => {

            console.error(
                "Delivery Tracking Error:",
                error
            );

            document.getElementById("deliveryTable").innerHTML = `

                <tr>
                    <td colspan="5"
                        class="text-center text-danger">

                        Unable to load delivery information

                    </td>
                </tr>

            `;

        });
}
// =======================================
// LOGOUT
// =======================================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";
}