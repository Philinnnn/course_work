package kstu.fit.sib.batyrkhanov.payment.interfaces;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;

public interface AccountLock {
    void blockAccount(Account account);
    void unblockAccount(Account account);
}