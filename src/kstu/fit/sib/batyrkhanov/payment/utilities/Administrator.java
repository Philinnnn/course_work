package kstu.fit.sib.batyrkhanov.payment.utilities;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import kstu.fit.sib.batyrkhanov.payment.entities.Client;
import kstu.fit.sib.batyrkhanov.payment.entities.CreditCard;
import kstu.fit.sib.batyrkhanov.payment.entities.DebitCard;
import kstu.fit.sib.batyrkhanov.payment.exceptions.BlockAccountException;
import kstu.fit.sib.batyrkhanov.payment.interfaces.AccountLock;

import java.util.LinkedList;

public final class Administrator implements AccountLock {

    public Account account;
    public static int nextCID = 0;
    public static int nextUID = 0;

////////////////////////////////
    // Методы клиента
////////////////////////////////

    public void createClient(LinkedList <Client> clients) {
        Client client = new Client(Generator.generateClientName(),nextCID++);
        clients.add(client);
    }
    public void createClient(LinkedList <Client> clients, int amount) {
        for(int i = 0; i < amount; i++) {
            createClient(clients);
        }
    }

////////////////////////////////
    // Методы счёта
////////////////////////////////

    public void createAccount(Client client, LinkedList <Account> allAccounts) {
        try {  
            if(client == null) throw new NullPointerException();
            Account account = new Account(client,nextUID++,Generator.generateBalance());
            client.getAccounts().add(account);
            allAccounts.add(account);
        }
        catch(NullPointerException e) {
            System.err.println("Ошибка: Клиент не найден");
        }
    }
    public void createAccount(LinkedList <Client> clients, LinkedList <Account> allAccounts) {
        for(Client client : clients) {
            int amount = (int) (Math.random() * 3 + 1);
            for(int i = 0; i < amount; i++) {
                createAccount(client, allAccounts);
            }
        }
    }
    @Override
    public void blockAccount(Account acc) {
        try {
            acc.block();
        }
            catch (BlockAccountException e) {
                System.err.println("Предупреждение: " + e.getMessage());
            }
    }
    @Override
    public void unblockAccount(Account acc) {
        try {
            acc.unblock();
        }
            catch (BlockAccountException e) {
                System.err.println("Предупреждение: " + e.getMessage());
            }
    }

////////////////////////////////
    // Методы карт
////////////////////////////////

    public void createCard(Account account, LinkedList<Card> allCards, boolean isCreditCard) {
        Card card;
        try {
            if(isCreditCard) {
                card = new DebitCard(Generator.generateCardNumber(), account, Generator.generatePIN(), Generator.generateCardType());
            } else {
                card = new CreditCard(Generator.generateCardNumber(), account, Generator.generatePIN(), Generator.generateCardType());
            }
            account.getCards().add(card);
            allCards.add(card);
        }
        catch (NullPointerException e) {
            System.err.println("Ошибка: Аккаунт не найден");
        }
    }
    public void createCards(LinkedList <Account> accounts, LinkedList <Card> allCards) {
        for(Account account : accounts) {
            int amount = (int) (Math.random() * 3 + 1);
            for(int i = 0; i < amount; i++) {
                createCard(account, allCards, Generator.generateBoolean());
            }
        }
    }
}
