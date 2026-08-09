package Procurement.Master.Controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.DeliveryRequest;
import Procurement.Master.Entity.Delivery;
import Procurement.Master.Service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin("*")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/supplier/{supplierId}")
    public List<Delivery> getSupplierDeliveries(
            @PathVariable Long supplierId){

        return deliveryService.getSupplierDeliveries(supplierId);

    }

    @PutMapping("/{deliveryId}/status")
    public Delivery updateDeliveryStatus(

            @PathVariable Long deliveryId,

            @RequestBody DeliveryRequest request){

        return deliveryService.updateStatus(
                deliveryId,
                request.getDeliveryStatus());

    }
    @GetMapping
    public List<Delivery> getAllDeliveryDetails(){

        return deliveryService.getAllDeliveries();

    }

}