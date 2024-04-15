package kstu.fit.sib.batyrkhanov.payment.utilities.actions;

import java.util.Collections;
import java.util.LinkedList;
import kstu.fit.sib.batyrkhanov.payment.utilities.CardBalanceComparator;
import kstu.fit.sib.batyrkhanov.payment.utilities.CardNumberComparator;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;

public class CardSorter {
    
    public void sortByBalance(LinkedList<Card> cards) {
        Collections.sort(cards);
    }
    public void sortByType(LinkedList<Card> cards) {
        CardBalanceComparator comparator = new CardBalanceComparator();
        cards.sort(comparator);
    }
    public void sortByNumber(LinkedList<Card> cards) {
        CardNumberComparator comparator = new CardNumberComparator();
        cards.sort(comparator);
    }
}
