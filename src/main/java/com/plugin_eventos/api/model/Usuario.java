package com.plugin_eventos.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classe pertencente ao banco de dados
@Table(name = "usuarios") // representa qual tabela do banco de dados usado
@Data // lombok (tira os get setters etc)
@NoArgsConstructor // cria construtor vazio
@AllArgsConstructor // cria construtor com todos os valores
public class Usuario {

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
}