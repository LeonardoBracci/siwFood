package it.uniroma3.siwfood.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Repository.CuocoRepository;

@Service
public class CuocoService {
    @Autowired 
	private CuocoRepository cuocoRepository;    

	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}

	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
	}
    
    public void save(Cuoco cuoco){
        cuocoRepository.save(cuoco);
    }

	public void deleteById(Long id) {
        cuocoRepository.deleteById(id);
    }

	public List<Cuoco> findByNameContainingIgnoreCase(String nome){
        return cuocoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
