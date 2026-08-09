// =====================================
// SUPPLIER PURCHASE ORDERS
// =====================================

const supplierId = localStorage.getItem("supplierId");
const token = localStorage.getItem("token");


// =====================================
// LOGIN CHECK
// =====================================

if (!supplierId) {

    alert("Please Login");

    window.location.href = "login.html";
}


// =====================================
// API
// =====================================

const API =
    "http://localhost:9090/api/purchase-orders/supplier/"
    + supplierId;


// =====================================
// PAGE LOAD
// =====================================

window.onload = function () {

    loadPurchaseOrders();

};


// =====================================
// LOAD PURCHASE ORDERS
// =====================================

function loadPurchaseOrders() {

    console.log("Supplier ID:", supplierId);
    console.log("API:", API);

    fetch(API, {

        method: "GET",

        headers: {

            "Authorization": "Bearer " + token,

            "Content-Type": "application/json"

        }

    })

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Unable to load Purchase Orders"
            );

        }

        return response.json();

    })

    .then(data => {

        console.log("Purchase Orders:", data);

        let rows = "";


        // =====================================
        // NO DATA
        // =====================================

        if (!data || data.length === 0) {

            rows = `
                <tr>

                    <td colspan="8"
                        class="text-center text-danger">

                        No Purchase Orders Found

                    </td>

                </tr>
            `;

        }


        // =====================================
        // DATA
        // =====================================

        else {

            data.forEach(po => {


                // =================================
                // ACTION
                // =================================

                let action = "";


                // =================================
                // SENT TO SUPPLIER
                // =================================

                if (po.status === "SENT_TO_SUPPLIER") {

                    action = `

                        <button
                            class="btn btn-success btn-sm"
                            onclick="acceptPO(${po.poId})">

                            Accept

                        </button>

                        <button
                            class="btn btn-danger btn-sm"
                            onclick="rejectPO(${po.poId})">

                            Reject

                        </button>

                    `;

                }


                // =================================
                // SUPPLIER ACCEPTED
                // =================================

                else if (
                    po.status === "SUPPLIER_ACCEPTED"
                ) {

                    action = `

                        <button
                            class="btn btn-primary btn-sm"
                            onclick="deliverPO(${po.poId})">

                            Delivered

                        </button>

                    `;

                }


                // =================================
                // OTHER STATUS
                // =================================

                else {

                    action = `

                        <span class="badge bg-secondary">

                            ${po.status}

                        </span>

                    `;

                }


                // =================================
                // REQUEST ID
                // =================================

                let requestId = "-";

                if (po.requestId) {

                    requestId = po.requestId;

                }

                else if (
                    po.purchaseRequisition &&
                    po.purchaseRequisition.requestId
                ) {

                    requestId =
                        po.purchaseRequisition.requestId;

                }


                // =================================
                // TABLE ROW
                // =================================

                rows += `

                    <tr>

                        <td>
                            ${po.poId || "-"}
                        </td>

                        <td>
                            ${requestId}
                        </td>

                        <td>
                            ${po.itemName || "-"}
                        </td>

                        <td>
                            ${po.quantity || "-"}
                        </td>

                        <td>
                            ${po.estimatedCost || "-"}
                        </td>

                        <td>
                            ${po.poDate || "-"}
                        </td>

                        <td>
                            ${po.status || "-"}
                        </td>

                        <td>
                            ${action}
                        </td>

                    </tr>

                `;

            });

        }


        // =====================================
        // DISPLAY
        // =====================================

        document.getElementById("poTable").innerHTML =
            rows;

    })

    .catch(error => {

        console.error(
            "Purchase Order Error:",
            error
        );

        document.getElementById("poTable").innerHTML = `

            <tr>

                <td colspan="8"
                    class="text-center text-danger">

                    ${error.message}

                </td>

            </tr>

        `;

    });

}


// =====================================
// ACCEPT PURCHASE ORDER
// =====================================

function acceptPO(poId) {

    if (!confirm(
        "Do you want to accept this Purchase Order?"
    )) {

        return;

    }


    fetch(
        "http://localhost:9090/api/purchase-orders/"
        + poId
        + "/accept",
        {

            method: "PUT",

            headers: {

                "Authorization":
                    "Bearer " + token,

                "Content-Type":
                    "application/json"

            }

        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Failed to accept Purchase Order"
            );

        }

        return response.text();

    })

    .then(message => {

        alert(
            message ||
            "Purchase Order accepted successfully"
        );

        loadPurchaseOrders();

    })

    .catch(error => {

        console.error(error);

        alert(error.message);

    });

}


// =====================================
// REJECT PURCHASE ORDER
// =====================================

function rejectPO(poId) {

    if (!confirm(
        "Do you want to reject this Purchase Order?"
    )) {

        return;

    }


    fetch(
        "http://localhost:9090/api/purchase-orders/"
        + poId
        + "/reject",
        {

            method: "PUT",

            headers: {

                "Authorization":
                    "Bearer " + token,

                "Content-Type":
                    "application/json"

            }

        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Failed to reject Purchase Order"
            );

        }

        return response.text();

    })

    .then(message => {

        alert(
            message ||
            "Purchase Order rejected successfully"
        );

        loadPurchaseOrders();

    })

    .catch(error => {

        console.error(error);

        alert(error.message);

    });

}


// =====================================
// DELIVER PURCHASE ORDER
// =====================================

function deliverPO(poId) {

    if (!confirm(
        "Confirm that the goods have been delivered?"
    )) {

        return;

    }


    fetch(
        "http://localhost:9090/api/purchase-orders/"
        + poId
        + "/deliver",
        {

            method: "PUT",

            headers: {

                "Authorization":
                    "Bearer " + token,

                "Content-Type":
                    "application/json"

            }

        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Failed to update delivery status"
            );

        }

        return response.text();

    })

    .then(message => {

        alert(
            message ||
            "Purchase Order marked as delivered"
        );

        loadPurchaseOrders();

    })

    .catch(error => {

        console.error(error);

        alert(error.message);

    });

}


// =====================================
// LOGOUT
// =====================================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";

}