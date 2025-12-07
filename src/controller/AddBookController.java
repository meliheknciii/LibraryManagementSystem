package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.DatabaseConnection;

public class AddBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField categoryField;
    @FXML private Label messageLabel;

    @FXML
    private void handleSave() {
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();

        if(title.isEmpty() || author.isEmpty() || category.isEmpty()){
            messageLabel.setText("Tüm alanları doldurun");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = "INSERT INTO books (title, author, category) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);

            ps.executeUpdate();

            messageLabel.setText("Kitap eklendi ✅");
            titleField.clear();
            authorField.clear();
            categoryField.clear();



        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Hata oluştu ❌");
        }
    }
}
