package model;

public class Book {

    private int id;
    private String title;
    private String author;
    private String category;
    private String status;
    private int quantity;

    public Book(int id,
                String title,
                String author,
                String category,
                String status,
                int quantity) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    // 🔥 ComboBox ve TableView için ZORUNLU
    @Override
    public String toString() {
        return title + " - " + author;
    }
}
