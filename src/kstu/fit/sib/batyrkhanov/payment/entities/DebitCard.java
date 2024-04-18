package kstu.fit.sib.batyrkhanov.payment.entities;

import kstu.fit.sib.batyrkhanov.payment.enums.CardTypes;
import kstu.fit.sib.batyrkhanov.payment.utilities.Administrator;
import kstu.fit.sib.batyrkhanov.payment.utilities.Generator;

import java.io.Serial;

public class DebitCard extends Card{

    @Serial
    private static final long serialVersionUID = 44142132178L;

    public DebitCard(long cardNumber, Account account, short PIN, CardTypes cardType) {
        super(cardNumber, account, PIN, cardType);
    }

    private double getMaxTransferAmount() { // Метод для возвращения максимальной суммы перевода
        return switch (getCardType()) {
            case DEFAULT -> 30000;
            case GOLD -> 75000;
            case PLATINUM -> 150000;
            case DIAMOND -> 500000;
        };
    }

    @Override
    public boolean withdraw(double amount) {
        if (getAccount().getBalance() >= amount) {
            getAccount().setBalance(getAccount().getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void payTo(Card cardTo, double amount) {
        double maxTransferAmount = getMaxTransferAmount();
        if (this.getAccount().getBalance() >= amount && amount <= maxTransferAmount) {
            this.getAccount().setBalance(this.getAccount().getBalance() - amount);
            cardTo.getAccount().setBalance(cardTo.getAccount().getBalance() + amount);
            System.out.println("Карта (" + this.getCardType() + "): Платеж выполнен успешно.");
        } else {
            System.out.println("Карта (" + this.getCardType() + "): Недостаточно средств на счете или превышен лимит перевода.");
        }
    }
    @Override
    public Object clone() {
        Client newClient = new Client(this.getAccount().getClient().getName(), Administrator.nextCID++);
        Account newAccount = new Account(newClient, Administrator.nextUID++, this.getAccount().getBalance());
        
        DebitCard cloned = new DebitCard(Generator.generateCardNumber(), newAccount, this.getPin(), this.getCardType());
        
        for (Card card : this.getAccount().getCards())
            newAccount.getCards().add(new DebitCard(Generator.generateCardNumber(), newAccount, card.getPin(), card.getCardType()));

        for (Account account : this.getAccount().getClient().getAccounts())
            newClient.getAccounts().add(new Account(newClient, Administrator.nextUID++, account.getBalance()));
        return cloned;
    }
    @Override
    public String toString() {
        return super.toString() + "\t\t[Дебетовая]}";
    }
}