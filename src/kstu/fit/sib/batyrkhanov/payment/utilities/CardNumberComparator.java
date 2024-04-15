package kstu.fit.sib.batyrkhanov.payment.utilities;

import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import java.util.Comparator;

public class CardNumberComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        Long cardNumber1 = card1.getCardNumber();
        Long cardNumber2 = card2.getCardNumber();
        return cardNumber1.compareTo(cardNumber2);
    }
}
