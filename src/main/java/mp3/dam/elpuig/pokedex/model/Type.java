package mp3.dam.elpuig.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Type {
    private TypeDetails type;

    // Constructor vacío
    public Type() {}

    // Getter y Setter para el atributo 'type'
    public TypeDetails getType() {
        return type;
    }

    public void setType(TypeDetails type) {
        this.type = type;
    }

    // Clase interna para representar el detalle del tipo (por ejemplo, "fire", "water", etc.)
    public static class TypeDetails {
        private String name;

        @JsonIgnore  // Ignora el campo 'url'
        private String url;

        // Constructor vacío
        public TypeDetails() {}

        // Getter y Setter para el atributo 'name'
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getter y Setter para el atributo 'url'
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "Type{" +
                "type=" + type +
                '}';
    }
}
