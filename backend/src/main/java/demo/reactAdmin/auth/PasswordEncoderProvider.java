package demo.reactAdmin.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderProvider implements IPasswordEncoderProvider {

    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
