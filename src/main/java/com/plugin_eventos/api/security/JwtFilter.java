package com.plugin_eventos.api.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//indica que a classe sera gerenciada pelo spring
@Component
//cria automaticamenre o construtor para os atributos final
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    //serviço responsável por extrair e validar o token
    private final JwtService jwtService;

    //serviço responsável por carregar o usuário pelo email/username
    private final UserDetailsService userDetailsService;

    //metodo executado automaticamnte em cada requisição HTTP
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        //pega o cabeçalho Authorization da requisição
        String authHeader = request.getHeader("Authorization");

        //se não tiver token ou se não começar com "beareer ", a requisição segue normalmente sem autentificar usuario
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //remove a palavra "bearer " e fica apenas com o token
        String token = authHeader.substring(7);
        //extrai o emial/username salvo de dentro do token
        String email = jwtService.extrairEmail(token);

        //verrifica se encontrou algum email no token e se ainda não existe usuário autenticado na requisição
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //busca os dados do usuario pelo email
            UserDetails usuario = userDetailsService.loadUserByUsername(email);

            //verifica se o token é valido para esse usuario
            if(jwtService.tokenValido(token, usuario)){
                    //cria um objeto de autenticação para o Spring Security.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );
                    //define o usuario como autenticado no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}
