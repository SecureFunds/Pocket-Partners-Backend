package b4u.pocketpartners.backend.operations.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long aLong) {
        super("User with id " + aLong + " not found");
    }
}
