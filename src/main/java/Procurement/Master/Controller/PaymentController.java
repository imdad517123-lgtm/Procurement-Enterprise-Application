package Procurement.Master.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Procurement.Master.Dto.PaymentRequest;
import Procurement.Master.Entity.Payment;
import Procurement.Master.Service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {


@Autowired
private PaymentService paymentService;



@PostMapping("/process")
public ResponseEntity<Payment> processPayment(
@RequestBody PaymentRequest request){


return ResponseEntity.ok(
paymentService.makePayment(request)
);


}


}