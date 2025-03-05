package mp3.dam.elpuig.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mp3.dam.elpuig.pokedex.control.MainWindow;

public class PokedexApp {
    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX desde el método launch
        Application.launch(JavaFXApp.class, args);
    }

    public static class JavaFXApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // Aquí puedes inicializar y configurar el MainWindow sin necesidad de extender Application
            MainWindow mainWindow = new MainWindow();
            mainWindow.start(primaryStage);
        }
    }
}

// package mp3.dam.elpuig.pokedex;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.layout.Parent;  // Importación necesaria
// import javafx.stage.Stage;

// public class PokedexApp {
//     public static void main(String[] args) {
//         // Lanzar la aplicación JavaFX desde el método launch
//         // Application.launch(JavaFXApp.class, args);
//     }

//     public static class JavaFXApp extends Application {
//         @Override
//         public void start(Stage primaryStage) {
//             try {

//                 // FXMLLoader loader = new FXMLLoader(getClass().getResource("/mp3/dam/elpuig/pokedex/fxml/main_window.fxml"));
//                 // Parent root = loader.load();


//                 // Scene scene = new Scene(root);
//                 // primaryStage.setTitle("Pokedex");
//                 // primaryStage.setScene(scene);
//                 // primaryStage.show();
//             } catch (Exception e) {
//                 // e.printStackTrace();
//             }
//         }
//     }
// }
