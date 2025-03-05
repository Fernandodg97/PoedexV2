package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonData {

    private int id;
    private String name;
    private List<Type> types;  // Listado de tipos del Pokémon
    private List<Stats> stats;  // Listado de estadísticas del Pokémon
    private Sprites sprites;   // Objeto que contiene las imágenes del Pokémon

    // Constructor vacío
    public PokemonData() {}

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return "PokemonData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", types=" + types +
                ", stats=" + stats +
                ", sprites=" + sprites +
                '}';
    }
}
