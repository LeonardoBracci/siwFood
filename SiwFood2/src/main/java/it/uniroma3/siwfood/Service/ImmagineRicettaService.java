package it.uniroma3.siwfood.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.Model.ImmagineRicetta;
import it.uniroma3.siwfood.Repository.ImmagineRicettaRepository;

@Service
public class ImmagineRicettaService {
    @Autowired
    private ImmagineRicettaRepository immagineRicettaRepository;

    public ImmagineRicetta findById(Long id) {
		return immagineRicettaRepository.findById(id).get();
	}

	public Iterable<ImmagineRicetta> findAll() {
		return immagineRicettaRepository.findAll();
	}

    public List<ImmagineRicetta> findAllByRicettaId(Long ricettaId) {
		return immagineRicettaRepository.findAllByRicettaId(ricettaId);
	}

    public void delete(ImmagineRicetta immagineRicetta){
        immagineRicettaRepository.delete(immagineRicetta);
    }

    public void save(ImmagineRicetta immagineRicetta){
        immagineRicettaRepository.save(immagineRicetta);
    }

    
}
