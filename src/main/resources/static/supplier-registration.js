const API =
    "http://localhost:9090/api/suppliers/register";


document
    .getElementById("supplierForm")
    .addEventListener("submit", function (event) {

        event.preventDefault();

        registerSupplier();

    });


// =====================================
// REGISTER SUPPLIER
// =====================================

function registerSupplier() {

    clearErrors();

    const supplierName =
        document.getElementById("supplierName").value.trim();

    const email =
        document.getElementById("email").value.trim();

    const password =
        document.getElementById("password").value.trim();

    const phone =
        document.getElementById("phone").value.trim();

    const gstNumber =
        document.getElementById("gstNumber").value
            .trim()
            .toUpperCase();

    const panNumber =
        document.getElementById("panNumber").value
            .trim()
            .toUpperCase();

    const city =
        document.getElementById("city").value.trim();


    let valid = true;


    // =====================================
    // SUPPLIER NAME
    // =====================================

    if (supplierName === "") {

        showError(
            "supplierNameError",
            "Supplier Name is required"
        );

        valid = false;
    }


    // =====================================
    // EMAIL
    // =====================================

    const emailPattern =
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/;


    if (email === "") {

        showError(
            "emailError",
            "Email is required"
        );

        valid = false;

    } else if (!emailPattern.test(email)) {

        showError(
            "emailError",
            "Enter a valid email address"
        );

        valid = false;
    }


    // =====================================
    // PASSWORD
    // =====================================

    if (password === "") {

        showError(
            "passwordError",
            "Password is required"
        );

        valid = false;

    } else if (password.length < 8) {

        showError(
            "passwordError",
            "Password must contain at least 8 characters"
        );

        valid = false;
    }


    // =====================================
    // PHONE
    // =====================================

    const phonePattern =
        /^[6-9][0-9]{9}$/;


    if (phone === "") {

        showError(
            "phoneError",
            "Phone number is required"
        );

        valid = false;

    } else if (!phonePattern.test(phone)) {

        showError(
            "phoneError",
            "Enter a valid 10 digit phone number"
        );

        valid = false;
    }


    // =====================================
    // GST
    // =====================================

    const gstPattern =
        /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z]$/;


    if (gstNumber === "") {

        showError(
            "gstError",
            "GST Number is required"
        );

        valid = false;

    } else if (!gstPattern.test(gstNumber)) {

        showError(
            "gstError",
            "Invalid GST Number"
        );

        valid = false;
    }


    // =====================================
    // PAN
    // =====================================

    const panPattern =
        /^[A-Z]{5}[0-9]{4}[A-Z]$/;


    if (panNumber === "") {

        showError(
            "panError",
            "PAN Number is required"
        );

        valid = false;

    } else if (!panPattern.test(panNumber)) {

        showError(
            "panError",
            "Invalid PAN Number"
        );

        valid = false;
    }


    // =====================================
    // CITY
    // =====================================

    if (city === "") {

        showError(
            "cityError",
            "City is required"
        );

        valid = false;
    }


    // =====================================
    // STOP IF INVALID
    // =====================================

    if (!valid) {
        return;
    }


    // =====================================
    // REQUEST DATA
    // =====================================

    const supplierData = {

        supplierName: supplierName,

        email: email,

        password: password,

        phone: phone,

        city: city,

        gstNumber: gstNumber,

        panNumber: panNumber

    };


    console.log(
        "Supplier Request:",
        supplierData
    );


    const button =
        document.getElementById("registerButton");

    button.disabled = true;

    button.innerText = "Registering...";


    // =====================================
    // API CALL
    // =====================================

    fetch(API, {

        method: "POST",

        headers: {

            "Content-Type": "application/json"

        },

        body: JSON.stringify(supplierData)

    })

    .then(async response => {

        const text = await response.text();

        let data;

        try {

            data = JSON.parse(text);

        } catch {

            data = text;

        }


        if (!response.ok) {

            throw new Error(
                getErrorMessage(data)
            );
        }


        return data;

    })


    // =====================================
    // SUCCESS
    // =====================================

    .then(data => {

        console.log(
            "Supplier Registration Response:",
            data
        );


        showMessage(
            "Supplier registered successfully!",
            "success"
        );


        document
            .getElementById("supplierForm")
            .reset();


        setTimeout(() => {

            window.location.href =
                "login.html";

        }, 2000);

    })


    // =====================================
    // ERROR
    // =====================================

    .catch(error => {

        console.error(
            "Supplier Registration Error:",
            error
        );


        showMessage(
            error.message ||
            "Supplier registration failed",
            "error"
        );

    })


    // =====================================
    // FINALLY
    // =====================================

    .finally(() => {

        button.disabled = false;

        button.innerText =
            "Register Supplier";

    });
}


// =====================================
// SHOW FIELD ERROR
// =====================================

function showError(id, message) {

    document
        .getElementById(id)
        .innerText = message;
}


// =====================================
// CLEAR ERRORS
// =====================================

function clearErrors() {

    const errors =
        document.querySelectorAll("small");

    errors.forEach(error => {

        error.innerText = "";

    });


    document
        .getElementById("message")
        .innerText = "";
}


// =====================================
// SHOW MESSAGE
// =====================================

function showMessage(message, type) {

    const messageElement =
        document.getElementById("message");

    messageElement.innerText = message;

    messageElement.className = type;
}


// =====================================
// GET BACKEND ERROR
// =====================================

function getErrorMessage(data) {

    if (typeof data === "string") {

        return data ||
            "Supplier registration failed";
    }


    if (data.message) {

        return data.message;
    }


    if (data.error) {

        return data.error;
    }


    return "Supplier registration failed";
}