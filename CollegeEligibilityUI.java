import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CollegeEligibilityUI extends JFrame {

    private JTextField tenthField, twelfthField, jeeField, cuetField;
    private JPanel mainPanel, loadingPanel, resultPanel;
    private CardLayout cardLayout;
    private JPanel resultCardsContainer;

    public CollegeEligibilityUI() {
        setTitle("EDU SCORE");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 1);
        int height = (int) (screenSize.height * 1);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(0x121212);
        Color cardColor = new Color(0x1F1B24);
        Color textColor = new Color(0xFFFFFF);
        Color accentColor = new Color(0xBB86FC);
        Color collegeBlockColor = new Color(0x3700B3);
        Font font = new Font("Segoe UI", Font.PLAIN, 18);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 20, 100));
        formPanel.setBackground(backgroundColor);

        JLabel heading = new JLabel("College Eligibility Finder", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 40));
        heading.setForeground(accentColor);

        JLabel tenthLabel = new JLabel("ENTER YOUR 10th PERCENTAGE :");
        JLabel twelfthLabel = new JLabel("ENTER YOUR 12th PERCENTAGE :");
        JLabel jeeLabel = new JLabel("ENTER YOUR JEE PERCENTILE:");
        JLabel cuetLabel = new JLabel("ENTER YOUR CUET SCORE:");

        JLabel[] labels = {tenthLabel, twelfthLabel, jeeLabel, cuetLabel};
        for (JLabel label : labels) {
            label.setForeground(textColor);
            label.setFont(font);
        }

        tenthField = new JTextField();
        twelfthField = new JTextField();
        jeeField = new JTextField();
        cuetField = new JTextField();

       Font inputFont = new Font("Poppins", Font.PLAIN, 40); // Use "Segoe UI" if Poppins not installed
JTextField[] fields = {tenthField, twelfthField, jeeField, cuetField};
for (JTextField field : fields) {
    field.setFont(inputFont);
    field.setBackground(cardColor);
    field.setForeground(textColor);
    field.setCaretColor(textColor);
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(accentColor, 15, true),
        BorderFactory.createEmptyBorder(16, 24, 16, 24)
        
    ));
}

        JButton checkButton = new JButton("Search");
        styleButton(checkButton, accentColor, font);
        checkButton.addActionListener(this::showLoadingAndCheckEligibility);

        formPanel.add(tenthLabel); formPanel.add(tenthField);
        formPanel.add(twelfthLabel); formPanel.add(twelfthField);
        formPanel.add(jeeLabel); formPanel.add(jeeField);
        formPanel.add(cuetLabel); formPanel.add(cuetField);
        formPanel.add(new JLabel()); formPanel.add(checkButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(backgroundColor);
        inputPanel.add(heading, BorderLayout.NORTH);
        inputPanel.add(formPanel, BorderLayout.CENTER);

        // Loading Panel
        loadingPanel = new JPanel(new GridBagLayout());
        loadingPanel.setBackground(backgroundColor);
        JLabel loadingLabel = new JLabel("Loading, please wait...");
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        loadingLabel.setForeground(accentColor);
        loadingPanel.add(loadingLabel);

        // Result Panel
        resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(backgroundColor);

        resultCardsContainer = new JPanel();
        resultCardsContainer.setLayout(new BoxLayout(resultCardsContainer, BoxLayout.Y_AXIS));
        resultCardsContainer.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(resultCardsContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(backgroundColor);

        JButton backButton = new JButton("←");
        styleButton(backButton, accentColor, font);
        backButton.setPreferredSize(new Dimension(60, 40));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "form"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(backgroundColor);
        backPanel.add(backButton);

        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(backPanel, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, "form");
        mainPanel.add(loadingPanel, "loading");
        mainPanel.add(resultPanel, "result");

        add(mainPanel);
        cardLayout.show(mainPanel, "form");
        setVisible(true);
    }

    private void styleButton(JButton button, Color accentColor, Font font) {
    button.setFont(font);
    button.setBackground(accentColor);
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setContentAreaFilled(true);
    button.setOpaque(true);

    // Rounded look with compound border
    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor.darker(), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));

    // Animation effects
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(accentColor.brighter());
            button.setFont(button.getFont().deriveFont(Font.BOLD, font.getSize() + 1f));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(accentColor);
            button.setFont(font);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            button.setBackground(accentColor.darker());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            button.setBackground(accentColor);
        }
    });
}


    private void showLoadingAndCheckEligibility(ActionEvent e) {
        cardLayout.show(mainPanel, "loading");
        Timer timer = new Timer(2000, evt -> checkEligibility());
        timer.setRepeats(false);
        timer.start();
    }

    private void checkEligibility() {
        Color accentColor = new Color(0xBB86FC);
        Color collegeBlockColor = new Color(0x3700B3);
        Font font = new Font("Segoe UI", Font.PLAIN, 18);

        resultCardsContainer.removeAll();

        try {
            double tenth = Double.parseDouble(tenthField.getText());
            double twelfth = Double.parseDouble(twelfthField.getText());
            double jee = Double.parseDouble(jeeField.getText());
            int cuet = Integer.parseInt(cuetField.getText());

            if (tenth < 60 || twelfth < 75) {
                JLabel notEligible = new JLabel("❌ Not eligible: Minimum 60% in 10th and 75% in 12th required.");
                notEligible.setForeground(accentColor);
                notEligible.setFont(font);
                resultCardsContainer.add(notEligible);
                cardLayout.show(mainPanel, "result");
                return;
            }

            List<CollegeModel> colleges = CollegeDAO.getEligibleColleges(jee, cuet);
            if (colleges.isEmpty() && cuet >= 500) {
                colleges = CollegeDAO.getFallbackCUETColleges(cuet);
            }

            if (colleges.isEmpty()) {
                JLabel noCollege = new JLabel("❌ Sorry, no eligible college found.");
                noCollege.setForeground(accentColor);
                noCollege.setFont(font);
                resultCardsContainer.add(noCollege);
            } else {
                for (CollegeModel college : colleges) {
                    JPanel collegeBlock = new JPanel(new BorderLayout());
                    collegeBlock.setBackground(collegeBlockColor);
                    collegeBlock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JLabel nameLabel = new JLabel(college.getName());
                    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    nameLabel.setForeground(Color.WHITE);

                    JLabel catLabel = new JLabel("Category: " + college.getCategory());
                    catLabel.setFont(font);
                    catLabel.setForeground(new Color(0xEDE7F6));

                    collegeBlock.add(nameLabel, BorderLayout.NORTH);
                    collegeBlock.add(catLabel, BorderLayout.SOUTH);

                    collegeBlock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                    resultCardsContainer.add(Box.createVerticalStrut(10));
                    resultCardsContainer.add(collegeBlock);
                }
            }
            resultCardsContainer.revalidate();
            resultCardsContainer.repaint();
            cardLayout.show(mainPanel, "result");

        } catch (NumberFormatException ex) {
            JLabel errorLabel = new JLabel("⚠️ Please enter valid numeric inputs.");
            errorLabel.setForeground(accentColor);
            errorLabel.setFont(font);
            resultCardsContainer.add(errorLabel);
            cardLayout.show(mainPanel, "result");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CollegeEligibilityUI::new);
    }
}