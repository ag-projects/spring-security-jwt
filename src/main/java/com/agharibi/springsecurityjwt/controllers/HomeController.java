package com.agharibi.springsecurityjwt.controllers;

import com.agharibi.springsecurityjwt.models.AuthenticationRequest;
import com.agharibi.springsecurityjwt.models.AuthenticationResponse;
import com.agharibi.springsecurityjwt.service.AppUserDetailsService;
import com.agharibi.springsecurityjwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping({"/index"})
    public String index() {
        return "Welcome to Spring Security JWT";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect userName or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        final String jwtToken = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
