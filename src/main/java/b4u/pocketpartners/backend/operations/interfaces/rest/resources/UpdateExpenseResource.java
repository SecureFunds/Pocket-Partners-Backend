package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import java.math.BigDecimal;

public record UpdateExpenseResource(String name, BigDecimal amount) {
}
