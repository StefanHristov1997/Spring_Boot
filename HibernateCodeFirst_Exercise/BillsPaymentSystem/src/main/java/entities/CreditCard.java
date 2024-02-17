package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class CreditCard extends BillingDetail {
    @Column (name = "card_type", nullable = false)
    private String cardType;

    @Column(name = "expiration_month", nullable = false)
    private LocalDate expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private LocalDate expirationYear;

    public CreditCard() {
        super();
    }



    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public LocalDate getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(LocalDate expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public LocalDate getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(LocalDate expirationYear) {
        this.expirationYear = expirationYear;
    }
}
