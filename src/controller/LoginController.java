package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.MemberSession;
import model.StaffSession;
import model.UserSession;

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
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // 🔴 ÖNCE PERSONEL KONTROLÜ
            String staffSQL = "SELECT * FROM staff WHERE (username=? OR email=?) AND password=?";
            PreparedStatement staffStmt = conn.prepareStatement(staffSQL);

            staffStmt.setString(1, usernameInput);
            staffStmt.setString(2, usernameInput);
            staffStmt.setString(3, passwordInput);

            ResultSet staffResult = staffStmt.executeQuery();

            if (staffResult.next()) {

                int staffId = staffResult.getInt("staff_id");
                String fullName = staffResult.getString("full_name");
                String role = staffResult.getString("role");

                // ✅ PERSONEL OTURUM AÇ
                StaffSession.setStaff(staffId, fullName, role);


                statusLabel.setText("Personel girişi başarılı!");
                statusLabel.setStyle("-fx-text-fill: green;");

                // ✅ PERSONEL DASHBOARD
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                return; // BURASI ÇOK ÖNEMLİ
            }

            // 🔵 PERSONEL YOKSA → ÜYE KONTROLÜ
            String userSQL = "SELECT * FROM users WHERE (username=? OR email=? OR tc=?) AND password=?";
            PreparedStatement userStmt = conn.prepareStatement(userSQL);

            userStmt.setString(1, usernameInput);
            userStmt.setString(2, usernameInput);
            userStmt.setString(3, usernameInput);
            userStmt.setString(4, passwordInput);

            ResultSet result = userStmt.executeQuery();

            if (result.next()) {

                int userId = result.getInt("id");
                String username = result.getString("username");
                String email = result.getString("email");
                String tc = result.getString("tc");

                UserSession.setUser(userId, username, email, tc);

                statusLabel.setText("Üye girişi başarılı!");
                MemberSession.setMemberId(userId);
                statusLabel.setStyle("-fx-text-fill: green;");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
                Parent root = loader.load();

                DashboardController controller = loader.getController();
                controller.setUserData(userId, username, email, tc);

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                statusLabel.setText("Giriş bilgileri yanlış!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }



        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Sunucu hatası!");
        }


    }
}
