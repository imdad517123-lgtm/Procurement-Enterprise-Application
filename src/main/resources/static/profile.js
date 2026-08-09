const employeeId = localStorage.getItem("employeeId");
const token = localStorage.getItem("token");

const API = "http://localhost:9090/api/employees/" + employeeId;

window.onload = loadProfile;

async function loadProfile(){

    try{

        const response = await fetch(API,{

            method:"GET",

            headers:{
                "Authorization":"Bearer "+token
            }

        });

        if(!response.ok){

            throw new Error("Unable to load profile");

        }

        const emp = await response.json();

        document.getElementById("employeeId").innerHTML = emp.employeeId;
        document.getElementById("companyId").innerHTML = emp.employeeCompanyId;
        document.getElementById("employeeName").innerHTML = emp.employeeName;
        document.getElementById("email").innerHTML = emp.email;
        document.getElementById("department").innerHTML = emp.department;
        document.getElementById("role").innerHTML = emp.role;

    }

    catch(error){

        alert(error.message);

    }

}

function logout(){

    localStorage.clear();

    window.location.href="login.html";

}