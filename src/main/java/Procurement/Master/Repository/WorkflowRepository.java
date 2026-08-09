package Procurement.Master.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Entity.Workflow;


@Repository
public interface WorkflowRepository 
        extends JpaRepository<Workflow, Long>{


    Optional<Workflow> findByRequisition(
            PurchaseRequisition requisition);



    List<Workflow> findByWorkflowStatus(
            String workflowStatus);



    List<Workflow> findByCurrentApprover(
            Employee currentApprover);

    Workflow findByRequisition_RequestId(Long requestId);



    List<Workflow> findByCurrentApproverAndWorkflowStatus(
            Employee employee,
            String workflowStatus
    );



}