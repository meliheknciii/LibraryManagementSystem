package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DatabaseConnection;
import model.MemberSession;
import model.MemberBorrowItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberBorrowListController {

    @FXML private TableView<MemberBorrowItem> borrowTable;
    @FXML private TableColumn<MemberBorrowItem, String> titleCol;
    @FXML private TableColumn<MemberBorrowItem, String> dateCol;
    @FXML private TableColumn<MemberBorrowItem, String> statusCol;

    @FXML
    public void initialize(){
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadBorrowedBooks();
    }

    private void loadBorrowedBooks(){
        borrowTable.getItems().clear();

        String sql =
                "SELECT br.id AS borrow_id, b.id AS book_id, b.title, br.borrow_date, br.status " +
                        "FROM borrow br " +
                        "JOIN books b ON br.book_id = b.id " +
                        "WHERE br.member_id = ? AND br.status = 'BORROWED'";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, MemberSession.getMemberId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                borrowTable.getItems().add(new MemberBorrowItem(
                        rs.getInt("borrow_id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("status")
                ));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void returnBook() {
        MemberBorrowItem selected = borrowTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Bir kitap seçilmedi!");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // 1) borrow tablosunu güncelle
            String sql1 = "UPDATE borrow SET status = 'RETURNED', return_date = NOW() WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, selected.getBorrowId());
            ps1.executeUpdate();

            // 2) kitap adedini artır
            String sql2 = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, selected.getBookId());
            ps2.executeUpdate();

            System.out.println("Kitap başarıyla iade edildi!");

            loadBorrowedBooks();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
