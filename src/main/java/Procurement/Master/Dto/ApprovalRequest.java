package Procurement.Master.Dto;





import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApprovalRequest {

 

    @NotBlank(message = "Remarks are required")
    private String remarks;




    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}