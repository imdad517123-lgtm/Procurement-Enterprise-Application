package Procurement.Master.Entity;




import jakarta.persistence.*;

@Entity
@Table(name = "workflow")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workflowId;
    private int currentLevel;
    @OneToOne
    @JoinColumn(name = "request_id")
    private PurchaseRequisition requisition;

    @ManyToOne
    @JoinColumn(name = "current_approver")
    private Employee currentApprover;

    @ManyToOne
    @JoinColumn(name = "hierarchy_id")
    private ApprovalHierarchy approvalHierarchy;

    private String workflowStatus;

    public Workflow() {
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public PurchaseRequisition getRequisition() {
        return requisition;
    }

    public void setRequisition(PurchaseRequisition requisition) {
        this.requisition = requisition;
    }

    public Employee getCurrentApprover() {
        return currentApprover;
    }

    public void setCurrentApprover(Employee currentApprover) {
        this.currentApprover = currentApprover;
    }

    public ApprovalHierarchy getApprovalHierarchy() {
        return approvalHierarchy;
    }

    public void setApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
        this.approvalHierarchy = approvalHierarchy;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}