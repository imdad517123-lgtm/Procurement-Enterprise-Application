const API = "http://localhost:9090/api/requisitions";

const token = localStorage.getItem("token");
const employeeId = localStorage.getItem("employeeId");

window.onload = function () {

    document.getElementById("employeeId").value = employeeId;

};

function saveRequest() {

    const requisition = {

        employeeId: employeeId,
        itemName: document.getElementById("itemName").value,
        quantity: document.getElementById("quantity").value,
        estimatedCost: document.getElementById("estimatedCost").value,
        justification: document.getElementById("justification").value

    };

    fetch(API, {

        method: "POST",

        headers: {

            "Content-Type": "application/json",
            "Authorization": "Bearer " + token

        },

        body: JSON.stringify(requisition)

    })

    .then(async response => {

        const message = await response.text();

        document.getElementById("message").innerHTML = message;

        if (response.ok) {

            clearForm();

            setTimeout(() => {

                window.location.href = "employee-dashboard.html";

            }, 1000);

        }

    })

    .catch(error => {

        document.getElementById("message").innerHTML = error.message;

    });

}

function clearForm() {

    document.getElementById("itemName").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("estimatedCost").value = "";
    document.getElementById("justification").value = "";
    document.getElementById("message").innerHTML = "";

}

function logout() {

    localStorage.removeItem("token");
    localStorage.removeItem("employeeId");
    localStorage.removeItem("role");
    localStorage.removeItem("email");

    window.location.href = "login.html";

}