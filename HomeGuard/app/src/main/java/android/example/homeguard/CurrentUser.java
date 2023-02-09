package android.example.homeguard;

public class CurrentUser {
    private static String userName;

    public CurrentUser() {
    }

    public CurrentUser(String Email) {
        String[] parts = Email.split(".com");
        this.userName = parts[0];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}