package Procurement.Master.Dto;





import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApprovalRequest {

    @NotNull(message = "Approver Id is required")
    private Long approverId;

    @NotBlank(message = "Remarks are required")
    private String remarks;

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}