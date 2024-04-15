package kstu.fit.sib.batyrkhanov.payment.exceptions;

public class BlockAccountException extends Exception{
    public BlockAccountException() {
        super();
    }
    public BlockAccountException(String message) {
        super(message);
    }
    public BlockAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
