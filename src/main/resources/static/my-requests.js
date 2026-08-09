const employeeId = localStorage.getItem("employeeId");
const token = localStorage.getItem("token");

const API = "http://localhost:9090/api/requisitions/employee/" + employeeId;


window.onload = loadRequests;


function loadRequests(){

    fetch(API, {

        method:"GET",

        headers:{
            "Authorization":"Bearer " + token,
            "Content-Type":"application/json"
        }

    })

    .then(response => {

        if(response.status === 401){

            alert("Session expired. Login again.");

            localStorage.clear();

            window.location.href="login.html";

            return;
        }

        return response.json();

    })

    .then(data=>{


        let rows="";


        data.forEach(req=>{


            rows += `

            <tr>

            <td>${req.requestId}</td>

            <td>${req.itemName}</td>

            <td>${req.quantity}</td>

            <td>₹ ${req.estimatedCost}</td>

            <td>${req.status}</td>

            <td>${req.createdDate}</td>

            </tr>

            `;


        });


        document.getElementById("requestTable").innerHTML = rows;


    })

    .catch(error=>{

        console.log(error);

    });

}