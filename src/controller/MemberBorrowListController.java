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
                "SELECT b.title, br.borrow_date, br.status " +
                        "FROM borrow br " +
                        "JOIN books b ON br.book_id = b.id " +
                        "WHERE br.member_id = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, MemberSession.getMemberId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                borrowTable.getItems().add(new MemberBorrowItem(
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
}
