package mp3.dam.elpuig.pokedex.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import mp3.dam.elpuig.pokedex.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class PokeAPIConnection {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public static int getTotalPokemons() throws IOException {
        String url = BASE_URL + "pokemon?limit=1";  // Solo necesitas un Pokémon para obtener el total
        String response = sendRequest(url);

        // Deserializar la respuesta en un objeto PokemonList
        ObjectMapper objectMapper = new ObjectMapper();
        PokemonList pokemonList = objectMapper.readValue(response, PokemonList.class);

        // Retorna el total de Pokémon disponibles
        return pokemonList.getCount();
    }


    public static Pokemon getPokemon(String pokemonName) throws IOException {
        String url = BASE_URL + "pokemon/" + pokemonName;
        String response = sendRequest(url);

        // Deserializar el JSON en un objeto Pokemon
        ObjectMapper objectMapper = new ObjectMapper();

        // Aquí deserializamos la respuesta en un Map o una clase que contenga todos los datos necesarios.
        PokemonData data = objectMapper.readValue(response, PokemonData.class);

        // Creación del objeto Pokemon y asignación de valores
        Pokemon pokemon = new Pokemon();
        pokemon.setId(data.getId());
        pokemon.setName(data.getName());

        // Asignamos los tipos de Pokémon
        for (Type type : data.getTypes()) {
            pokemon.addType(type.getType().getName());
        }

        // Asignamos las estadísticas del Pokémon
        Stats stats = data.getStats().get(0);  // Ejemplo con la primera estadística, puedes cambiar esto según tus necesidades
        pokemon.setHp(stats.getBaseStat());
        pokemon.setAttack(stats.getBaseStat());
        pokemon.setDefense(stats.getBaseStat());
        pokemon.setSpecialAttack(stats.getBaseStat());
        pokemon.setSpecialDefense(stats.getBaseStat());
        pokemon.setSpeed(stats.getBaseStat());

        // Asignar la imagen
        pokemon.setImageUrl(data.getSprites().getFrontDefault());

        return pokemon;
    }

    private static String sendRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Verificar el código de estado de la respuesta
        int status = connection.getResponseCode();
        if (status != 200) {
            throw new IOException("Error al obtener la respuesta de la API. Código de estado: " + status);
        }

        // Usar Scanner para leer la respuesta
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            return response.toString();
        } finally {
            // Asegurarse de cerrar la conexión
            connection.disconnect();
        }
    }

    public static Pokemon getPokemonById(int id) throws IOException {
        String url = BASE_URL + "pokemon/" + id;  // Construir la URL para obtener el Pokémon por ID
        String response = sendRequest(url);  // Hacer la solicitud GET a la API

        // Deserializar el JSON en un objeto Pokemon
        ObjectMapper objectMapper = new ObjectMapper();

        // Aquí deserializamos la respuesta en un Map o una clase que contenga todos los datos necesarios.
        PokemonData data = objectMapper.readValue(response, PokemonData.class);

        //System.out.println("Deserializar el JSON en un objeto Pokemon: " + data);
        //System.out.println("############################FIN #######################");

        // Crear el objeto Pokémon y asignar los valores
        Pokemon pokemon = new Pokemon();
        pokemon.setId(data.getId());
        pokemon.setName(data.getName());

        // Asignamos los tipos de Pokémon
        for (Type type : data.getTypes()) {
            pokemon.addType(type.getType().getName());
        }

        // Asignamos las estadísticas del Pokémon
        Stats stats = data.getStats().get(0);  // Ejemplo con la primera estadística, puedes cambiar esto según tus necesidades
        pokemon.setHp(stats.getBaseStat());
        pokemon.setAttack(stats.getBaseStat());
        pokemon.setDefense(stats.getBaseStat());
        pokemon.setSpecialAttack(stats.getBaseStat());
        pokemon.setSpecialDefense(stats.getBaseStat());
        pokemon.setSpeed(stats.getBaseStat());

        // Asignar la imagen
        pokemon.setImageUrl(data.getSprites().getFrontDefault());

        // Verificar y asignar la imagen con manejo de excepciones
        try {
            Sprites sprites = data.getSprites();
            //System.out.println("Sprites: " + sprites);
            if (sprites != null) {
                if (sprites.getFrontDefault() != null && !sprites.getFrontDefault().isEmpty()) {
                    pokemon.setImageUrl(sprites.getFrontDefault());
                } else if (sprites.getFrontShiny() != null && !sprites.getFrontShiny().isEmpty()) {
                    pokemon.setImageUrl(sprites.getFrontShiny());
                } else {
                    System.out.println("No se ha encontrado una imagen predeterminada ni shiny para el Pokémon: " + pokemon.getName());
                }
            } else {
                System.out.println("Los datos de sprites son nulos para el Pokémon: " + pokemon.getName());
            }
        } catch (Exception e) {
            System.err.println("Error al intentar asignar la imagen para el Pokémon: " + pokemon.getName());
            e.printStackTrace();
        }

        return pokemon;
    }
}
