package ui;

import db.DBConnection;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class QuizFrame extends JFrame {
    private ArrayList<Question> questions = new ArrayList<>();
    private int current = 0, score = 0;

    private JLabel questionLabel;
    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup group = new ButtonGroup();
    private JButton nextBtn;

    public QuizFrame() {
        setTitle("Quiz");
        setSize(500, 300);
        setLayout(new GridLayout(6, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel();
        add(questionLabel);

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            add(options[i]);
        }

        nextBtn = new JButton("Next");
        add(nextBtn);

        nextBtn.addActionListener(e -> checkAnswer());

        loadQuestions();
        showQuestion();
        setVisible(true);
    }

    private void loadQuestions() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                String q = rs.getString("question");
                String[] opts = {
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4")
                };
                int ans = rs.getInt("answer");
                questions.add(new Question(q, opts, ans));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showQuestion() {
        if (current < questions.size()) {
            group.clearSelection();
            Question q = questions.get(current);
            questionLabel.setText("Q" + (current + 1) + ": " + q.getQuestion());
            String[] opts = q.getOptions();
            for (int i = 0; i < 4; i++) {
                options[i].setText(opts[i]);
            }
        } else {
            dispose();
            new ResultFrame(score, questions.size());
        }
    }

    private void checkAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i + 1;
            }
        }
        if (selected == questions.get(current).getAnswer()) {
            score++;
        }
        current++;
        showQuestion();
    }
}
