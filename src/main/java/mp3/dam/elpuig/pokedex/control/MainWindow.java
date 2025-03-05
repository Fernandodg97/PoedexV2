package mp3.dam.elpuig.pokedex.control;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mp3.dam.elpuig.pokedex.connection.PokeAPIConnection;
import mp3.dam.elpuig.pokedex.model.Pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow {

    @FXML private ListView<String> pokemonList;
    @FXML private TextArea resultArea;
    @FXML private ImageView imageView;
    @FXML private TextField searchField;
    @FXML private Button loadAllButton;
    @FXML private Label statusLabel;

    private final List<Pokemon> allPokemons = new ArrayList<>();
    private boolean allPokemonsLoaded = false; // Para evitar recargar

    @FXML
    public void initialize() {
        pokemonList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onPokemonSelected(newVal));
    }

    public void start(Stage primaryStage) {
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
        mainLayout.setCenter(resultArea);
        mainLayout.setBottom(imageView);

        // Crear escena y mostrar ventana
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Cargar los 151 Pokémon de la primera generación en un hilo separado
        loadPokemonList(151);
    }

    private void loadPokemonList(int limit) {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws IOException {
                try {
                    for (int i = 1; i <= limit; i++) {
                        Pokemon pokemon = PokeAPIConnection.getPokemonById(i);
                        allPokemons.add(pokemon);
                        int finalI = i;
                        javafx.application.Platform.runLater(() -> {
                            pokemonList.getItems().add(pokemon.getId() + " " + pokemon.getName());
                            statusLabel.setText("Cargando Pokémon... " + finalI + "/" + limit);
                        });
                    }
                    javafx.application.Platform.runLater(() -> statusLabel.setText("Carga completa"));
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> statusLabel.setText("Error al cargar Pokémon"));
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void loadAllPokemon() {
        if (allPokemonsLoaded) return; // Evita recargar si ya están todos cargados

        loadAllButton.setDisable(true);
        statusLabel.setText("Cargando todos los Pokémon...");

        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws IOException {
                try {
                    int totalPokemons = PokeAPIConnection.getTotalPokemons();
                    for (int i = 152; i <= totalPokemons; i++) {
                        Pokemon pokemon = PokeAPIConnection.getPokemonById(i);
                        allPokemons.add(pokemon);
                        int finalI = i;
                        javafx.application.Platform.runLater(() -> {
                            pokemonList.getItems().add(pokemon.getId() + " " + pokemon.getName());
                            statusLabel.setText("Cargando Pokémon... " + finalI + "/" + totalPokemons);
                        });
                    }
                    allPokemonsLoaded = true;
                    javafx.application.Platform.runLater(() -> statusLabel.setText("Todos los Pokémon cargados"));
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> statusLabel.setText("Error al cargar Pokémon"));
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void onPokemonSelected(String selected) {
        if (selected == null) return;

        int id = Integer.parseInt(selected.split(" ")[0]);
        Pokemon selectedPokemon = allPokemons.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (selectedPokemon != null) {
            // Construir el texto con la información del Pokémon
            StringBuilder resultText = new StringBuilder();
            resultText.append("ID: ").append(selectedPokemon.getId()).append("\n");
            resultText.append("Nombre: ").append(selectedPokemon.getName()).append("\n");

            // Mostrar los tipos del Pokémon separados por comas
            resultText.append("Tipos: ").append(String.join(", ", selectedPokemon.getTypes())).append("\n");

            // Mostrar las estadísticas del Pokémon
            resultText.append("HP: ").append(selectedPokemon.getHp()).append("\n");
            resultText.append("Ataque: ").append(selectedPokemon.getAttack()).append("\n");
            resultText.append("Defensa: ").append(selectedPokemon.getDefense()).append("\n");
            resultText.append("Ataque Especial: ").append(selectedPokemon.getSpecialAttack()).append("\n");
            resultText.append("Defensa Especial: ").append(selectedPokemon.getSpecialDefense()).append("\n");
            resultText.append("Velocidad: ").append(selectedPokemon.getSpeed()).append("\n");

            // Actualizar el área de texto en JavaFX
            resultArea.setText(resultText.toString());

            // Cargar imagen del Pokémon
            if (selectedPokemon.getImageUrl() != null && !selectedPokemon.getImageUrl().isEmpty()) {
                imageView.setImage(new Image(selectedPokemon.getImageUrl()));
            } else {
                // Imagen por defecto en caso de URL nula o vacía
                imageView.setImage(new Image(getClass().getResource("/mp3/dam/elpuig/pokedex/fxml/images/default.png").toExternalForm()));
            }
        }
    }

    @FXML
    private void searchPokemon() {
        String query = searchField.getText().trim().toLowerCase();
        for (String item : pokemonList.getItems()) {
            if (item.toLowerCase().contains(query)) {
                pokemonList.getSelectionModel().select(item);
                onPokemonSelected(item);
                break;
            }
        }
    }
}
