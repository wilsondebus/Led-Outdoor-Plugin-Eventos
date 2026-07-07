//serve pra acessar os dados do banco sem que precise mexer nele
package com.plugin_eventos.api.repository;

import com.plugin_eventos.api.model.Usuario; //importa a classe usuario e essa classe representa a tabela do bd
import org.springframework.data.jpa.repository.JpaRepository; //vem com metodos prontos para acessar o bd
import java.util.Optional; //usado quando a busca pode ou não encontrar um resuultado

//interface responsavel por acessar os dados da entidade usuario
//o extends quer dizer que o usuarioRepository controla os dados da tebela usuarios
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //cria um metodo para buscar um usuario por email
    //"procure um usuario onde o campo email seja igual ao valor recebido por parametro"
    Optional<Usuario> findByEmail(String email);
}