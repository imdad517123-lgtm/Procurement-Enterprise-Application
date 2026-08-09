package Procurement.Master.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.InventoryRequest;
import Procurement.Master.Entity.Inventory;
import Procurement.Master.Entity.PurchaseOrder;
import Procurement.Master.Repository.InventoryRepository;
import Procurement.Master.Repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class InventoryService {


@Autowired
private InventoryRepository inventoryRepository;


@Autowired
private PurchaseOrderRepository purchaseOrderRepository;



public Inventory receiveGoods(
        InventoryRequest request){



PurchaseOrder po =
purchaseOrderRepository.findById(request.getPoId())

.orElseThrow(() ->
new RuntimeException("PO Not Found"));



Inventory inventory = new Inventory();


inventory.setPurchaseOrder(po);


inventory.setItemName(
po.getPurchaseRequisition()
.getItemName()
);


inventory.setQuantity(
request.getQuantity()
);


inventory.setStatus(
"GOODS_RECEIVED"
);



po.setStatus("GOODS_RECEIVED");


purchaseOrderRepository.save(po);



return inventoryRepository.save(inventory);


}


}	