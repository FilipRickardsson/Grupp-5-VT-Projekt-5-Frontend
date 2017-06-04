package quizztoolfrontend;

public class LoggedInCredentials {

    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoggedInCredentials.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoggedInCredentials.password = password;
    }

}
