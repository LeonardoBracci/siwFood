package it.uniroma3.siwfood.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siwfood.Model.Ingrediente;
import it.uniroma3.siwfood.Repository.IngredienteRepository;

@Service
public class IngredienteService {
    @Autowired 
	private IngredienteRepository ingredienteRepository;

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	public Iterable<Ingrediente> findAll() {
		return ingredienteRepository.findAll();
	}
    
    public void save(Ingrediente movie){
        ingredienteRepository.save(movie);
    }
	
	public List<Ingrediente> findAllByRicettaId(Long idRicetta){
		return ingredienteRepository.findAllByRicetteId(idRicetta);
	}

	public void deleteById(Long id) {
        ingredienteRepository.deleteById(id);
    }

	public List<Ingrediente> findByNameContainingIgnoreCase(String nome){
        return ingredienteRepository.findByNomeContainingIgnoreCase(nome);
    }
}
