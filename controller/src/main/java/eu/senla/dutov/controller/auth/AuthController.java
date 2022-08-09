package eu.senla.dutov.controller.auth;

import eu.senla.dutov.dto.RegistrationDto;
import eu.senla.dutov.dto.ResponseUserDto;
import eu.senla.dutov.model.jwt.JwtRequest;
import eu.senla.dutov.model.jwt.JwtResponse;
import eu.senla.dutov.service.user.AuthenticationService;
import eu.senla.dutov.service.user.RegistrationService;
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

import javax.validation.Valid;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final String URI_REGISTER = "/register";
    private static final String URI_AUTH = "/auth";
    private static final String AUTHENTICATION_USER = "Authentication user";
    private static final String STATUS_200 = "200";
    private static final String REGISTRATION_USER = "Registration user";
    private static final String REGISTRATION_IS_SUCCESSFUL = "Registration is successful";
    private static final String AUTHENTICATION_IS_SUCCESSFUL = "Authentication is successful";

    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    @PostMapping(URI_AUTH)
    @Operation(summary = AUTHENTICATION_USER)
    @ApiResponse(responseCode = STATUS_200, description = AUTHENTICATION_IS_SUCCESSFUL)
    public ResponseEntity<JwtResponse> authentication(@RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(authenticationService.authentication(jwtRequest));
    }

    @PostMapping(URI_REGISTER)
    @Operation(summary = REGISTRATION_USER)
    @ApiResponse(responseCode = STATUS_200, description = REGISTRATION_IS_SUCCESSFUL)
    public ResponseEntity<ResponseUserDto> registerUser(@RequestBody @Valid RegistrationDto registrationDto) {
        return ResponseEntity.ok(registrationService.registration(registrationDto));
    }
}