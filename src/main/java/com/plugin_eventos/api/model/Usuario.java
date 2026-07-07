package com.plugin_eventos.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // classe pertencente ao banco de dados
@Table(name = "usuarios") // representa qual tabela do banco de dados usado
@Data // lombok (tira os get setters etc)
@NoArgsConstructor // cria construtor vazio
@AllArgsConstructor // cria construtor com todos os valores


public class Usuario implements UserDetails {

    @Id // chave primaria da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera o id int por ordem crescente
    private Long id;

    @Column(nullable = false)  // nullable: configura que variavel nao pode ser null
    private String nome;

    @Column(nullable = false, unique = true) // unique: variavel nao pode ser repetida, unica
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "tipo_acesso", nullable = false) // name: configura para que column mandar a variavel se variavel
    private String tipoAcesso;                      //       tiver nome diferente


@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + tipoAcesso)); // define o tipo de poder q o cara vai ter
}

@Override
public String getPassword() {
    return senha;
} // getters necessario do Spring Security

@Override
public String getUsername() { // username vai ser o email
    return email;
}

@Override
public boolean isAccountNonExpired() { return true; } // conta nao ta expirada

@Override
public boolean isAccountNonLocked() { return true; } // conta nao ta bloqueada

@Override
public boolean isCredentialsNonExpired() { return true; } // senha nao expirou

@Override
public boolean isEnabled() { return true; } // conta ta ativa
}