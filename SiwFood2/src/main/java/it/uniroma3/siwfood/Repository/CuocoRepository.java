package it.uniroma3.siwfood.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siwfood.Model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long>{
    public List<Cuoco> findByNomeContainingIgnoreCase(String nome);
}