package be.pxl.student.entity.DomainClass;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class Payment {
    @Id
    private int id; //PK

    @OneToOne
    private int accountID; //FK

    @OneToOne
    private int counterAccountID; //FK

    private String IBAN;
    private Date date;
    private float amount;
    private String currency;
    private String detail;

    public Payment(int id, int accountID, int counterAccountID, String IBAN, Date date, float amount, String currency, String detail) {
        this.id = id;
        this.accountID = accountID;
        this.counterAccountID = counterAccountID;
        this.IBAN = IBAN;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    public Payment(String IBAN, Date date, float amount, String currency, String detail) {
        this.IBAN = IBAN;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    // GETTER & SETTER VOOR PK MOET NIET


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getCounterAccountID() {
        return counterAccountID;
    }

    public void setCounterAccountID(int counterAccountID) {
        this.counterAccountID = counterAccountID;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "IBAN='" + IBAN + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Float.compare( payment.amount, amount ) == 0 &&
                Objects.equals( IBAN, payment.IBAN ) &&
                Objects.equals( date, payment.date ) &&
                Objects.equals( currency, payment.currency ) &&
                Objects.equals( detail, payment.detail );
    }

    @Override
    public int hashCode() {
        return Objects.hash( IBAN, date, amount, currency, detail );
    }
}
