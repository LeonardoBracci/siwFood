package it.uniroma3.siwfood.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siwfood.Authentication.GlobalController;
import it.uniroma3.siwfood.Model.Admin;
import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.ImmagineRicetta;
import it.uniroma3.siwfood.Model.Ingrediente;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Service.AdminService;
import it.uniroma3.siwfood.Service.CredentialsService;
import it.uniroma3.siwfood.Service.CuocoService;
import it.uniroma3.siwfood.Service.ImmagineRicettaService;
import it.uniroma3.siwfood.Service.IngredienteService;
import it.uniroma3.siwfood.Service.RicettaService;

@Controller
public class AdminController {
    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private RicettaService ricettaService;
    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ImmagineRicettaService immagineRicettaService;
    @Autowired
    private GlobalController globalController;
    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/admin")
    public String indexAdmin() {
        return "Admin/adminIndex.html";
    }

    /* ADMIN */
    @GetMapping("/admin/profilo")
    public String getProfile(Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        return "Admin/profiloAdmin.html";
    }

    @PostMapping("/admin/modificaProfilo")
    public String setProfile(@ModelAttribute Admin admin, Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin adminCorrente = this.credentialsService.getCredentials(username).getAdmin();
        adminCorrente.setNome(admin.getNome());
        adminCorrente.setCognome(admin.getCognome());
        adminCorrente.setDataDiNascita(admin.getDataDiNascita());
    
        this.adminService.save(admin);
        return "redirect:/";
    }


    @GetMapping("/admin/admin")
    public String visualizzaAdmin(Model model) {
        model.addAttribute("admins", this.adminService.findAll());
        return "Admin/admin.html";
    }
    

    /* CUOCHI */

    @GetMapping("/admin/cuochi")
    public String visualizzaCuochi(Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "Admin/cuochi.html";
    }

    @GetMapping("/admin/cuoco/{id}")
    public String visualizzaCuoco(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        model.addAttribute("ricette", this.ricettaService.findAllByCuocoId(id));
        return "Admin/cuoco.html";
    }

    @GetMapping("/admin/formAggiungiCuoco")
    public String formAggiungiCuoco(Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("cuoco", new Cuoco());
        return "Admin/aggiungiCuoco.html";
    }

    @PostMapping("/admin/aggiungiCuoco")
    public String aggiungiCuoco(@ModelAttribute("cuoco") Cuoco cuoco, @RequestParam("immagine") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                byte[] byteFoto = file.getBytes();
                cuoco.setBase64(byteFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.cuocoService.save(cuoco);
        return "redirect:/admin/cuochi";
    }


    @GetMapping("/admin/formModificaCuoco/{id}")
    public String formModificaCuoco(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "Admin/modificaCuoco.html";
    }

    @PostMapping("/admin/modificaCuoco/{id}")
    public String modificaCuoco(@PathVariable("id") Long id, @ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile file, Model model) {
        Cuoco cuocoCorrente = this.cuocoService.findById(id);
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
        return "redirect:/admin/cuoco/" + id;
    }

    @GetMapping("/admin/cancellaCuoco/{id}")
    public String cancellaCuoco(@PathVariable("id") Long id, Model model) {
        this.cuocoService.deleteById(id);
        return "redirect:/admin/cuochi";
    }

    /* RICETTE */

    @GetMapping("/admin/ricette")
    public String visualizzaRicette(Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "Admin/ricette.html";
    }

    @GetMapping("/admin/ricetta/{id}")
    public String visualizzaRicetta(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        model.addAttribute("ingredienti", this.ingredienteService.findAllByRicettaId(id));
        return "Admin/ricetta.html";
    }

    @GetMapping("/admin/formAggiungiRicetta")
    public String formAggiungiRicetta(Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("ricetta", new Ricetta());
        model.addAttribute("cuochi", this.cuocoService.findAll());
        model.addAttribute("ingrediente", new Ingrediente());
        return "Admin/aggiungiRicetta.html";
    }


    @PostMapping("/admin/aggiungiRicetta")
    public String aggiungiRicetta(@ModelAttribute("ricetta")Ricetta ricetta, 
                                    @RequestParam("images") MultipartFile[] files, 
                                    @RequestParam("cuocoId") Long cuocoId, Model model) {
        try {
            Cuoco cuoco = cuocoService.findById(cuocoId);
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
            return "redirect:/admin/ricette";
        } catch (IOException e) {
            model.addAttribute("message", "Errore durante il caricamento delle immagini per la ricetta!");
            return "Admin/aggiungiRicetta.html";
        }
    }

    @GetMapping("/admin/formModificaRicetta/{id}")
    public String formModificaRicetta(@PathVariable("id") Long id, Model model) {
        String username = this.globalController.getUser().getUsername();
        Admin admin = this.credentialsService.getCredentials(username).getAdmin();
        model.addAttribute("admin", admin);
        Ricetta ricetta = this.ricettaService.findById(id);
        if (ricetta == null) {
            return "redirect:/admin/ricette";
        }
        model.addAttribute("ricetta", ricetta);
        model.addAttribute("cuochi", this.cuocoService.findAll());
        
        model.addAttribute("ingrediente", new Ingrediente());
        return "Admin/modificaRicetta.html";
    }

    @PostMapping("/admin/modificaRicetta/{id}")
    public String modificaRicetta(@PathVariable("id") Long id, 
                        @ModelAttribute("ricetta") Ricetta ricetta,
                        @RequestParam("cuocoId") Long cuocoId, 
                        @RequestParam("images") MultipartFile[] files, 
                        Model model) {
        try {
            Ricetta ricettaCorrente = this.ricettaService.findById(id);   
            Cuoco cuoco = cuocoService.findById(cuocoId);     

            ricettaCorrente.setNome(ricetta.getNome());
            ricettaCorrente.setDescrizione(ricetta.getDescrizione());
            ricettaCorrente.setSteps(ricetta.getSteps());
            ricettaCorrente.setCuoco(cuoco);
            

            List<ImmagineRicetta> nuoveImmagini = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    byte[] byteFoto = file.getBytes();
                    ImmagineRicetta immagine = new ImmagineRicetta();
                    immagine.setBase64(byteFoto);
                    immagine.setRicetta(ricettaCorrente);
                    nuoveImmagini.add(immagine);
                }
            }
            if (!nuoveImmagini.isEmpty()) {
                ricettaCorrente.getImmagini().addAll(nuoveImmagini);
            }
            this.ricettaService.save(ricettaCorrente);
            return "redirect:/admin/ricetta/" + id;
        } catch (IOException e) {
            model.addAttribute("message", "Errore durante il caricamento delle immagini per la ricetta!");
            return "Admin/modificaRicetta.html";
        }
    }

    @PostMapping("/admin/modificaRicetta/{id}/eliminaImmagine/{idImmagine}")
    public String eliminaImmaginePost(@PathVariable("id") Long idRicetta, @PathVariable("idImmagine") Long idImmagine, Model model) {
        try {
            Ricetta ricetta = this.ricettaService.findById(idRicetta);
            ImmagineRicetta immagineDaEliminare = this.immagineRicettaService.findById(idImmagine);

            ricetta.getImmagini().remove(immagineDaEliminare);
            this.immagineRicettaService.delete(immagineDaEliminare);

            this.ricettaService.save(ricetta);
            return "redirect:/admin/formModificaRicetta/" + idRicetta;
        } catch (Exception e) {
            model.addAttribute("message", "Errore durante l'eliminazione dell'immagine");
            return "redirect:/admin/ricette";
        }
    }

    @GetMapping("/admin/cancellaRicetta/{id}")
    public String cancellaRicetta(@PathVariable("id") Long id, Model model) {
        this.ricettaService.deleteById(id);
        return "redirect:/admin/ricette";
    }

    /* INGREDIENTI */

    @GetMapping("/admin/ingrediente/{id}")
    public String visualizzaIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.findById(id));
        model.addAttribute("ricette", this.ricettaService.findAllByIngredienteId(id));
        return "Admin/ingrediente.html";
    }
    
    @GetMapping("/admin/formAggiungiIngrediente/{id}")
    public String formAggiungiIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "Admin/aggiungiIngrediente.html";
    }

    @PostMapping("/admin/aggiungiIngrediente/{id}")
    public String aggiungiIngrediente(@PathVariable("id") Long id, @ModelAttribute("ingrediente") Ingrediente ingrediente) {
        Ricetta ricetta = this.ricettaService.findById(id);
        Ingrediente nuovoIngrediente = new Ingrediente();

        nuovoIngrediente.setNome(ingrediente.getNome());
        nuovoIngrediente.setQuantita(ingrediente.getQuantita());

        this.ingredienteService.save(nuovoIngrediente);
        ricetta.getIngredienti().add(nuovoIngrediente);        
        this.ricettaService.save(ricetta);
        return "redirect:/admin/formModificaRicetta/" + ricetta.getId();
    }

    @GetMapping("/admin/formModificaIngrediente/{idRicetta}/{id}")
    public String formModificaIngrediente(@PathVariable("id") Long id, 
    @PathVariable("idRicetta") Long idRicetta,Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.findById(id));
        model.addAttribute("ricetta", this.ricettaService.findById(idRicetta));
        return "Admin/modificaIngrediente.html";
    }

    @PostMapping("/admin/modificaIngrediente/{idRicetta}/{id}")
    public String modificaIngrediente(@PathVariable("id") Long id, @PathVariable("idRicetta") Long idRicetta,
    @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
        Ingrediente ingredienteCorrente = this.ingredienteService.findById(id);

        ingredienteCorrente.setNome(ingrediente.getNome());
        ingredienteCorrente.setQuantita(ingrediente.getQuantita());
    
        this.ingredienteService.save(ingredienteCorrente);
        return "redirect:/admin/formModificaRicetta/" + idRicetta;
    }

    @GetMapping("/admin/rimuoviIngrediente/{ricettaId}/{ingredienteId}")
    public String rimuoviIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId, Model model) {
        Ricetta ricetta = this.ricettaService.findById(ricettaId);
        Ingrediente ingrediente = this.ingredienteService.findById(ingredienteId);
        
        ricetta.getIngredienti().remove(ingrediente);
        ingrediente.getRicette().remove(ricetta);

        this.ricettaService.save(ricetta);
        /*DA CAPIRE */
        this.ingredienteService.deleteById(ingredienteId);
        
        return "redirect:/admin/formModificaRicetta/" + ricettaId;
    }
}