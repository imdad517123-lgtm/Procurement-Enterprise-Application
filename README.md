# Enterprise Procurement System

## 📌 Project Overview

The **Enterprise Procurement System** is a web-based procurement management application designed to streamline and automate the complete procurement lifecycle.

The system manages the process from **purchase requisition creation to approval, supplier selection, purchase order generation, delivery verification, inventory updates, and finance processing**.

The application is developed using **Java, Spring Boot, MySQL, HTML, CSS, and JavaScript** and follows an Agile development approach.

---

## 🚀 Key Features

* Employee authentication and authorization
* Create and manage purchase requisitions
* Manager approval workflow
* Procurement officer approval
* Supplier management
* Verified supplier selection
* Purchase order creation
* Purchase order acceptance
* Delivery management and tracking
* Delivery verification
* Inventory management
* Finance/payment workflow
* Approval history tracking
* Role-based access control
* RESTful APIs
* JWT-based security
* Exception handling and validation
* Unit testing

---

## 🛠️ Technology Stack

### Backend

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* REST APIs
* Maven
* MySQL

### Frontend

* HTML5
* CSS3
* JavaScript
* Bootstrap

### Development Tools

* Eclipse IDE
* MySQL Workbench
* Postman
* Git
* GitHub

---

## 📂 Project Structure

```text
Enterprise-Procurement-System/
│
├── src/
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   └── Procurement/
│   │   │       └── Master/
│   │   │           ├── Controller/
│   │   │           ├── Dto/
│   │   │           ├── Entity/
│   │   │           ├── Exception/
│   │   │           ├── Repository/
│   │   │           ├── Security/
│   │   │           └── Service/
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── HTML files
│   │       │   ├── CSS files
│   │       │   └── JavaScript files
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│
├── Agile-Documentation/
│   ├── Agile_Template_v0.1.xlsx
│   └── Unit_TestCases.xlsx
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── README.md
└── LICENSE
```

### Frontend Code

The frontend code is located under:

```text
src/main/resources/static/
```

It contains the application's **HTML, CSS, and JavaScript** files.

Because this is a Spring Boot application developed using Eclipse, the frontend is integrated into the Spring Boot project's `resources/static` directory.

### Backend Code

The backend code is located under:

```text
src/main/java/Procurement/Master/
```

It contains:

* Controllers
* DTOs
* Entities
* Exception handling
* Repositories
* Security configuration
* Services

---

## 🔄 Procurement Workflow

```text
Employee Login
      ↓
Create Purchase Requisition
      ↓
Manager Approval
      ↓
Procurement Officer
      ↓
Select Verified Supplier
      ↓
Generate Purchase Order
      ↓
Supplier Accepts Purchase Order
      ↓
Goods Delivery
      ↓
Delivery Verification
      ↓
Inventory Update
      ↓
Finance Processing
      ↓
Reports
```

---

## 🔐 Security

The application implements security using:

* Spring Security
* JWT authentication
* Role-based authorization
* Password encryption
* Protected REST APIs
* Authentication and authorization filters

---

## 🗄️ Database

The application uses **MySQL** as the database.

Major entities include:

* Employee
* Purchase Requisition
* Approval History
* Approval Hierarchy
* Workflow
* Supplier
* Purchase Order
* Delivery
* Inventory
* Finance

---

## 📋 Agile Documentation

All Agile-related project documentation is maintained in:

```text
Agile-Documentation/
```

The folder contains project planning and testing documentation, including:

* Agile template
* Unit test cases
* Product backlog
* User stories
* Sprint planning
* Sprint review
* Sprint retrospective

---

## ▶️ How to Run the Project

### Prerequisites

Install the following:

* Java JDK
* Maven
* MySQL
* Eclipse IDE
* Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/imdad517123-lgtm/LearningGitHub.git
```

### Step 2: Open in Eclipse

1. Open Eclipse.
2. Select **File → Import**.
3. Import the project as a Maven project.
4. Wait for Maven dependencies to download.
5. Update the database configuration in `application.properties`.

### Step 3: Configure MySQL

Create the required MySQL database and update:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

according to your local MySQL configuration.

### Step 4: Run the Application

Run the Spring Boot main application:

```text
GitHubApplication.java
```

The application will start on the configured server port.

---

## 🧪 Testing

REST APIs can be tested using **Postman**.

Unit and integration tests are located under:

```text
src/test/
```

Additional test documentation is available under:

```text
Agile-Documentation/
```

---

## 📌 Git Workflow

The project follows the standard Git workflow:

```text
Create / Modify Code
        ↓
git status
        ↓
git add .
        ↓
git commit -m "Commit message"
        ↓
git push
```

---

## 📄 License

This project is licensed under the **MIT License**.

See the [`LICENSE`](LICENSE) file for complete license information.

---

## 👨‍💻 Author

**Imdad TS**

Enterprise Procurement System developed as a full-stack Spring Boot application with an integrated frontend and Agile project documentation.
