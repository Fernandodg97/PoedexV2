package mp3.dam.elpuig.pokedex;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import mp3.dam.elpuig.pokedex.control.MainWindow;
import java.io.IOException;

public class PokedexApp extends Application {

    public static void main(String[] args) {
        launch(args);  // Inicia la aplicación JavaFX
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Cargar el archivo FXML de la interfaz gráfica
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mp3/dam/elpuig/pokedex/fxml/MainWindow.fxml"));
        Parent root = loader.load();

        // Configurar la ventana principal
        primaryStage.setTitle("Pokedex");
        Scene scene = new Scene(root);

        // Obtener el controlador y pasarle la escena y el stage
        MainWindow mainWindow = loader.getController();
        mainWindow.setStage(primaryStage);
        mainWindow.setScene(scene);

        // Configurar la ventana y mostrarla
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // Bloquear el redimensionamiento de la ventana
        primaryStage.show();

        // Cargar los 151 Pokémon de la primera generación en un hilo separado
        mainWindow.loadPokemonList(151);
    }
}
