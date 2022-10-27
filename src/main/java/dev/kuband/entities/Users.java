package dev.kuband.entities;

import java.util.Objects;

public class Users {
    //Users parameters
    private int user_id;
    private String username;
    private String password;

    private boolean isAdmin;


    public Users() {
    }

    public Users(int user_id, String username, String password, boolean isAdmin) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return user_id == users.user_id && isAdmin == users.isAdmin && Objects.equals(username, users.username) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, password, isAdmin);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
