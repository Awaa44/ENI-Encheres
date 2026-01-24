package fr.eni.projetencheres.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetailService implements UserDetails {

    private int noUtilisateur;
    private String pseudo;
    private String motDePasse;
    private String role;

    public CustomUserDetailService(int noUtilisateur, String pseudo, String motDePasse, String role) {
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public @Nullable String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
