package b4u.pocketpartners.backend.users.interfaces.rest.resources;

public record UserInformationResource(Long id,
                                      String fullName,
                                      String phoneNumber,
                                      String photo,
                                      String email,
                                      Long userId) {
}
