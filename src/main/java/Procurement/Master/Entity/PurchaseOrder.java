package Procurement.Master.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;


    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private PurchaseRequisition purchaseRequisition;


    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;


    @Column(nullable = false)
    private String status;
    

    private LocalDate poDate;


	public Long getPoId() {
		return poId;
	}


	public void setPoId(Long poId) {
		this.poId = poId;
	}


	public PurchaseRequisition getPurchaseRequisition() {
		return purchaseRequisition;
	}


	public void setPurchaseRequisition(PurchaseRequisition purchaseRequisition) {
		this.purchaseRequisition = purchaseRequisition;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public LocalDate getPoDate() {
		return poDate;
	}


	public void setPoDate(LocalDate poDate) {
		this.poDate = poDate;
	}



  
    // Getters and Setters
}