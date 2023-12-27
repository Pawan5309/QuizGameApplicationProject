import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class QuixApp extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton submitButton;
    public void setSubmitButton(JButton submitButton) {
        this.submitButton = submitButton;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private int timeRemaining;
    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    private String[] questions = {
            "What is the capital of France?",
            "What is the largest planet in our solar system?",
            "Which gas do plants absorb from the atmosphere?",
            " who is the Primr Minister of india?"
    };

    private String[] correctAnswers = {
            "Paris",
            "Jupiter",
            "Carbon dioxide",
            "Narendra Modi"
    };

    private String[][] answerOptions = {
            {"Paris", "London", "Berlin","Canada"},
            {"Mars", "Jupiter", "Saturn","Earth"},
            {"Oxygen", "Carbon dioxide", "Nitrogen","Hydrogen"},
            {"Rahul Gandhi","Narendra Modi","Yogi Aditya Nath","Amit Shah"}

    };

    public QuixApp() {
        setTitle("Quix App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize( 300,300);
        setLayout(new GridLayout(7, 0));

        questionLabel = new JLabel();
        add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            add(options[i]);
        }

        submitButton = new JButton("Submit");
        add(submitButton);

        timerLabel = new JLabel();
        add(timerLabel);

        currentQuestionIndex = 0;
        score = 0;
        timeRemaining = 15; // Set the initial timer value in seconds

        showNextQuestion();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                showNextQuestion();
            }
        });

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    checkAnswer();
                    showNextQuestion();
                } else {
                    timerLabel.setText("Time remaining: " + timeRemaining + " seconds");
                }
            }
        });
        timer.start();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(answerOptions[currentQuestionIndex][i]);
                options[i].setSelected(false);
            }
            currentQuestionIndex++;
            timeRemaining = 15; // Reset the timer for the next question
            timerLabel.setText("Time remaining: " + timeRemaining + " seconds");
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                if (options[i].getText().equals(correctAnswers[currentQuestionIndex - 1])) {
                    score++;
                }
                break;
            }
        }
    }

    private void showResult() {
        timer.stop();
        questionLabel.setText("Quiz Completed!");
        for (int i = 0; i < 4; i++) {
            options[i].setVisible(false);
        }
        submitButton.setVisible(false);
        timerLabel.setText("Final Score: " + score + " out of " + questions.length);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuixApp().setVisible(true);
            }
        });
    }
}