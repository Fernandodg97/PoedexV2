package mp3.dam.elpuig.pokedex.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mp3.dam.elpuig.pokedex.connection.PokeAPIConnection;
import mp3.dam.elpuig.pokedex.model.Pokemon;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindow implements Initializable {
    private Scene scene;
    private Stage stage;
    private PokeAPIConnection pokeAPIConnection;

    private BarChart<String, Number> barChart;
    private PieChart pieChart;
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    private List<Pokemon> pokemons;

    @FXML
    HBox hBox0;
    @FXML
    VBox vBox0;
    @FXML
    ComboBox<String> cmbComarca;

    // Nuevos elementos para mostrar los detalles del Pokémon
    @FXML
    private Label lblPokemonName;
    @FXML
    private Label lblPokemonId;
    @FXML
    private Label lblPokemonSpecies;
    @FXML
    private Label lblPokemonHeight;
    @FXML
    private Label lblPokemonWeight;
    @FXML
    private ImageView ivPokemonImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.printf("init %s%n", url.toString());
        pokeAPIConnection = new PokeAPIConnection();  // Inicializamos la conexión con la API

        // Add a listener to the ComboBox to update the BarChart based on the selected category
        cmbComarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateBarChart(newValue);
            }
        });
    }

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void clickMenuItemLoad(ActionEvent actionEvent) {
        // Realizamos una consulta a la API para obtener los Pokémon
        try {
            pokemons = pokeAPIConnection.getPokemons();

            // Poblemos el ComboBox con los nombres de los Pokémon
            Set<String> pokemonNames = pokemons.stream()
                    .map(Pokemon::getName) // Obtener los nombres de los Pokémon
                    .collect(Collectors.toSet());

            cmbComarca.getItems().clear();
            cmbComarca.getItems().addAll(pokemonNames);

            // Creamos el gráfico de barras
            barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Cantidad de Pokémon por Tipo");
            xAxis.setLabel("Tipo");
            yAxis.setLabel("Cantidad");

            updateBarChart(pokemonNames.iterator().next());  // Actualizamos el gráfico con el primer Pokémon disponible
            barChart.setPrefWidth(780);
            vBox0.getChildren().add(barChart);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickMenuItemClose(ActionEvent actionEvent) {
        stage.close();
    }

    private void updateBarChart(String selectedPokemonName) {
        barChart.getData().clear();

        // Contamos cuántos Pokémon de cada tipo existen
        Map<String, Long> categoryCount = pokemons.stream()
                .flatMap(pokemon -> pokemon.getTypes().stream())  // Aplana la lista de tipos de cada Pokémon
                .map(type -> type.getName()) // Accede al nombre del tipo (ya que 'type' es un objeto de la clase 'Type')
                .collect(Collectors.groupingBy(type -> type, Collectors.counting())); // Agrupa por nombre de tipo y cuenta las ocurrencias


        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(entry.getKey());
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            barChart.getData().add(series);
        }
    }

    // Método para mostrar los detalles del Pokémon
    private void displayPokemonDetails(String pokemonName) {
        try {
            // Obtener el Pokémon por su nombre desde la API
            Pokemon pokemon = pokeAPIConnection.getPokemon(pokemonName);
            if (pokemon != null) {
                // Actualizar la interfaz con los detalles del Pokémon
                lblPokemonName.setText("Pokémon: " + pokemon.getName());
                lblPokemonId.setText("ID: " + pokemon.getId());
                lblPokemonSpecies.setText("Especie: " + pokemon.getSpecies());
                lblPokemonHeight.setText("Altura: " + pokemon.getHeight() + " m");
                lblPokemonWeight.setText("Peso: " + pokemon.getWeight() + " kg");

                // Cargar la imagen del Pokémon, si está disponible
                String imageUrl = pokemon.getSprites().getFront_default();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image image = new Image(imageUrl);
                    ivPokemonImage.setImage(image);
                } else {
                    ivPokemonImage.setImage(null);  // Si no hay imagen, asegurarse de que no se muestre nada
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método que se llama cuando se selecciona un Pokémon desde el ComboBox
    @FXML
    public void onPokemonSelected(ActionEvent event) {
        String selectedPokemon = cmbComarca.getValue();
        if (selectedPokemon != null) {
            displayPokemonDetails(selectedPokemon);
        }
    }

    public void setPokeAPIConnection(PokeAPIConnection pokeAPIConnection) {
        this.pokeAPIConnection = pokeAPIConnection;
    }
}
