package ma.enset.tresorory.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        // 1. Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Generate JWT token
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority()) // Should return "ROLE_USER" (not "USER")
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:8085")
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.HOURS))
                .subject(username)
                .claim("scope", scope)
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                claims
        );

        Jwt jwt = jwtEncoder.encode(parameters);

        // 3. Return token
        return Map.of("access_token", jwt.getTokenValue());
    }
}