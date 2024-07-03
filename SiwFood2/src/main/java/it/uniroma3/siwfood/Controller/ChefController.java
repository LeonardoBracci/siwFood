package it.uniroma3.siwfood.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.Authentication.GlobalController;
import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.ImmagineRicetta;
import it.uniroma3.siwfood.Model.Ingrediente;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Service.CredentialsService;
import it.uniroma3.siwfood.Service.CuocoService;
import it.uniroma3.siwfood.Service.ImmagineRicettaService;
import it.uniroma3.siwfood.Service.IngredienteService;
import it.uniroma3.siwfood.Service.RicettaService;

@Controller
public class ChefController {
    @Autowired
    private GlobalController globalController;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private RicettaService ricettaService;
    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private ImmagineRicettaService immagineRicettaService;
    @Autowired
    private CuocoService cuocoService;

    @GetMapping("/chef")
    public String indexChef() {
        return "Chef/chefIndex.html";
    }

    @GetMapping("/chef/profilo")
    public String getProfile(Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        return "Chef/profiloChef.html";
    }

    @PostMapping("/chef/modificaProfilo")
    public String setProfile(@ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile file, Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuocoCorrente = this.credentialsService.getCredentials(username).getCuoco();
        cuocoCorrente.setNome(cuoco.getNome());
        cuocoCorrente.setCognome(cuoco.getCognome());
        cuocoCorrente.setDataDiNascita(cuoco.getDataDiNascita());
        cuocoCorrente.setBiografia(cuoco.getBiografia());
        /*AGGIUNGI ATTRIBUTI */
        if (!file.isEmpty()) {
            try {
                byte[] byteFoto = file.getBytes();
                cuocoCorrente.setBase64(byteFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.cuocoService.save(cuocoCorrente);
        return "redirect:/";
    }

    /* CUOCHI */

    @GetMapping("/chef/cuochi")
    public String visualizzaCuochi(Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "Chef/cuochi.html";
    }

    @GetMapping("/chef/cuoco/{id}")
    public String visualizzaCuoco(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("io", cuoco);
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        model.addAttribute("ricette", this.ricettaService.findAllByCuocoId(id));
        return "Chef/cuoco.html";
    }
    
    

    /* RICETTE */

    @GetMapping("/chef/ricette")
    public String visualizzaRicette(Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        model.addAttribute("ricette", this.ricettaService.findAllByCuocoId(cuoco.getId()));
        return "Chef/ricette.html";
    }

    @GetMapping("/chef/ricetta/{id}")
    public String visualizzaRicetta(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        model.addAttribute("ingredienti", this.ingredienteService.findAllByRicettaId(id));
        return "Chef/ricetta.html";
    }

    @GetMapping("/chef/tutteLeRicette")
    public String visualizzaTutteLeRicette(Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "Chef/tutteLeRicette.html";
    }

    @GetMapping("/chef/formAggiungiRicetta")
    public String formAggiungiRicetta(Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        model.addAttribute("ricetta", new Ricetta());
        model.addAttribute("ingrediente", new Ingrediente());
        return "Chef/aggiungiRicetta.html";
    }

    @PostMapping("/chef/aggiungiRicetta")
    public String aggiungiRicetta(@ModelAttribute("ricetta") Ricetta ricetta, @RequestParam("images") MultipartFile[] files, Model model) {
        try {
            String username = this.globalController.getUser().getUsername();
            Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
            ricetta.setCuoco(cuoco);

            List<ImmagineRicetta> immagini = new ArrayList<>();

            for (MultipartFile file : files) {
                byte[] byteFoto = file.getBytes();
                ImmagineRicetta immagine = new ImmagineRicetta();
                immagine.setBase64(byteFoto);
                immagine.setRicetta(ricetta);
                immagini.add(immagine);
            }
            ricetta.setImmagini(immagini);
            ricettaService.save(ricetta);
            model.addAttribute("message", "Ricetta aggiunta con successo!");
            return "redirect:/chef/ricette";
        } catch (IOException e) {
            model.addAttribute("message", "Errore durante il caricamento delle immagini per la ricetta!");
            return "Chef/aggiungiRicetta.html";
        }
    }


    @GetMapping("/chef/formModificaRicetta/{id}")
    public String formModificaRicetta(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Cuoco cuoco = this.credentialsService.getCredentials(username).getCuoco();
        model.addAttribute("cuoco", cuoco);
        Ricetta ricetta = this.ricettaService.findById(id);
        if (ricetta == null) {
            return "redirect:/chef/ricette";
        }
        model.addAttribute("ricetta", ricetta);
        model.addAttribute("ingrediente", new Ingrediente());
        return "Chef/modificaRicetta.html";
    }

    @PostMapping("/chef/modificaRicetta/{id}")
    public String modificaRicetta(@PathVariable("id") Long id, @ModelAttribute("ricetta") Ricetta ricetta, 
                                @RequestParam("images") MultipartFile[] files, Model model) {
        try {
            // Carica la ricetta esistente dal database usando l'ID della ricetta passata dal form
            Ricetta ricettaCorrente = this.ricettaService.findById(id);        

            // Aggiorna i campi della ricetta esistente
            ricettaCorrente.setNome(ricetta.getNome());
            ricettaCorrente.setDescrizione(ricetta.getDescrizione());
            ricettaCorrente.setSteps(ricetta.getSteps());

            // Carica e aggiorna le nuove immagini
            List<ImmagineRicetta> nuoveImmagini = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    byte[] byteFoto = file.getBytes();

                    ImmagineRicetta immagine = new ImmagineRicetta();
                    immagine.setBase64(byteFoto); // Usa byte[] invece di String base64
                    immagine.setRicetta(ricettaCorrente);
                    nuoveImmagini.add(immagine);
                }
            }
            // Aggiungi le nuove immagini alla ricetta corrente
            if (!nuoveImmagini.isEmpty()) {
                ricettaCorrente.getImmagini().addAll(nuoveImmagini);
            }
            // Salva la ricetta aggiornata
            this.ricettaService.save(ricettaCorrente);
            return "redirect:/chef/ricetta/" + id;
        } catch (IOException e) {
            model.addAttribute("message", "Errore durante il caricamento delle immagini per la ricetta!");
            return "Chef/modificaRicetta.html";
        }
    }

    @PostMapping("/chef/modificaRicetta/{id}/eliminaImmagine/{idImmagine}")
    public String eliminaImmaginePost(@PathVariable("id") Long idRicetta, @PathVariable("idImmagine") Long idImmagine, Model model) {
        try {
            // Trova la ricetta dal database
            Ricetta ricetta = this.ricettaService.findById(idRicetta);
            
            // Trova l'immagine da eliminare
            ImmagineRicetta immagineDaEliminare = this.immagineRicettaService.findById(idImmagine);

            // Rimuovi l'immagine dalla lista delle immagini della ricetta
            ricetta.getImmagini().remove(immagineDaEliminare);
            
            // Salva la ricetta aggiornata
            this.ricettaService.save(ricetta);

            // Elimina fisicamente l'immagine
            this.immagineRicettaService.delete(immagineDaEliminare);
        
            // Redirect alla pagina di modifica della ricetta
            return "redirect:/chef/formModificaRicetta/" + idRicetta;
            
        } catch (Exception e) {
            model.addAttribute("message", "Errore durante l'eliminazione dell'immagine");
            // Gestisci l'errore come preferisci, ad esempio mostrando un messaggio d'errore
            return "redirect:/chef/ricette";
        }
    }



    @GetMapping("/chef/cancellaRicetta/{id}")
    public String cancellaRicetta(@PathVariable("id") Long id, Model model) {
        this.ricettaService.deleteById(id);
        return "redirect:/chef/ricette";
    }

    /* INGREDIENTI */

    @GetMapping("/chef/ingrediente/{id}")
    public String visualizzaIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.findById(id));
        model.addAttribute("ricette", this.ricettaService.findAllByIngredienteId(id));
        return "Chef/ingrediente.html";
    }

    @GetMapping("/chef/formAggiungiIngrediente/{id}")
    public String formAggiungiIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "Chef/aggiungiIngrediente.html";
    }

    @PostMapping("/chef/aggiungiIngrediente/{id}")
    public String aggiungiIngrediente(@PathVariable("id") Long id, @ModelAttribute("ingrediente") Ingrediente ingrediente) {
        Ricetta ricetta = this.ricettaService.findById(id);
        // Crea un nuovo oggetto Ingrediente
        Ingrediente nuovoIngrediente = new Ingrediente();
        nuovoIngrediente.setNome(ingrediente.getNome());
        nuovoIngrediente.setQuantita(ingrediente.getQuantita());

        // Salva l'ingrediente con la relazione aggiornata
        this.ingredienteService.save(nuovoIngrediente);

        // Aggiungi l'ingrediente alla lista degli ingredienti della ricetta
        ricetta.getIngredienti().add(nuovoIngrediente);        

        // Salva la ricetta con l'ingrediente aggiunto
        this.ricettaService.save(ricetta);

        return "redirect:/chef/formModificaRicetta/" + ricetta.getId();
    }

    @GetMapping("/chef/formModificaIngrediente/{id}")
    public String formModificaIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.findById(id));
        return "Chef/modificaIngrediente.html";
    }

    @PostMapping("/chef/modificaIngrediente/{ricettaId}/{id}")
    public String modificaIngrediente(@PathVariable("ricettaId") Long ricettaId,@PathVariable("id") Long id, @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
        Ingrediente ingredienteEsistente = this.ingredienteService.findById(id);
        // Aggiorna i campi dell'ingrediente esistente
        ingredienteEsistente.setNome(ingrediente.getNome());
        ingredienteEsistente.setQuantita(ingrediente.getQuantita());
        
        // Salva l'ingrediente aggiornato
        this.ingredienteService.save(ingredienteEsistente);
        return "redirect:/chef/formModificaRicetta/" + ricettaId;
    }


    @GetMapping("/chef/rimuoviIngrediente/{ricettaId}/{ingredienteId}")
    public String rimuoviIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId, Model model) {
        Ricetta ricetta = this.ricettaService.findById(ricettaId);
        Ingrediente ingrediente = this.ingredienteService.findById(ingredienteId);
        
        ricetta.getIngredienti().remove(ingrediente);
        ingrediente.getRicette().remove(ricetta);

        this.ricettaService.save(ricetta);
        /*DA CAPIRE */
        this.ingredienteService.deleteById(ingredienteId);
        
        return "redirect:/chef/ricetta/" + ricettaId;
    }
}
