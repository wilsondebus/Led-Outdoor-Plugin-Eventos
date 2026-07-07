package com.plugin_eventos.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity //fala que essa é uma classe que pertence ao banco de dados
@Table(name="Produtos") //fala de quual tabela pertence
@Data //gera autimaticamente os metodos toString
@NoArgsConstructor //cria um construtor vazio
@AllArgsConstructor //cria um construtor com todos os atributos

public class Produto {

    @Id //indica que é a chave primaria
    @GeneratedValue(strategy= GenerationType.IDENTITY) //gera automaticamente o valor do id de forma crescente
    private long id;

    @Column(nullable = false, unique  = true) //define que o valor do codigo não pode ser nulo e tem que ser unico
    private String codigo;

    @Column (nullable = false) //define que o nome não pode ser nulo
    private String nome;

    @Column (name = "data_entrada")
    private LocalDate dataEntrada; //define que os valores de data_entrada vão para dataEntrada

    private String observacao; //pode ser vazio ou nulo
}
