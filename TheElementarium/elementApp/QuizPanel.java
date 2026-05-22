package TheElementarium.elementApp;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class QuizPanel extends JPanel {
    
    public QuizPanel(ActionListener onBackToMenuClicked) {
        setBackground(ElementGUI.DARK_BLUE);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel placeholderLabel = new JLabel("Quiz Mode Coming Soon!");
        placeholderLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        placeholderLabel.setForeground(Color.WHITE);
        add(placeholderLabel, gbc);
        
        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.setPreferredSize(new Dimension(250, 45));
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnBack.setBackground(ElementGUI.DEFAULT_PASTEL);
        btnBack.setForeground(ElementGUI.DARK_BLUE);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder());
        
        btnBack.addActionListener(onBackToMenuClicked);
        
        gbc.gridy = 1;
        add(btnBack, gbc);
    }
}
