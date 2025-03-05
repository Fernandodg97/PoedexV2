package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {
    @JsonProperty("base_stat")
    private int baseStat;
    private int effort;
    private Stat stat;

    // Getters y setters
    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    // Clase interna para representar el "stat" que contiene nombre y URL
    public static class Stat {
        private String name;  // Nombre del stat, como 'hp', 'attack', etc.
        private String url;   // URL asociada al stat (no utilizada en este caso, pero se incluye por la estructura de la API)

        // Getters y setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
