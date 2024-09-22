package b4u.pocketpartners.backend.groups.domain.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long aLong) {
        super("Payment with id " + aLong + " not found");
    }
}
