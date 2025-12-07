package model;

public class MemberBorrowItem {

    private String title;
    private String borrowDate;
    private String status;

    public MemberBorrowItem(String title, String borrowDate, String status){
        this.title = title;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getBorrowDate() { return borrowDate; }
    public String getStatus() { return status; }
}
