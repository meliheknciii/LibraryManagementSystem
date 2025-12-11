package model;

public class Member {

    private int id;
    private String username;
    private String email;
    private String tc;

    public Member(int id, String username, String email, String tc) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tc = tc;
    }

    // GETTER'lar
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTc() {
        return tc;
    }
}
