const supplierId = localStorage.getItem("supplierId");

const API =
"http://localhost:9090/api/deliveries/supplier/" + supplierId;

window.onload = loadDeliveries;

function loadDeliveries(){

fetch(API)

.then(res=>res.json())

.then(data=>{

let rows="";

data.forEach(delivery=>{
	rows += `
	<tr>

	    <td>${delivery.deliveryId}</td>

	    <td>${delivery.purchaseOrder.poId}</td>

	    <td>${delivery.purchaseOrder.purchaseRequisition.itemName}</td>

	    <td>${delivery.receivedQuantity}</td>

	    <td>
	        <span class="badge bg-primary">
	            ${delivery.deliveryStatus}
	        </span>
	    </td>

	    <td>

	        <select id="status${delivery.deliveryId}" class="form-select"
	            ${delivery.deliveryStatus === "DELIVERED" ? "disabled" : ""}>

	            <option value="PACKED"
	                ${delivery.deliveryStatus==="PACKED"?"selected":""}>
	                PACKED
	            </option>

	            <option value="SHIPPED"
	                ${delivery.deliveryStatus==="SHIPPED"?"selected":""}>
	                SHIPPED
	            </option>

	            <option value="IN_TRANSIT"
	                ${delivery.deliveryStatus==="IN_TRANSIT"?"selected":""}>
	                IN TRANSIT
	            </option>

	            <option value="DELIVERED"
	                ${delivery.deliveryStatus==="DELIVERED"?"selected":""}>
	                DELIVERED
	            </option>

	        </select>

	        <br>

	        <button
	            class="btn btn-success btn-sm"
	            onclick="updateStatus(${delivery.deliveryId})"
	            ${delivery.deliveryStatus==="DELIVERED"?"disabled":""}>

	            Update

	        </button>

	    </td>

	</tr>
	`;


});

document.getElementById("deliveryTable").innerHTML=rows;

});

}
function updateStatus(id){

    let status = document.getElementById("status"+id).value;

    fetch("http://localhost:9090/api/deliveries/"+id+"/status",{

        method:"PUT",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({
            deliveryStatus:status
        })

    })
    .then(res=>{

        if(!res.ok){
            throw new Error("Status Update Failed");
        }

        return res.json();

    })
    .then(data=>{

        alert("Delivery Status Updated");

        loadDeliveries();

    })
    .catch(err=>{

        alert(err.message);

    });

}