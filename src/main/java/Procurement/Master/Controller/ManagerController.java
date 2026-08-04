package Procurement.Master.Controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Service.ManagerService;



@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*")
public class ManagerController {



    @Autowired
    private ManagerService managerService;




    // Manager Dashboard

    @GetMapping("/requests")
    public List<PurchaseRequisition> getRequests(){


        return managerService.getManagerRequests();

    }





    // Approve

    @PutMapping("/approve/{id}")
    public String approveRequest(
            @PathVariable Long id){


        return managerService.approveRequest(id);

    }





    // Reject

    @PutMapping("/reject/{id}")
    public String rejectRequest(
            @PathVariable Long id){


        return managerService.rejectRequest(id);

    }


}