package it.uniroma3.siwfood.Repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.Model.Ingrediente;

import java.util.List;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
    // Trova tutti gli ingredienti associati a una determinata ricetta
    public List<Ingrediente> findAllByRicetteId(Long ricettaId);

    public List<Ingrediente> findByNomeContainingIgnoreCase(String nome);
}
