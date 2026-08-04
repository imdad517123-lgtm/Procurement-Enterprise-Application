const API = "http://localhost:8080/api/manager/requests";

window.onload = function () {

    loadRequests();

};

// Load Manager Requests

function loadRequests(){

    fetch(API)

    .then(response => response.json())

    .then(data =>{

        let rows="";

        data.forEach(request=>{

            rows +=`

            <tr>

                <td>${request.requestId}</td>

                <td>${request.employee.employeeName}</td>

                <td>${request.itemName}</td>

                <td>${request.quantity}</td>

                <td>${request.estimatedCost}</td>

                <td>${request.status}</td>

                <td>

                    <button
                    class="approve"
                    onclick="approveRequest(${request.requestId})">

                    Approve

                    </button>

                    <button
                    class="reject"
                    onclick="rejectRequest(${request.requestId})">

                    Reject

                    </button>

                </td>

            </tr>

            `;

        });

        document.getElementById("requestTable").innerHTML=rows;

    })

    .catch(error=>{

        console.log(error);

    });

}

// Approve

function approveRequest(id){

    fetch("http://localhost:8080/api/manager/approve/"+id,{

        method:"PUT"

    })

    .then(response=>response.text())

    .then(message=>{

        alert(message);

        loadRequests();

    });

}

// Reject

function rejectRequest(id){

    fetch("http://localhost:8080/api/manager/reject/"+id,{

        method:"PUT"

    })

    .then(response=>response.text())

    .then(message=>{

        alert(message);

        loadRequests();

    });

}

// Logout

function logout(){

    localStorage.clear();

    window.location.href="login.html";

}