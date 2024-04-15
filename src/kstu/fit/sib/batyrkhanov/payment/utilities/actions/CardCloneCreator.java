package kstu.fit.sib.batyrkhanov.payment.utilities.actions;

import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import kstu.fit.sib.batyrkhanov.payment.utilities.Reporter;

public class CardCloneCreator {

    public CardCloneCreator() {
    }

    public Card createClone(Card card) throws CloneNotSupportedException {
        Card clone = (Card) card.clone();
        return clone;
    }

    public boolean isDeepCopy(Card card, Card clone) {
        if (card.equals(clone)) {
            return false;
        }
        if (card.getAccount() != clone.getAccount()) {
            Reporter.print(card.getAccount());
            Reporter.print(clone.getAccount());
            return true;
        }
        return false;
    }
}
