package mp3.dam.elpuig.pokedex;

import javafx.application.Application;
import javafx.stage.Stage;
import mp3.dam.elpuig.pokedex.control.MainWindow;
/*#####*/
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

import static javafx.application.Application.launch;

public class PokedexApp extends Application{
    /**/
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mp3/dam/elpuig/pokedex/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Pokedex");
        Scene scene = new Scene(root);

        // Llamar al controlador
        MainWindow mainWindow = loader.getController();
        mainWindow.setStage(primaryStage);
        mainWindow.setScene(scene);

        // Crear escena y mostrar ventana
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Cargar los 151 Pokémon de la primera generación en un hilo separado
        mainWindow.loadPokemonList(151);
    }
    /**/
    /*

     primaryStage.setTitle("Pokedex");

        // Lista de Pokémon
        pokemonList = new ListView<>();
        pokemonList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onPokemonSelected(newVal));

        // Campo de búsqueda y botón
        searchField = new TextField();
        Button searchButton = new Button("Buscar");
        searchButton.setOnAction(e -> searchPokemon());

        // Botón para cargar todos los Pokémon
        loadAllButton = new Button("Cargar Todos");
        loadAllButton.setOnAction(e -> loadAllPokemon());

        // Estado de carga
        statusLabel = new Label("Cargando...");

        // Área de información del Pokémon
        resultArea = new TextArea();
        resultArea.setEditable(false);

        // Imagen del Pokémon
        imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // Layout principal
        VBox leftPanel = new VBox(10, searchField, searchButton, pokemonList, loadAllButton, statusLabel);
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftPanel);

        // Crear una caja para las estadísticas y la imagen
        VBox rightPanel = new VBox(10);
        rightPanel.getChildren().addAll(imageView, resultArea);

        mainLayout.setCenter(rightPanel);

        // Crear escena y mostrar ventana
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Cargar los 151 Pokémon de la primera generación en un hilo separado
        loadPokemonList(151);

    */

    // ################################################################################
}

