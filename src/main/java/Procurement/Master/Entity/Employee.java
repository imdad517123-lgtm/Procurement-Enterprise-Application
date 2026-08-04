package Procurement.Master.Entity;


import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(nullable = false, unique = true)
    private String employeeCompanyId;
    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String password;

    @OneToMany(
    	    mappedBy = "employee",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    @JsonIgnore
    private List<PurchaseRequisition> requisitions;

    public Employee() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PurchaseRequisition> getRequisitions() {
        return requisitions;
    }

    public void setRequisitions(List<PurchaseRequisition> requisitions) {
        this.requisitions = requisitions;
    }
    public String getEmployeeCompanyId() {
        return employeeCompanyId;
    }

    public void setEmployeeCompanyId(String employeeCompanyId) {
        this.employeeCompanyId = employeeCompanyId;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}