package b4u.pocketpartners.backend.users.application.internal.outboundservices.tokens;

public interface TokenService {

    String generateToken(String username);
    String getUsernameFromToken(String token);
    boolean validateToken(String token);
}