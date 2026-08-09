package Procurement.Master.Dto;



public class InventoryRequest {


private Long poId;

private Integer quantity;



public Long getPoId() {
    return poId;
}


public void setPoId(Long poId) {
    this.poId = poId;
}


public Integer getQuantity() {
    return quantity;
}


public void setQuantity(Integer quantity) {
    this.quantity = quantity;
}

}