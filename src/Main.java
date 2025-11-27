import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // 🔥 İLK AÇILACAK EKRAN: SEÇİM EKRANI
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectLoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Kütüphane Yönetim Sistemi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
