package kstu.fit.sib.batyrkhanov.payment.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 793217832L;

    private String name;
    private int CID; // Уникальный номер клиента Client ID
    private LinkedList <Account> accounts = new LinkedList<>();

    public Client(){
    }
    public Client(String name, int CID) {
        this.name = name;
        this.CID = CID;
    }

////////////////////////////////
    // GETTERS
////////////////////////////////

    public String getName(){
        return name;
    }
    public int getCID() {
        return CID;
    }
    public LinkedList <Account> getAccounts() {
        return this.accounts;
    }
    public static Client getClientByCID(int CID, LinkedList <Client> clients) {
        for (Client client : clients) {
            if (client.getCID() == CID) {
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
        return getCID() == client.getCID() &&
            Objects.equals(getName(), client.getName()) &&
            Objects.equals(getAccounts(), client.getAccounts());
    }
    @Override
    public String toString() {
        String str = "Клиент {Имя='" + name + "', CID=" + CID + "}\n";
        for (Account acc : accounts)
            str += "\t" + acc.toString();
        return str;
    }      
}