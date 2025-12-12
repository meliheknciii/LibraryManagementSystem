package service;

import dao.BookDAO;
import dao.BorrowDAO;
import java.time.LocalDate;

public class BorrowService {

    private final BorrowDAO borrowDAO = new BorrowDAO();
    private final BookDAO bookDAO = new BookDAO();

    public void borrowBook(int memberId, int bookId, int staffId, LocalDate dueDate) {
        if (!bookDAO.isAvailable(bookId)) {
            throw new RuntimeException("Kitap stokta yok");
        }
        borrowDAO.insertBorrow(memberId, bookId, staffId, dueDate);
        bookDAO.decreaseStock(bookId);
    }
}