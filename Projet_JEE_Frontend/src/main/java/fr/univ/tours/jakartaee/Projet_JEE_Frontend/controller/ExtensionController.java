package fr.univ.tours.jakartaee.Projet_JEE_Frontend.controller;


import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.CardConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.ExtensionConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Card;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Extension;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/extensions")
public class ExtensionController {

    private final ExtensionConsumer extensionConsumer;

    public ExtensionController(ExtensionConsumer extensionConsumer) {
        this.extensionConsumer = extensionConsumer;
    }

    @ModelAttribute("newExt")
    public Extension newExt() {
        return new Extension();
    }

    @GetMapping("/newExtension")
    public String newExtForm(Model model) {
        model.addAttribute("newExt", new Extension());
        return "newExtension";
    }

    @PostMapping("/newExtension")
    public String createExt(@ModelAttribute Extension ext) {
        extensionConsumer.createExtension(ext);
        return "redirect:/extensions" + ext.getId();
    }
}
