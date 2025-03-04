package mp3.dam.elpuig.pokedex.model;

public class Species {

    private String name;

    // Constructor
    public Species(String name) {
        this.name = name;
    }

    // Getter y Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Species{" +
                "name='" + name + '\'' +
                '}';
    }
}
