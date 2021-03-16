package ntnu.idatt1002;

public class User {
    private String username;
    private String password;
    private byte[] salt;

    public User(){}

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, byte[] salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
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

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
