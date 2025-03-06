package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprites {
    @JsonProperty("front_default")  // Asegúrate de que el nombre del campo coincida con el JSON
    private String frontDefault;

    @JsonProperty("front_shiny")  // Lo mismo para front_shiny
    private String frontShiny;

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
