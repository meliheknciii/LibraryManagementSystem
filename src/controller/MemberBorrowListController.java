package controller;

import dao.BorrowDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MemberBorrowItem;
import model.MemberSession;

import java.time.LocalDate;

public class MemberBorrowListController {

    @FXML private TableView<MemberBorrowItem> borrowTable;

    @FXML private TableColumn<MemberBorrowItem, String> titleCol;
    @FXML private TableColumn<MemberBorrowItem, LocalDate> borrowDateCol;
    @FXML private TableColumn<MemberBorrowItem, LocalDate> dueDateCol;
    @FXML private TableColumn<MemberBorrowItem, String> statusCol;

    private final BorrowDAO borrowDAO = new BorrowDAO();

    @FXML
    public void initialize() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadBorrowedBooks();
    }

    private void loadBorrowedBooks() {
        borrowTable.getItems().clear();
        borrowTable.getItems().addAll(
                borrowDAO.getActiveBorrowsByMember(
                        MemberSession.getMemberId()
                )
        );
    }

    @FXML
    private void returnBook() {
        MemberBorrowItem selected = borrowTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Bir kitap seçilmedi!");
            return;
        }

        try {
            borrowDAO.returnBook(
                    selected.getBorrowId(),
                    selected.getBookId()
            );

            System.out.println("Kitap başarıyla iade edildi!");
            loadBorrowedBooks();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
