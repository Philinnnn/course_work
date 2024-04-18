package kstu.fit.sib.batyrkhanov.payment.entities;

import kstu.fit.sib.batyrkhanov.payment.exceptions.BlockAccountException;
import kstu.fit.sib.batyrkhanov.payment.interfaces.AccountLock;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Client implements Serializable, AccountLock {

    @Serial
    private static final long serialVersionUID = 793217832L;

    private String name;
    private int clientID; // Уникальный номер клиента Client ID
    private LinkedList <Account> accounts = new LinkedList<>();

    public Client(){
    }
    public Client(String name, int clientID) {
        this.name = name;
        this.clientID = clientID;
    }

////////////////////////////////
    // GETTERS
////////////////////////////////

    public String getName(){
        return name;
    }
    public int getClientID() {
        return clientID;
    }
    public LinkedList <Account> getAccounts() {
        return this.accounts;
    }
    public static Client getClientByID(int ID, LinkedList <Client> clients) {
        for (Client client : clients) {
            if (client.getClientID() == ID) {
                return client;
            }
        }
        return null;
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
        Client client = (Client) obj;
        return getClientID() == client.getClientID() &&
            Objects.equals(getName(), client.getName()) &&
            Objects.equals(getAccounts(), client.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, clientID, accounts);
    }

    @Override
    public void blockAccount(Account acc) {
        if (this.accounts.contains(acc)) {
            try {
                acc.block();
            } catch (BlockAccountException e) {
                System.err.println("Предупреждение: " + e.getMessage());
            }
        }
        else System.out.println("Данный счет принадлежит другому клиенту");
    }

    @Override
    public void unblockAccount(Account acc) {
        if (this.accounts.contains(acc)) {
            try {
                acc.unblock();
            } catch (BlockAccountException e) {
                System.err.println("Предупреждение: " + e.getMessage());
            }
        }
        else System.out.println("Данный счет принадлежит другому клиенту");
    }

    @Override
    public String toString() {
        String str = "Клиент {Имя='" + name + "', ID=" + clientID + "}\n";
        for (Account acc : accounts)
            str += "\t" + acc.toString();
        return str;
    }
}