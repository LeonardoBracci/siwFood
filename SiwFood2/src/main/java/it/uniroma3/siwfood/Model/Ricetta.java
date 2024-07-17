package it.uniroma3.siwfood.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ricetta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Column(length = 10000000)
    private String descrizione;

    @Column(length = 10000000)
    private List<String> steps = new ArrayList<>();    

    @ManyToOne
    private Cuoco cuoco;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingrediente> ingredienti;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL)
    private List<ImmagineRicetta> immagini = new ArrayList<>();


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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Cuoco getCuoco() {
        return cuoco;
    }

    public void setCuoco(Cuoco cuoco) {
        this.cuoco = cuoco;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public List<ImmagineRicetta> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<ImmagineRicetta> immagini) {
        this.immagini = immagini;
    }
    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() + this.nome.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Ricetta ricetta = (Ricetta)obj;
        return this.nome.equals(ricetta.nome);
    }
}
