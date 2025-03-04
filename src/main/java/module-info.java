module mp3.dam.elpuig.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens mp3.dam.elpuig.pokedex.fxml to javafx.fxml;
    opens mp3.dam.elpuig.pokedex.control to javafx.fxml;
    opens mp3.dam.elpuig.pokedex.model to java.xml.bind;
    exports mp3.dam.elpuig.pokedex;
    exports mp3.dam.elpuig.pokedex.control;
    exports mp3.dam.elpuig.pokedex.model;
}
