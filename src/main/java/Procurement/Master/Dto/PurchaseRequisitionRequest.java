package Procurement.Master.Dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PurchaseRequisitionRequest {

    @NotNull(message = "Employee Id is required")
    private Long employeeId;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    @NotNull(message = "Estimated cost is required")
    private BigDecimal estimatedCost;

    @NotBlank(message = "Justification is required")
    private String justification;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}