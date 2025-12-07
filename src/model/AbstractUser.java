package model;

public abstract class AbstractUser {
    protected int id;
    protected String name;
    protected String email;

    public abstract void showDashboard();
}
