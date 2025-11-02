import gui.SimulationFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimulationFrame frame = new SimulationFrame();
                frame.setVisible(true);
            }
        });
    }
}
