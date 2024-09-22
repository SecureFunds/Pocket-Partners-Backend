package b4u.pocketpartners.backend.users.interfaces.rest;

import b4u.pocketpartners.backend.users.domain.model.commands.DeleteUserInformationCommand;
import b4u.pocketpartners.backend.users.domain.model.queries.GetAllUsersInformationQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserInformationByIdQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserInformationByUserIdQuery;
import b4u.pocketpartners.backend.users.domain.services.UserInformationCommandService;
import b4u.pocketpartners.backend.users.domain.services.UserInformationQueryService;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.CreateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UpdateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.CreateUserInformationCommandFromResourceAssembler;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.UpdateUserInformationCommandFromResourceAssembler;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.UserInformationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/usersInformation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users Information", description = "User Information Management Endpoints")
public class UsersInformationController {

    private final UserInformationQueryService userInformationQueryService;
    private final UserInformationCommandService userInformationCommandService;

    public UsersInformationController(UserInformationQueryService userInformationQueryService, UserInformationCommandService userInformationCommandService) {
        this.userInformationQueryService = userInformationQueryService;
        this.userInformationCommandService = userInformationCommandService;
    }

    //CREATE
    @Operation(summary = "Create a new user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User information created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<UserInformationResource> createUser(@RequestBody CreateUserInformationResource resource) {
        var createUserCommand = CreateUserInformationCommandFromResourceAssembler.toCommandfromResource(resource);
        var user = userInformationCommandService.handle(createUserCommand);
        if (user.isEmpty()) return ResponseEntity.badRequest().build();
        var userResource = UserInformationResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    //GET BY ID
    @Operation(summary = "Get user information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @GetMapping("/{userInformationId}")
    public ResponseEntity<UserInformationResource> getProfileById(@PathVariable Long userInformationId) {
        var getUserInformationByIdQuery = new GetUserInformationByIdQuery(userInformationId);
        var userInformation = userInformationQueryService.handle(getUserInformationByIdQuery);
        if (userInformation.isEmpty()) return ResponseEntity.badRequest().build();
        var profileResource = UserInformationResourceFromEntityAssembler.toResourceFromEntity(userInformation.get());
        return ResponseEntity.ok(profileResource);
    }

    @Operation(summary = "Get user information by User ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserInformationResource> getProfileByUserId(@PathVariable Long userId) {
        var getUserInformationByUserIdQuery = new GetUserInformationByUserIdQuery(userId);
        var userInformation = userInformationQueryService.handle(getUserInformationByUserIdQuery);
        if (userInformation.isEmpty()) return ResponseEntity.badRequest().build();
        var profileResource = UserInformationResourceFromEntityAssembler.toResourceFromEntity(userInformation.get());
        return ResponseEntity.ok(profileResource);
    }

    //GET ALL
    @Operation(summary = "Get all users information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users information")
    })
    @GetMapping
    public ResponseEntity<List<UserInformationResource>> getAllUsersInformation() {
        var getAllUsersInformationQuery = new GetAllUsersInformationQuery();
        var usersInformation = userInformationQueryService.handle(getAllUsersInformationQuery);
        var userResources = usersInformation.stream().map(UserInformationResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(userResources);
    }


    //UPDATE
    @Operation(summary = "Update user information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @PutMapping("/{userInformationId}")
    public ResponseEntity<UserInformationResource> updateUserInformationById(@PathVariable Long userInformationId, @RequestBody UpdateUserInformationResource resource) {
        var updateUserCommand = UpdateUserInformationCommandFromResourceAssembler.toCommandfromResource(userInformationId, resource);
        var updatedUserInformation = userInformationCommandService.handle(updateUserCommand);
        if (updatedUserInformation.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        var userInformationResource = UserInformationResourceFromEntityAssembler.toResourceFromEntity(updatedUserInformation.get());
        return ResponseEntity.ok(userInformationResource);
    }


    //DELETE
    @Operation(summary = "Delete user information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User information deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @DeleteMapping("/{userInformationId}")
    public ResponseEntity<Void> deleteUserInformationById(@PathVariable Long userInformationId) {
        var deleteUserInformationCommand = new DeleteUserInformationCommand(userInformationId);
        var userInformationDeleted = userInformationCommandService.handle(deleteUserInformationCommand);
        if (userInformationDeleted.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }

}
