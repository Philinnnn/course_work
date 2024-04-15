package kstu.fit.sib.batyrkhanov.payment.entities;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import kstu.fit.sib.batyrkhanov.payment.enums.CardTypes;

public abstract class Card implements Comparable <Card>, Serializable, Cloneable {

    private long cardNumber;
    private transient short pin; // pin-код
    private Account account; // Счет, к которому привязана карта
    private CardTypes cardType;

    public Card() {
    }
    public Card(long cardNumber, Account account, short pin, CardTypes cardType) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.account = account;
        this.cardType = cardType;
    }

////////////////////////////////
    // SETTERS
////////////////////////////////

    public void setAccount(Account account) {
        this.account = account;
    }

////////////////////////////////
    // GETTERS
////////////////////////////////

    public long getCardNumber() {
        return cardNumber;
    }
    public short getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
    public CardTypes getCardType() {
        return cardType;
    }
    public static Card findCardByNumber(long cardNumber, LinkedList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardNumber() == cardNumber) {
                return card;
            }
        }
        return null;
    }
    
////////////////////////////////
    // Abstract
////////////////////////////////

    public abstract void payTo(Card cardTo, double amount);
    public abstract boolean withdraw(double amount);
    public abstract Object clone() throws CloneNotSupportedException;

////////////////////////////////
    // Overrides
////////////////////////////////

    @Override
    public int compareTo(Card otherCard) {
        return Double.compare(otherCard.getAccount().getBalance(), this.getAccount().getBalance());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return Objects.equals(getCardNumber(), card.getCardNumber()) &&
               Objects.equals(getPin(), card.getPin()) &&
               Objects.equals(getCardType(), card.getCardType()) &&
               (getAccount() == null ? card.getAccount() == null : getAccount().equals(card.getAccount()));
    }    
    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getPin(), getCardType());
    }
    @Override
    public String toString() {
        return String.format("""
                Карта {Баланс=%.2f\tНомер=%s\tТип=%s""", account.getBalance(), cardNumber, cardType);
    }
}