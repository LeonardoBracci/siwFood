package it.uniroma3.siwfood.Model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Admin {
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

    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private Credentials credentials;   

    public Admin(){}
    
    public Admin(String nome, String cognome, LocalDate dataDiNascita){
        this.nome=nome;
        this.cognome=cognome;
        this.dataDiNascita=dataDiNascita;
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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    @Override
    public int hashCode() {
        return this.id.hashCode() + this.nome.hashCode() + this.cognome.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Admin admin = (Admin)obj;
        return this.nome.equals(admin.nome) && this.cognome.equals(admin.cognome);
    }
}
