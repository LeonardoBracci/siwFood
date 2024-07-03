package it.uniroma3.siwfood.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.Model.ImmagineRicetta;
public interface ImmagineRicettaRepository extends CrudRepository<ImmagineRicetta, Long>{
    // Trova tutti gli ingredienti associati a una determinata ricetta
    public List<ImmagineRicetta> findAllByRicettaId(Long ricettaId);
}
