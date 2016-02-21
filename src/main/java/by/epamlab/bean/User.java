package by.epamlab.bean;


import by.epamlab.resources.Constants;

public class User {
    private String login;
    private String email;
    private Constants.Role role;
    private long idUser;

    public User() {
    }


    public User(String login, String email, Constants.Role role, long idUser) {
        this.login = login;
        this.email = email;
        this.role = role;
        this.idUser = idUser;
    }

    public User(String login, String email, long idUser) {
        this.login = login;
        this.email = email;
        this.role = Constants.Role.USER;
        this.idUser = idUser;
    }


    public User(String login, String email, String role, long idUser) {
        this.login = login;
        this.email = email;
        this.idUser = idUser;
        setRole(role);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Constants.Role getRole() {
        return role;
    }

    public void setRole(Constants.Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = Constants.Role.valueOf(role);
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return role == user.role;

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
