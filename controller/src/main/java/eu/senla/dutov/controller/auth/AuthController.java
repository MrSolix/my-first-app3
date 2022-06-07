package eu.senla.dutov.controller.auth;

import eu.senla.dutov.dto.RegistrationDto;
import eu.senla.dutov.dto.ResponseUserDto;
import eu.senla.dutov.model.jwt.JwtRequest;
import eu.senla.dutov.model.jwt.JwtResponse;
import eu.senla.dutov.service.user.AuthenticationService;
import eu.senla.dutov.service.user.RegistrationService;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final String URI_REGISTER = "/register";
    private static final String URI_AUTH = "/auth";
    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    @PostMapping(URI_AUTH)
    @Operation(summary = "Authentication user")
    @ApiResponse(responseCode = "200", description = "Authentication is successful")
    public ResponseEntity<JwtResponse> authentication(@RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(authenticationService.authentication(jwtRequest));
    }

    @PostMapping(URI_REGISTER)
    @Operation(summary = "Registration user")
    @ApiResponse(responseCode = "200", description = "Registration is successful")
    public ResponseEntity<ResponseUserDto> registerUser(@RequestBody @Valid RegistrationDto registrationDto) {
        return ResponseEntity.ok(registrationService.registration(registrationDto));
    }
}
