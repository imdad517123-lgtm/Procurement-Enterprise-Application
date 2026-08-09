package Procurement.Master.Dto;


public class LoginResponse {

    private String message;
    private Long employeeId;
    private Long supplierId;
    private String supplierName;
    private String employeeCompanyId;
    private String employeeName;
    private String email;
    private String role;
    private String token;
    // Default Constructor
    public LoginResponse() {
    }

    // Parameterized Constructor
    public LoginResponse(String message,
                         Long employeeId,
                         String employeeCompanyId,
                         String employeeName,
                         String email,
                         String role) {

        this.message = message;
        this.employeeId = employeeId;
        this.employeeCompanyId = employeeCompanyId;
        this.employeeName = employeeName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getToken(){

    	return token;

    	}


    	public void setToken(String token){

    	this.token=token;

    	}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCompanyId() {
        return employeeCompanyId;
    }

    public void setEmployeeCompanyId(String employeeCompanyId) {
        this.employeeCompanyId = employeeCompanyId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}