package fr.univ.tours.jakartaee.Projet_JEE_Frontend.controller;


import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.EraConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.ExtensionConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Era;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Extension;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/eras")
public class EraController {

    private final EraConsumer eraConsumer;

    public EraController(EraConsumer eraConsumer) {
        this.eraConsumer = eraConsumer;
    }

    @ModelAttribute("newEra")
    public Era newEra() {
        return new Era();
    }

    @GetMapping("/newEra")
    public String newEraForm(Model model) {
        model.addAttribute("newEra", new Era());
        return "newEra";
    }

    @PostMapping("/newEra")
    public String createExt(@ModelAttribute Era era) {
        eraConsumer.createEra(era);
        return "redirect:/era" + era.getId();
    }

    @GetMapping("/")
    public String getAllEras(Model model) {
        model.addAttribute("eras", new ArrayList<Era>());
        return "eras";
    }

    @PostMapping("/")
    public String getAllEras(@ModelAttribute List<Era> eras) {
        eras = eraConsumer.getAllEras();
        return "eras";
    }
}
