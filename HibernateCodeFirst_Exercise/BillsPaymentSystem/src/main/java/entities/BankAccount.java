package entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends BillingDetail {
    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false, unique = true)
    private String swiftCode;

    public BankAccount() {
        super();
    }

    public BankAccount(String bankName, String swiftCode) {
        this();
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
