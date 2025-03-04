package mp3.dam.elpuig.pokedex.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import mp3.dam.elpuig.pokedex.model.Pokemon;
import mp3.dam.elpuig.pokedex.model.PokemonList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PokeAPIConnection {

    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    // Método para obtener una lista de Pokémon
    public List<Pokemon> getPokemons() throws IOException, InterruptedException {
        // Crear cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud HTTP para obtener datos de los Pokémon
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        // Enviar solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar si la respuesta es exitosa
        if (response.statusCode() == 200) {
            // Mapeo de la respuesta JSON a un objeto PokemonList usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            PokemonList pokemonList = objectMapper.readValue(response.body(), PokemonList.class);
            return pokemonList.getResults(); // Retornar la lista de Pokémon
        } else {
            System.out.println("Error en la conexión: " + response.statusCode());
            return null;
        }
    }

    // Método para obtener un solo Pokémon por nombre
    public Pokemon getPokemon(String name) throws IOException, InterruptedException {
        // Crear cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud HTTP para obtener datos del Pokémon
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + name.toLowerCase()))
                .build();

        // Enviar solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar si la respuesta es exitosa
        if (response.statusCode() == 200) {
            // Mapeo de la respuesta JSON a un objeto Pokémon usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Pokemon.class);
        } else {
            System.out.println("Error en la conexión: " + response.statusCode());
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            // Probar con un Pokémon
            PokeAPIConnection connection = new PokeAPIConnection();
            List<Pokemon> pokemons = connection.getPokemons();

            if (pokemons != null && !pokemons.isEmpty()) {
                for (Pokemon pokemon : pokemons) {
                    System.out.println("Pokémon: " + pokemon.getName());
                }
            }

            // También puedes obtener un solo Pokémon:
            Pokemon pokemon = connection.getPokemon("pikachu");

            if (pokemon != null) {
                System.out.println("Pokémon: " + pokemon.getName());
                System.out.println("ID: " + pokemon.getId());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
