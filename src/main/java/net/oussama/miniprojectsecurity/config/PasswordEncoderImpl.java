package net.oussama.miniprojectsecurity.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return encode(rawPassword.toString());
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        System.out.println("Username: " + rawPassword + " encoded: " + encodedPassword);
        return rawPassword.equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(@Nullable String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }
}
