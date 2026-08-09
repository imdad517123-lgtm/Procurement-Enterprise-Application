package Procurement.Master.Dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SupplierRequest {

    @NotBlank(message = "Supplier name is required")
    @Size(min = 3, max = 100,
          message = "Supplier name must be between 3 and 100 characters")
    private String supplierName;


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;


    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Phone number must be a valid 10 digit number"
    )
    private String phone;


    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50,
          message = "City must be between 2 and 50 characters")
    private String city;


    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100,
          message = "Password must contain at least 8 characters")
    private String password;


    @NotBlank(message = "GST number is required")
    @Pattern(
        regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z]$",
        message = "Invalid GST number"
    )
    private String gstNumber;


    @NotBlank(message = "PAN number is required")
    @Pattern(
        regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
        message = "Invalid PAN number"
    )
    private String panNumber;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SupplierRequest() {
    }


    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }


    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
}