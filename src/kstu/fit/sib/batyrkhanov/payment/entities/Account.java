package kstu.fit.sib.batyrkhanov.payment.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

import kstu.fit.sib.batyrkhanov.payment.exceptions.BlockAccountException;
import kstu.fit.sib.batyrkhanov.payment.utilities.Reporter;

public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 974216421L;

    private double balance;
    private boolean isBlocked;
    private int accountId;
    private LinkedList <Card> cards = new LinkedList<>();
    private Client client;

    public Account() {
    }
    public Account(Client client, int accountId, double balance) {
        this.client = client;
        this.balance = balance;
        this.isBlocked = false;
        this.accountId = accountId;
    }

////////////////////////////////
    // GETTERS
////////////////////////////////

    public Client getClient() {
        return client;
    }
    public LinkedList<Card> getCards(){
        return cards;
    }
    public double getBalance() {
        return balance;
    }
    public int getAccountId() {
        return accountId;
    }
    public boolean isBlocked() {
        return isBlocked;
    }
    public static Account getAccountByID(int ID, LinkedList <Account> allAccounts) {
        for (Account account : allAccounts) {
            if (account.getAccountId() == ID) {
                return account;
            }
        }
        return null;
    }

////////////////////////////////
    // SETTERS
////////////////////////////////

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void block() throws BlockAccountException {
        if(isBlocked())
            throw new BlockAccountException("Аккаунт уже заблокирован");
        isBlocked = true;
    }
    public void unblock() throws BlockAccountException {
        if(!isBlocked())
            throw new BlockAccountException("Аккаунт не был заблокирован");
        isBlocked = false;
    }

////////////////////////////////
    // Overrides
////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Account account = (Account) obj;
        return Double.compare(account.getBalance(), getBalance()) == 0 &&
            isBlocked() == account.isBlocked() &&
            getAccountId() == account.getAccountId() &&
            Objects.equals(getCards(), account.getCards()) &&
            Objects.equals(getClient(), account.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, isBlocked, accountId, cards, client);
    }

    @Override
    public String toString() {
        String str = String.format("""
            Счет {баланс=%.2f, заблокирована=%s, UID=%d}%n""", balance, Reporter.boolToString(isBlocked), accountId);
        for (Card card : cards)
            str += "\t\t" + card.toString() + "\n";
        return str;
    }
}
