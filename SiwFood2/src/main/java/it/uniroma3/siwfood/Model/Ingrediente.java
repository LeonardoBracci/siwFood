package it.uniroma3.siwfood.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String quantita;

    @ManyToMany(mappedBy = "ingredienti")
    private List<Ricetta> ricette;

    

    // Costruttore di default richiesto da JPA
    public Ingrediente() {}

    public Ingrediente(String nome, String quantita){
        this.nome=nome;
        this.quantita=quantita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public void setRicette(List<Ricetta> ricette) {
        this.ricette = ricette;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() + this.nome.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Ingrediente ingrediente = (Ingrediente)obj;
        return this.nome.equals(ingrediente.nome);
    }

    
}
