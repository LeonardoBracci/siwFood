package it.uniroma3.siwfood.Model;

import java.util.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ImmagineRicetta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ricetta_id")
    private Ricetta ricetta;
        
    private byte[] base64; // Rappresentazione in base64 dell'immagine

    public ImmagineRicetta(){}
    public ImmagineRicetta(Ricetta ricetta, byte[] base64){
        this.ricetta=ricetta;
        this.base64=base64;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

    // Metodo per ottenere la stringa Base64
    public String getBase64String() {
        if (this.base64 != null) {
            return Base64.getEncoder().encodeToString(this.base64);
        }
        return "";
    }
}
