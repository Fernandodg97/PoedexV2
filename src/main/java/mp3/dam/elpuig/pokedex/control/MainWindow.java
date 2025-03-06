package mp3.dam.elpuig.pokedex.control;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mp3.dam.elpuig.pokedex.connection.PokeAPIConnection;
import mp3.dam.elpuig.pokedex.model.Pokemon;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow implements Initializable {

    public Label pokemonNameLabel;
    public Label pokemonIdLabel;
    private Scene scene;
    private Stage stage;

    @FXML
    private ListView<String> pokemonList;
    @FXML
    private TextArea resultArea;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchField;
    @FXML
    private Button loadAllButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button prevButton;  // Botón de "Anterior"
    @FXML
    private Button nextButton;  // Botón de "Siguiente"

    private final List<Pokemon> allPokemons = new ArrayList<>();
    private boolean allPokemonsLoaded = false;

    private int currentIndex = 0;  // Índice para navegación

    public MainWindow() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Iniciando...");
        pokemonList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onPokemonSelected(newVal));
    }

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void loadPokemonList(int limit) {
        System.out.println("Cargando 151 pokemons");
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws IOException {
                try {
                    for (int i = 1; i <= limit; i++) {
                        Pokemon pokemon = PokeAPIConnection.getPokemonById(i);
                        allPokemons.add(pokemon);
                        int finalI = i;
                        javafx.application.Platform.runLater(() -> {
                            // Formatear el ID a tres dígitos (001, 002, ...)
                            String formattedId = String.format("%03d", pokemon.getId());
                            pokemonList.getItems().add(formattedId + " - " + pokemon.getName());
                            statusLabel.setText("Cargando Pokémon... " + finalI + "/" + limit);

                            // Seleccionar el primer Pokémon y mostrar sus datos
                            if (finalI == 1) {
                                pokemonList.getSelectionModel().selectFirst();
                                onPokemonSelected(pokemonList.getSelectionModel().getSelectedItem());
                            }
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
        if (allPokemonsLoaded) return;

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
            // **Actualizar el índice actual basado en la selección**
            currentIndex = allPokemons.indexOf(selectedPokemon);

            // Mostrar la información del Pokémon
            showPokemonInfo(selectedPokemon);
        }
    }

    private void showPokemonInfo(Pokemon pokemon) {
        StringBuilder resultText = new StringBuilder();


        // Mostrar los tipos
        resultText.append("Tipos: ");
        for (int i = 0; i < pokemon.getTypes().size(); i++) {
            resultText.append(pokemon.getTypes().get(i));
            if (i < pokemon.getTypes().size() - 1) resultText.append(", ");
        }
        resultText.append("\n");

        // Mostrar estadísticas
        resultText.append("HP: ").append(pokemon.getHp()).append("\n");
        resultText.append("Ataque: ").append(pokemon.getAttack()).append("\n");
        resultText.append("Defensa: ").append(pokemon.getDefense()).append("\n");
        resultText.append("Ataque Especial: ").append(pokemon.getSpecialAttack()).append("\n");
        resultText.append("Defensa Especial: ").append(pokemon.getSpecialDefense()).append("\n");
        resultText.append("Velocidad: ").append(pokemon.getSpeed()).append("\n");

        resultArea.setText(resultText.toString());

        // Cambiar el texto del nombre y el ID
        Label nameLabel = (Label) scene.lookup("#pokemonNameLabel");
        Label idLabel = (Label) scene.lookup("#pokemonIdLabel");

        if (nameLabel != null) {
            nameLabel.setText(pokemon.getName());
        }
        if (idLabel != null) {
            idLabel.setText(String.format("%03d", pokemon.getId()));  // ID formateado como 001, 002, ...
        }

        // Cargar imagen
        if (pokemon.getImageUrl() != null && !pokemon.getImageUrl().isEmpty()) {
            imageView.setImage(new Image(pokemon.getImageUrl()));
        } else {
            imageView.setImage(new Image(getClass().getResource("/mp3/dam/elpuig/pokedex/fxml/images/default.png").toExternalForm()));
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

    // Método para el botón Anterior
    @FXML
    public void previousPokemon() {
        if (currentIndex > 0) {
            currentIndex--;
            showPokemonInfo(allPokemons.get(currentIndex));
        }
    }

    // Método para el botón Siguiente
    @FXML
    public void nextPokemon() {
        if (currentIndex < allPokemons.size() - 1) {
            currentIndex++;
            showPokemonInfo(allPokemons.get(currentIndex));
        }
    }
}