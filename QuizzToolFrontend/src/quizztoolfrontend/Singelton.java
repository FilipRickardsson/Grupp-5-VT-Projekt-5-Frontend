package quizztoolfrontend;

public class Singelton {

    private static Singelton instance = null;
    private int x;

    private Singelton() {
        // Exists only to defeat instantiation.
    }

    public static Singelton getInstance() {
        if (instance == null) {
            instance = new Singelton();
        }
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}
