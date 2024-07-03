package it.uniroma3.siwfood.Model;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cuoco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome e' obbligatorio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Cognome e' obbligatorio")
    @Column(nullable = false)
    private String cognome;

    private LocalDate dataDiNascita;

    @Column(length = 1000000)
    private String biografia;

    

    private byte[] base64;

    @OneToOne(mappedBy = "cuoco",cascade = CascadeType.ALL)
    private Credentials credentials;    

    @OneToMany(mappedBy = "cuoco",cascade = CascadeType.ALL)
    private List<Ricetta> ricette;

    
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public void setRicette(List<Ricetta> ricette) {
        this.ricette = ricette;
    }

    public byte[] getBase64() {
            return base64;
    }

    public void setBase64(byte[] base64) {
            this.base64 = base64;
    }

    // Metodo per ottenere la stringa Base64
    public String base64String() {
        if (this.base64 != null) {
            return Base64.getEncoder().encodeToString(this.base64);
        }
        return "";
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode() + this.nome.hashCode() + this.cognome.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Cuoco cuoco = (Cuoco)obj;
        return this.nome.equals(cuoco.nome) && this.cognome.equals(cuoco.cognome);
    }
}
