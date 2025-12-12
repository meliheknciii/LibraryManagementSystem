package service;

import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();

    // 🔥 BorrowController'ın çağırdığı metot BU
    public List<Book> getAvailableBooks() {
        return bookDAO.getAvailableBooks();
    }
}
