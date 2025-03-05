package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonList {
    private int count;  // Total de Pokémon

    // Getter y Setter
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}