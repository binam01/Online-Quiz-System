package ui;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {
    public ResultFrame(int score, int total) {
        setTitle("Result");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Your Score: " + score + " / " + total, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label, BorderLayout.CENTER);

        setVisible(true);
    }
}
