package mp3.dam.elpuig.pokedex.model;

public class Sprites {
    private String front_default;  // URL de la imagen del Pokémon

    // Constructor
    public Sprites(String front_default) {
        this.front_default = front_default;
    }

    // Getter y setter
    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}
