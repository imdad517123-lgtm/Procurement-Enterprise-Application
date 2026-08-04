package Procurement.Master.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "approval_hierarchy")
public class ApprovalHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hierarchyId;

    private int level;

    private String role;

    private int nextLevel;

    public ApprovalHierarchy() {
    }

    public Long getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(Long hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }
}