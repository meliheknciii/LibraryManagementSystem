import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {

        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            statusLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {
            Connection conn = DatabaseConnection.connect();

            String sql = "SELECT * FROM users WHERE (username = ? OR email = ? OR tc = ?) AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, usernameInput);
            statement.setString(2, usernameInput);
            statement.setString(3, usernameInput);
            statement.setString(4, passwordInput);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                statusLabel.setText("Giriş başarılı!");
                statusLabel.setStyle("-fx-text-fill: green;");

                int userId = result.getInt("id");
                String username = result.getString("username");
                String email = result.getString("email");
                String tc = result.getString("tc");

                // 🔥 USER SESSION KAYIT
                UserSession.setUser(userId, username, email, tc);

                // 🔥 Dashboard Aç
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
                Parent root = loader.load();

                DashboardController controller = loader.getController();
                controller.setUserData(userId, username, email, tc);

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                statusLabel.setText("Bilgiler yanlış!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Bağlantı hatası!");
        }
    }
}
