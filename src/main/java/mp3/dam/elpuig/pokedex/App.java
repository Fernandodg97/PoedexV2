package mp3.dam.elpuig.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mp3.dam.elpuig.pokedex.control.MainWindow;
import mp3.dam.elpuig.pokedex.connection.PokeAPIConnection;

import java.io.IOException;

public class App extends Application {
    static final String pathXML = "https://pokeapi.co/api/v2/pokemon?limit=100";  // URL de la PokeAPI

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Cargamos el archivo FXML para la interfaz de usuario de la Pokedex
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainWindow.fxml"));
        Parent root = loader.load();

        // Establecemos el título de la ventana principal
        primaryStage.setTitle("Pokedex");

        // Creamos la escena con la raíz cargada desde el FXML
        Scene scene = new Scene(root);

        // Obtenemos el controlador y le pasamos la escena y el stage
        MainWindow mainWindow = loader.getController();
        mainWindow.setStage(primaryStage);
        mainWindow.setScene(scene);

        // Inicializamos la conexión a la PokeAPI en el controlador
        PokeAPIConnection pokeAPIConnection = new PokeAPIConnection();
        mainWindow.setPokeAPIConnection(pokeAPIConnection);

        // Configuramos la escena y mostramos la ventana
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // Desactivamos la opción de redimensionar la ventana
        primaryStage.show();
    }
}
