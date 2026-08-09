const API = "http://localhost:9090/api/deliveries";


// Page load
window.onload = function () {
    loadDeliveries();
};


// Load all deliveries
function loadDeliveries() {

    fetch(API)
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load deliveries");
            }

            return response.json();

        })
        .then(data => {

            let rows = "";

            data.forEach(delivery => {

                rows += `
                <tr>

                    <td>${delivery.deliveryId}</td>

                    <td>
                        ${delivery.purchaseOrder 
                            ? delivery.purchaseOrder.poId 
                            : "N/A"}
                    </td>

					<td>
					${delivery.purchaseOrder &&
					 delivery.purchaseOrder.supplier
					 ? delivery.purchaseOrder.supplier.supplierName
					 : "N/A"}
					</td>


                    <td>
                        <span class="badge 
                        ${delivery.deliveryStatus === 'DELIVERED' 
                            ? 'bg-success' 
                            : 'bg-warning'}">
                            ${delivery.deliveryStatus}
                        </span>
                    </td>


                    <td>
                        ${delivery.receivedQuantity}
                    </td>


                    <td>
                        ${delivery.remarks || "-"}
                    </td>


                    <td>
                        ${delivery.deliveryDate 
                            ? delivery.deliveryDate 
                            : "-"}
                    </td>


                </tr>
                `;

            });


            document.getElementById("deliveryTable").innerHTML = rows;


        })
        .catch(error => {

            console.error("Error:", error);

            document.getElementById("deliveryTable").innerHTML =
            `
            <tr>
                <td colspan="7" class="text-center text-danger">
                    Failed to load delivery data
                </td>
            </tr>
            `;

        });

}