package com.plugin_eventos.api.security;

//Esse pacote será utilizado para guardar classes relacionadas à segurança do sistema

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtService {

    private final SecretKey chave = Keys.hmacShaKeyFor(
            "sua-chave-secreta-com-pelo-menos-32-caracteres!".getBytes()
    );

    //metodo responsável por gerar um token JWT ao usuário
    //recebe o UserDetails, que mostra se o usuário é autenticado ou não
    public String gerarToken(UserDetails usuarios){
        //cria o token JWT
        return Jwts.builder()

                //guarda o identificado do usuario, que é o username, neste caso o username é o email
                .subject(usuarios.getUsername())
                //define a data que o token foi criado
                .issuedAt(new Date())
                //define a data de expiração do token
                //System.currentMillis pega o horário atual e sera valido por 10 horas
                .expiration(new Date (System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                //assina o token utilizando a chave secreta, impedindo que alguém altere
                .signWith(chave)
                //finaliza e transforma o token em uma string
                .compact();
    }

    //metodo responsável por extrair o email/username de dentro do token
    public String extrairEmail(String token){

        //le a valida o token utilizando a mesma chave secreta
        return Jwts.parser()

                //define a chave usada para verificar a assinatura do token
                .verifyWith(chave)
                //constoi o parser
                .build()
                //faz a leitura do token assinado
                .parseSignedClaims(token)
                //pega os dados internos do token
                .getPayload()
                //retorna o email/username
                .getSubject();
    }

    //metodo responsavel por verificar se o token pertence ao usuário informado
    public boolean tokenValido(String token, UserDetails usuario){
        //extrai o username de dentro do token
        String email = extrairEmail(token);
        //compara o email do token com o username do usuario
        //se forem iguais signifca que o token pertence a aquele usuário
        //aqui ele ainda não verifica se o token expirou
        return email.equals(usuario.getUsername());
    }

}
