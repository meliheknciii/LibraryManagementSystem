package service;

import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();

    public List<Book> getAvailableBooks() {
        return bookDAO.getAvailableBooks();
    }
}
