package it.uniroma3.siwfood.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{
    public boolean existsByNomeAndCuoco(String nome, Cuoco cuoco);

    // Trova tutte le ricette che utilizzano un determinato ingrediente
    public List<Ricetta> findAllByIngredientiId(Long ingredienteId);
    // Trova tutte le ricette di un determinato cuoco
    public List<Ricetta> findAllByCuocoId(Long cuocoId);

    public List<Ricetta> findByNomeContainingIgnoreCase(String nome);
}
