package org.authservice.user.web;

import lombok.AllArgsConstructor;
import org.authservice.system.jwt.JWTTokenProvider;
import org.authservice.user.dto.UserDTO;
import org.authservice.user.dto.login.LoginResponse;
import org.authservice.user.dto.login.RegisterResponse;
import org.authservice.user.models.User;
import org.authservice.user.service.UserCommandService;
import org.authservice.user.service.UserQuerryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

import static org.springframework.http.HttpStatus.OK;


import static org.authservice.utils.Utils.JWT_TOKEN_HEADER;
@RestController
@CrossOrigin
@RequestMapping("api/v1/")
@AllArgsConstructor
public class UserRest {


    private UserCommandService userCommandService;
    private UserQuerryService userQuerryService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO user) {
        authenticate(user.email(),user.password());
        User loginUser = userQuerryService.findByEmail(user.email()).get();
        User userPrincipal = new User();
        userPrincipal.setEmail(loginUser.getEmail());
        userPrincipal.setPassword(loginUser.getPassword());
        userPrincipal.setUserRole(loginUser.getUserRole());
        userPrincipal.setActive(loginUser.isActive());
        userPrincipal.setFirstName(loginUser.getFirstName());
        userPrincipal.setLastName(loginUser.getLastName());
        userPrincipal.setPhoneNumber(loginUser.getPhoneNumber());
        userPrincipal.setRegisteredAt(loginUser.getRegisteredAt());
        userPrincipal.setCreatedAt(loginUser.getCreatedAt());

        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        LoginResponse loginResponse= new LoginResponse(jwtHeader.getFirst(JWT_TOKEN_HEADER), userPrincipal.getFirstName(), userPrincipal.getLastName(), userPrincipal.getPhoneNumber(), userPrincipal.getEmail(), userPrincipal.isActive());
        return new ResponseEntity<>(loginResponse,jwtHeader,OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> addStudent(@RequestBody UserDTO user){
        this.userCommandService.addUser(user);
        User userPrincipal = userQuerryService.findByEmail(user.email()).get();
        HttpHeaders jwtHeader=getJwtHeader(userPrincipal);
        RegisterResponse registerResponse= new RegisterResponse(jwtHeader.getFirst(JWT_TOKEN_HEADER), userPrincipal.getFirstName(), userPrincipal.getLastName(), userPrincipal.getPhoneNumber(), userPrincipal.getEmail(), userPrincipal.isActive());
        authenticate(user.email(), user.password());
        return new ResponseEntity<>(registerResponse,jwtHeader,OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }

}
