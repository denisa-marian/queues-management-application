package gui;

import businessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationFrame extends JFrame {
    private final JTextField timeLimitField;
    private final JTextField minArrivalTimeField;
    private final JTextField maxArrivalTimeField;
    private final JTextField minServiceTimeField;
    private final JTextField maxServiceTimeField;
    private final JTextField numberOfServersField;
    private final JTextField numberOfTasksField;
    private final JButton startButton;
    private final JTextArea logArea;

    public SimulationFrame() {
        setTitle("Queue Simulation");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10));
        inputPanel.setBackground(new Color(45, 45, 45));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(177, 212, 250, 179);

        JLabel[] labels = new JLabel[] {
                new JLabel("Number of Tasks:"),
                new JLabel("Number of Queues:"),
                new JLabel("Simulation Time Limit:"),
                new JLabel("Min Arrival Time:"),
                new JLabel("Max Arrival Time:"),
                new JLabel("Min Service Time:"),
                new JLabel("Max Service Time:"),
        };

        JTextField[] fields = new JTextField[] {
                numberOfTasksField = new JTextField(),
                numberOfServersField = new JTextField(),
                timeLimitField = new JTextField(),
                minArrivalTimeField = new JTextField(),
                maxArrivalTimeField = new JTextField(),
                minServiceTimeField = new JTextField(),
                maxServiceTimeField = new JTextField()
        };


        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(labelColor);
            labels[i].setFont(labelFont);
            inputPanel.add(labels[i]);
            fields[i].setBackground(new Color(60, 60, 60, 169));
            fields[i].setForeground(new Color(200, 220, 255));
            fields[i].setCaretColor(new Color(120, 208, 255));
            inputPanel.add(fields[i]);
        }

        startButton = new JButton("Start Simulation");
        startButton.setBackground(new Color(90, 182, 211));
        startButton.setForeground(new Color(230, 240, 255));
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(new JLabel()); // empty label
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(35, 35, 35));
        logArea.setForeground(new Color(180, 210, 255));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(880, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
        add(scrollPane, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
                appendLog("Simulation started...");
                int timeLimit = getTimeLimit();
                int numberOfServers = getNumberOfServers();
                int numberOfTasks = getNumberOfTasks();
                int minArrival = getMinArrivalTime();
                int maxArrival = getMaxArrivalTime();
                int minService = getMinServiceTime();
                int maxService = getMaxServiceTime();

                SimulationManager simulationManager = new SimulationManager(
                        timeLimit, numberOfServers, numberOfTasks, minArrival, maxArrival,
                        minService, maxService, SimulationFrame.this
                );
                new Thread(simulationManager).start();
            }
        });
    }

    public int getTimeLimit() { return Integer.parseInt(timeLimitField.getText()); }
    public int getMinArrivalTime() { return Integer.parseInt(minArrivalTimeField.getText()); }
    public int getMaxArrivalTime() { return Integer.parseInt(maxArrivalTimeField.getText()); }
    public int getMinServiceTime() { return Integer.parseInt(minServiceTimeField.getText()); }
    public int getMaxServiceTime() { return Integer.parseInt(maxServiceTimeField.getText()); }
    public int getNumberOfServers() { return Integer.parseInt(numberOfServersField.getText()); }
    public int getNumberOfTasks() { return Integer.parseInt(numberOfTasksField.getText()); }
    public void appendLog(String text) { logArea.append(text + "\n"); }

    public void saveSimulationToFile(String filename, String averageWaitingTime) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("Simulation Log:\n");
            writer.write(logArea.getText());
           // writer.write("\nAverage Waiting Time: " + averageWaitingTime + "\n");
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }
}