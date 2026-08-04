package Procurement.Master.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Entity.Workflow;

import java.util.List;
import java.util.Optional;


public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

	Optional<Workflow> findByRequisition(PurchaseRequisition requisition);

    List<Workflow> findByWorkflowStatus(String workflowStatus);

    List<Workflow> findByCurrentApprover(Employee employee);

}