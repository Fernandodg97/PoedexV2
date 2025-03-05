package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprites {
    private String frontDefault;  // Imagen frontal por defecto
    private String frontShiny;    // Imagen frontal shiny (si existe)


    // Constructor vacío
    public Sprites() {}

    // Getters y Setters
    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    @Override
    public String toString() {
        return "Sprites{" +
                "frontDefault='" + frontDefault + '\'' +
                ", frontShiny='" + frontShiny + '\'' +
                '}';
    }
}
