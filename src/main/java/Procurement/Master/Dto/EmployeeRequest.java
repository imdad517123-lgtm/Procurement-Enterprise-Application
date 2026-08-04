package Procurement.Master.Dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeRequest {

    @NotBlank(message = "Employee Name is required")
    private String employeeName;
    @NotBlank(message = "Employee Company ID is required")
    private String employeeCompanyId;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Role is required")
    @Pattern(
        regexp = "EMPLOYEE|MANAGER|PROCUREMENT_OFFICER|FINANCE",
        message = "Role must be EMPLOYEE, MANAGER, PROCUREMENT_OFFICER or FINANCE"
    )
    private String role;
  
    public EmployeeRequest() {
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