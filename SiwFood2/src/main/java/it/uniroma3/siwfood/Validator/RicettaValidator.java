package it.uniroma3.siwfood.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Repository.RicettaRepository;

@Component
public class RicettaValidator implements Validator {
  @Autowired
  private RicettaRepository ricettaRepository;

  @Override
  public void validate(Object o, Errors errors) {
    Ricetta ricetta = (Ricetta)o;
    if (ricetta.getNome()!=null && ricetta.getCuoco()!=null && ricettaRepository.existsByNomeAndCuoco(ricetta.getNome(), ricetta.getCuoco())){
      errors.reject("ricetta.duplicate");
    }
  }
  
  @Override
  public boolean supports(Class<?> aClass) {
    return Ricetta.class.equals(aClass);
  }
}