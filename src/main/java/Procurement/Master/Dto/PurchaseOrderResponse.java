package Procurement.Master.Dto;


import java.math.BigDecimal;
import java.time.LocalDate;


public class PurchaseOrderResponse {
	private Long requestId;


	
    private Long poId;

    private String supplierName;

    private String supplierEmail;

    private String itemName;

    private Integer quantity;

    private BigDecimal estimatedCost;

    private LocalDate poDate;

    private String status;



    public Long getPoId() {
        return poId;
    }


    public void setPoId(Long poId) {
        this.poId = poId;
    }


    public String getSupplierName() {
        return supplierName;
    }


    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getSupplierEmail() {
        return supplierEmail;
    }


    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }


    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


 

    public LocalDate getPoDate() {
        return poDate;
    }


    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


	public Long getRequestId() {
		return requestId;
	}


	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}


	public BigDecimal getEstimatedCost() {
		return estimatedCost;
	}


	public void setEstimatedCost(BigDecimal estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

}