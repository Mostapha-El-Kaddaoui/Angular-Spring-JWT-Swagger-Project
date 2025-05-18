package ma.enset.tresorory.Exceptions;

public class BalanceNotSufficentException extends RuntimeException {
    public BalanceNotSufficentException(String message) {
        super(message);
    }
}
