package it.uniroma3.siwfood.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Repository.CuocoRepository;

@Component
public class CuocoValidator implements Validator{
    @Autowired
    private CuocoRepository cuocoRepository;

    @Override
    public void validate(Object o, Errors errors) {
        Cuoco cuoco = (Cuoco)o;
        if (cuoco.getNome()!=null && cuoco.getCognome()!=null && cuocoRepository.existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())){
        errors.reject("cuochi.duplicati");
        }
    }
    
    @Override
    public boolean supports(Class<?> aClass) {
        return Ricetta.class.equals(aClass);
  }
    
}
