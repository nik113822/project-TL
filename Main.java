import javax.swing.JFrame;

public class Main extends JFrame {
    public Main() {
        setTitle("Main Application Window");
        setSize(300, 200); // Set the desired size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main window = new Main();
        window.setVisible(true);
    }
}