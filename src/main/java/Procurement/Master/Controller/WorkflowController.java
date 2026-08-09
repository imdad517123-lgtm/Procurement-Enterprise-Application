package Procurement.Master.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.ApprovalRequest;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.Workflow;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Service.WorkflowService;



@RestController
@RequestMapping("/api/workflow")
@CrossOrigin("*")
public class WorkflowController {



@Autowired
private WorkflowService workflowService;


@Autowired
private EmployeeRepository employeeRepository;





// ================================
// PENDING REQUESTS
// ================================


@GetMapping("/pending")
public List<Workflow> pending(
Authentication authentication){



String email =
authentication.getName();



Employee employee =
employeeRepository
.findByEmail(email)
.orElseThrow();



return workflowService.getPendingRequests(employee);


}







// ================================
// APPROVE
// ================================

@PutMapping("/approve/{requestId}")
public String approve(

        @PathVariable Long requestId,

        @RequestBody ApprovalRequest request,

        Authentication authentication){


    String email =
            authentication.getName();



    Employee employee =
            employeeRepository
            .findByEmail(email)
            .orElseThrow();



    return workflowService
            .approveRequest(
                    requestId,
                    employee,
                    request.getRemarks()
            );

}








// ================================
// REJECT
// ================================

@PutMapping("/reject/{requestId}")
public String reject(

        @PathVariable Long requestId,

        @RequestBody ApprovalRequest request,

        Authentication authentication){


    String email =
            authentication.getName();



    Employee employee =
            employeeRepository
            .findByEmail(email)
            .orElseThrow();



    return workflowService
            .rejectRequest(
                    requestId,
                    employee,
                    request.getRemarks()
            );

}


// ================================
// GET BY REQUEST ID
// ================================


@GetMapping("/{requestId}")
public Workflow getWorkflow(

@PathVariable Long requestId){



return workflowService.getWorkflow(requestId);


}








// ================================
// ALL WORKFLOW
// ================================


@GetMapping
public List<Workflow> all(){


return workflowService
.getAllWorkflows();


}







// ================================
// APPROVED
// ================================


@GetMapping("/approved")
public List<Workflow> approved(){


return workflowService
.getApprovedRequests();


}







// ================================
// REJECTED
// ================================


@GetMapping("/rejected")
public List<Workflow> rejected(){


return workflowService
.getRejectedRequests();


}


}