package Procurement.Master.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Procurement.Master.Dto.InventoryRequest;
import Procurement.Master.Entity.Inventory;
import Procurement.Master.Service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
public class InventoryController {


@Autowired
private InventoryService inventoryService;



@PostMapping("/receive")
public ResponseEntity<Inventory> receiveGoods(
@RequestBody InventoryRequest request){


return ResponseEntity.ok(
inventoryService.receiveGoods(request)
);


}

}
