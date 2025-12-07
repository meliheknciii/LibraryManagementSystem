package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.DatabaseConnection;
import model.MemberSession;

import java.sql.*;

public class MemberBorrowController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book,Integer> idCol;
    @FXML private TableColumn<Book,String> titleCol;
    @FXML private TableColumn<Book,String> authorCol;
    @FXML private TableColumn<Book,String> categoryCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        loadBooks();
    }

    private void loadBooks() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                bookTable.getItems().add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            // ❌ conn.close() KESİNLİKLE YOK
        }
    }

    @FXML
    private void borrowBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Uyarı","Kitap seçmeden ödünç alamazsın.");
            return;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO borrow (member_id, book_id, borrow_date, status) VALUES (?, ?, CURDATE(), 'BORROWED')";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, MemberSession.getMemberId());
            ps.setInt(2, selected.getId());

            ps.executeUpdate();

            showAlert("Başarılı", "Kitap ödünç alındı!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Hata", "Ödünç alma başarısız.");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            // ❌ connection.close() ASLA EKLENMEYECEK
        }
    }

    private void showAlert(String title,String msg){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) bookTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
