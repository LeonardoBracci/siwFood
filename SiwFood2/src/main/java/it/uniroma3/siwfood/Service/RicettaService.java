package it.uniroma3.siwfood.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Repository.RicettaRepository;

@Service
public class RicettaService {
    @Autowired 
	private RicettaRepository ricettaRepository;

	public Ricetta findById(Long id) {
		return ricettaRepository.findById(id).get();
	}

	public Iterable<Ricetta> findAll() {
		return ricettaRepository.findAll();
	}
    
    public void save(Ricetta movie){
        ricettaRepository.save(movie);
    }

	public List<Ricetta> findAllByIngredienteId(Long idIngrediente){
        return ricettaRepository.findAllByIngredientiId(idIngrediente);
    }

	public List<Ricetta> findAllByCuocoId(Long idCuoco){
        return ricettaRepository.findAllByCuocoId(idCuoco);
    }

	public void deleteById(Long id) {
        ricettaRepository.deleteById(id);
    }

    public List<Ricetta> findByNameContainingIgnoreCase(String nome){
        return ricettaRepository.findByNomeContainingIgnoreCase(nome);
    }
}
