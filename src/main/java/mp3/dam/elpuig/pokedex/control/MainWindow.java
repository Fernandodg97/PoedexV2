package mp3.dam.elpuig.pokedex.control;

import mp3.dam.elpuig.pokedex.connection.PokeAPIConnection;
import mp3.dam.elpuig.pokedex.model.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MainWindow {

    private JFrame frame;
    private JList<String> pokemonList;
    private JTextArea resultArea;
    private JLabel imageLabel;
    private DefaultListModel<String> pokemonListModel;
    private List<Pokemon> allPokemons = new ArrayList<>();

    public MainWindow() {
        initialize();
    }

    // Método para inicializar la ventana principal
    private void initialize() {
        // Crear la ventana principal
        frame = new JFrame("Pokedex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel para contener la lista de Pokémon y la información
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Crear el modelo y la lista de Pokémon (por nombre)
        pokemonListModel = new DefaultListModel<>();
        pokemonList = new JList<>(pokemonListModel);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pokemonList.addListSelectionListener(e -> onPokemonSelected());

        // Agregar un JScrollPane para la lista
        JScrollPane listScrollPane = new JScrollPane(pokemonList);
        mainPanel.add(listScrollPane, BorderLayout.WEST);

        // Crear área de texto para mostrar los resultados
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Etiqueta para mostrar la imagen del Pokémon
        imageLabel = new JLabel();
        mainPanel.add(resultScrollPane, BorderLayout.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Hacer visible la ventana
        frame.setVisible(true);

        // Cargar la lista de todos los Pokémon
        loadPokemonList();
    }

    // Método para cargar la lista de Pokémon
    private void loadPokemonList() {
        try {
            // Obtener el número total de Pokémon disponibles
            int totalPokemons = PokeAPIConnection.getTotalPokemons(); // Asume que esta función existe y devuelve el total

            System.out.println("Iniciando la carga de " + totalPokemons + " Pokémon...");
            for (int i = 1; i <= totalPokemons; i++) {
                try {
                    System.out.println("Intentando obtener el Pokémon con ID: " + i);
                    Pokemon pokemon = PokeAPIConnection.getPokemonById(i); // Método para obtener Pokémon por ID

                    // Imprimir el Pokémon antes de agregarlo
                    System.out.println("Cargando Pokémon: " + pokemon.getName());
                    System.out.println(pokemon);

                    allPokemons.add(pokemon);
                    pokemonListModel.addElement(pokemon.getName());  // Agregar el nombre del Pokémon a la lista
                    System.out.println("Pokémon agregado correctamente: " + pokemon.getName());

                } catch (IOException e) {
                    System.out.println("Error al obtener el Pokémon con ID: " + i);  // Mejor manejo de error si algo falla
                    e.printStackTrace();  // Imprimir detalles del error
                }
            }
            System.out.println("Carga de Pokémon completada.");
        } catch (Exception e) {
            System.out.println("Error general al cargar los Pokémon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método que se ejecuta cuando un Pokémon es seleccionado en la lista
    private void onPokemonSelected() {
        String selectedPokemonName = pokemonList.getSelectedValue();
        if (selectedPokemonName != null) {
            // Buscar el Pokémon correspondiente en la lista
            Pokemon selectedPokemon = getPokemonByName(selectedPokemonName);

            if (selectedPokemon != null) {
                // Mostrar los detalles del Pokémon
                StringBuilder resultText = new StringBuilder();
                resultText.append("ID: ").append(selectedPokemon.getId()).append("\n");
                resultText.append("Nombre: ").append(selectedPokemon.getName()).append("\n");

                // Mostrar los tipos del Pokémon
                resultText.append("Tipos: ");
                for (String type : selectedPokemon.getTypes()) {
                    resultText.append(type).append(" ");
                }
                resultText.append("\n");

                // Mostrar las estadísticas del Pokémon
                resultText.append("HP: ").append(selectedPokemon.getHp()).append("\n");
                resultText.append("Ataque: ").append(selectedPokemon.getAttack()).append("\n");
                resultText.append("Defensa: ").append(selectedPokemon.getDefense()).append("\n");
                resultText.append("Ataque Especial: ").append(selectedPokemon.getSpecialAttack()).append("\n");
                resultText.append("Defensa Especial: ").append(selectedPokemon.getSpecialDefense()).append("\n");
                resultText.append("Velocidad: ").append(selectedPokemon.getSpeed()).append("\n");

                // Mostrar la imagen del Pokémon
                ImageIcon pokemonImage = new ImageIcon(selectedPokemon.getImageUrl());
                imageLabel.setIcon(pokemonImage);

                // Actualizar el área de texto con la información del Pokémon
                resultArea.setText(resultText.toString());
            }
        }
    }

    // Método para buscar un Pokémon por su nombre
    private Pokemon getPokemonByName(String name) {
        for (Pokemon pokemon : allPokemons) {
            if (pokemon.getName().equalsIgnoreCase(name)) {
                return pokemon;
            }
        }
        return null;
    }

    // Método principal para ejecutar la ventana
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
