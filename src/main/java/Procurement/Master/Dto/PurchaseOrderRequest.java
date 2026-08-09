package Procurement.Master.Dto;



public class PurchaseOrderRequest {


    private Long requestId;

    private Long supplierId;



    public Long getRequestId() {
        return requestId;
    }


    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }


    public Long getSupplierId() {
        return supplierId;
    }


    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

}