package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignorar campos no definidos en la clase
public class Pokemon {

    private String name;
    private int id;
    private Species species;
    private double height;
    private double weight;
    private List<Type> types;  // Lista de tipos (cada tipo con un nombre)
    private Sprites sprites;

    // Constructor vacío para Jackson
    public Pokemon() {}

    // Constructor con parámetros
    public Pokemon(String name, int id, Species species, double height, double weight, List<Type> types, Sprites sprites) {
        this.name = name;
        this.id = id;
        this.species = species;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.sprites = sprites;
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    // Método adicional para crear un objeto Pokemon desde la respuesta de la API
    public static Pokemon fromApiResponse(String name, int id, Species species, double height, double weight, List<Type> types, Sprites sprites) {
        return new Pokemon(name, id, species, height, weight, types, sprites);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", species=" + species +
                ", height=" + height +
                ", weight=" + weight +
                ", types=" + types +
                ", sprites=" + sprites +
                '}';
    }
}
