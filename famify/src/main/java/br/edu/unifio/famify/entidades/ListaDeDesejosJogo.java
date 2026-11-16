package br.edu.unifio.famify.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"biblioteca_id", "jogo_id"}
        )
    }
)
public class ListaDeDesejosJogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lista_desejo_id")
    private ListaDeDesejos listaDeDesejos;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;
}
