package fr.univ.tours.jakartaee.Projet_JEE_Frontend.models;

import java.util.ArrayList;
import java.util.List;

public class Era {

    private Long id;
    private String eraname;
    private List<Extension> extensions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEraname() {
        return eraname;
    }

    public void setEraname(String eraname) {
        this.eraname = eraname;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
