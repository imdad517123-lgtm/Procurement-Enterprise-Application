package Procurement.Master.Entity;




import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="inventory")
public class Inventory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;


    @OneToOne
    @JoinColumn(name="po_id")
    private PurchaseOrder purchaseOrder;


    private String itemName;


    private Integer quantity;


    private String status;


    private LocalDateTime receivedDate;



    public Long getInventoryId() {
        return inventoryId;
    }


    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }


    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }


    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }


    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

}