package Procurement.Master.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.PaymentRequest;
import Procurement.Master.Entity.Payment;
import Procurement.Master.Entity.PurchaseOrder;
import Procurement.Master.Repository.PaymentRepository;
import Procurement.Master.Repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class PaymentService {



@Autowired
private PaymentRepository paymentRepository;


@Autowired
private PurchaseOrderRepository purchaseOrderRepository;




public Payment makePayment(
        PaymentRequest request){



PurchaseOrder po =
purchaseOrderRepository.findById(request.getPoId())

.orElseThrow(() ->
new RuntimeException("PO Not Found"));



if(!po.getStatus()
.equals("GOODS_RECEIVED")){


throw new RuntimeException(
"Goods not verified");

}




Payment payment=new Payment();



payment.setPurchaseOrder(po);


payment.setSupplierName(
		po.getSupplier().getEmail()
);


payment.setAmount(
request.getAmount()
);



payment.setPaymentStatus(
"PAYMENT_COMPLETED"
);



po.setStatus(
"PAYMENT_COMPLETED"
);



purchaseOrderRepository.save(po);



return paymentRepository.save(payment);


}

}
