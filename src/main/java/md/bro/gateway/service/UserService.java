package md.bro.gateway.service;

import lombok.RequiredArgsConstructor;
import md.bro.gateway.exception.CustomException;
import md.bro.gateway.repository.UserRepository;
import md.bro.gateway.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            return jwtTokenProvider.createToken(username, repository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, repository.findByUsername(username).getRoles());
    }
}
