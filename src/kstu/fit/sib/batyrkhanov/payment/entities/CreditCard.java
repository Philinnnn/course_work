package kstu.fit.sib.batyrkhanov.payment.entities;
import kstu.fit.sib.batyrkhanov.payment.enums.CardTypes;
import kstu.fit.sib.batyrkhanov.payment.utilities.Administrator;
import kstu.fit.sib.batyrkhanov.payment.utilities.Generator;

import java.io.Serial;

@SuppressWarnings("unused")
public class CreditCard extends Card {

    @Serial
    private static final long serialVersionUID = 976321903L;

    public CreditCard(long cardNumber, Account account, short PIN, CardTypes cardType) {
        super(cardNumber, account, PIN, cardType);
    }

    private double getInterestRate() {
        return switch (getCardType()) {
            case DEFAULT -> 0.15; // 15%
            case GOLD -> 0.125; // 12.5%
            case PLATINUM -> 0.10; // 10%
            case DIAMOND -> 0.075; // 7.5%
        };
    }
    private double getMaxCreditLimit() {
        return switch (getCardType()) {
            case DEFAULT -> 75000;
            case GOLD -> 125000;
            case PLATINUM -> 175000;
            case DIAMOND -> 250000;
        };
    }

    @Override
    public boolean withdraw(double amount) {
        double interestRate = getInterestRate();
        double maxCreditLimit = getMaxCreditLimit();

        if (getAccount().getBalance() >= amount) {
            getAccount().setBalance(getAccount().getBalance() - amount);
            return true;
        } else if (getAccount().getBalance() - amount < maxCreditLimit) {
            double amountWithInterest = amount + (amount * interestRate);
            if (amountWithInterest <= maxCreditLimit) {
                getAccount().setBalance(getAccount().getBalance() - amountWithInterest);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void payTo(Card cardTo, double amount) {
        double maxCreditLimit = getMaxCreditLimit();
        if (getAccount().getBalance() >= amount) {
            this.getAccount().setBalance(getAccount().getBalance() - amount);
            cardTo.getAccount().setBalance(cardTo.getAccount().getBalance() + amount);
            System.out.println("Карта (" + getCardType() + "): Платеж выполнен успешно.");
        } else if ((getAccount().getBalance() - amount) < maxCreditLimit) {
            double interestRate = getInterestRate();
            double amountWithInterest = amount + (amount * interestRate);
            if (amountWithInterest <= maxCreditLimit) {
                this.getAccount().setBalance(-(amountWithInterest));
                cardTo.getAccount().setBalance(cardTo.getAccount().getBalance() + amount);
                System.out.println("Карта (" + getCardType() + "): Платеж выполнен успешно.");
            } else {
                System.out.println("Карта (" + getCardType() + "): Недостаточно средств на счете.");
            }
        } else {
            System.out.println("Карта (" + getCardType() + "): Недостаточно средств на счете.");
        }
    }
    @Override
    public Object clone() {
        Client newClient = new Client(this.getAccount().getClient().getName(), Administrator.nextCID++);
        Account newAccount = new Account(newClient, Administrator.nextUID++, this.getAccount().getBalance());
        
        CreditCard cloned = new CreditCard(Generator.generateCardNumber(), newAccount, this.getPin(), this.getCardType());

        for (Card card : this.getAccount().getCards())
            newAccount.getCards().add(new CreditCard(Generator.generateCardNumber(), newAccount, card.getPin(), card.getCardType()));

        for (Account account : this.getAccount().getClient().getAccounts())
            newClient.getAccounts().add(new Account(newClient, Administrator.nextUID++, account.getBalance()));
        return cloned;
    }
    @Override
    public String toString() {
        return super.toString() + "\t\t[Кредитная]}";
    }
}
