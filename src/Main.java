import ui.DSAView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DSAView view = new DSAView();
            view.setVisible(true);
        });
    }
} 