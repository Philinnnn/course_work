package kstu.fit.sib.batyrkhanov.payment.utilities;

import java.util.Comparator;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;

public class CardTypeComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        return card2.getCardType().ordinal() - card1.getCardType().ordinal();
    }
}