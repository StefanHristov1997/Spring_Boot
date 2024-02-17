package entities;

import javax.persistence.*;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class BillingDetail {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String owner;

    public BillingDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long number) {
        this.id = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

