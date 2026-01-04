package fr.univ.tours.jakartaee.Projet_JEE_Frontend.controller;


import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.CardConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer.ExtensionConsumer;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.dto.CardDTO;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Card;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cards")
public class CardController {


    private final CardConsumer cardConsumer;
    private final ExtensionConsumer extensionConsumer;


    public CardController(CardConsumer cardConsumer, ExtensionConsumer extensionConsumer) {
        this.cardConsumer = cardConsumer;
        this.extensionConsumer = extensionConsumer;
    }

    @ModelAttribute("cardSearch")
    public Card cardSearchInit() {
        return new Card();
    }

    @GetMapping("/search")
    public String cardSearchForm(Model model) {
        model.addAttribute("cardSearch", new Card());
        return "search";
    }

    @PostMapping("/search")
    public String cardSearch(@ModelAttribute Card card, Model model) {
        List<CardDTO> searchResult = cardConsumer.searchCards(card);
        model.addAttribute("cards", searchResult);
        model.addAttribute("search", card);

        return "search";
    }

    @ModelAttribute("newCard")
    public Card newCard() {
        return new Card();
    }

    @GetMapping("/newOrUpdate")
    public String newCardForm(Model model) {
        model.addAttribute("newCard", new Card());
        return "newOrUpdateCard";
    }

    @PostMapping("/newOrUpdate")
    public String createOrUpdateCard(@ModelAttribute Card card) {
        if(card.getId() != null) {
            cardConsumer.updateCard(String.valueOf(card.getId()), card);
            return "redirect:/cards/" + card.getId();
        }
        cardConsumer.createCard(card);
        return "redirect:/cards" + card.getId();
    }

}
