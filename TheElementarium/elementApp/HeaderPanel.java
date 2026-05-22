package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class HeaderPanel extends JPanel {
    private final ElementGUI frame;
    private JTextField searchField;
    private JButton backBtn;

    public HeaderPanel(ElementGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(ElementGUI.DARK_BLUE);
        setPreferredSize(new Dimension(0, 70));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ElementGUI.HEADER_LINE));
        initComponents();
    }

    private void initComponents() {
        try {
            URL titleURL = getClass().getResource("/TheElementarium/elements/elementGraphics/title.png");
            if (titleURL != null) {
                ImageIcon titleIcon = new ImageIcon(titleURL);
                Image scaledImg = titleIcon.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH);
                JLabel titleLabel = new JLabel(new ImageIcon(scaledImg));
                titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Padding from left edge
                add(titleLabel, BorderLayout.WEST);
            }
        } catch (Exception e) {
            System.err.println("Could not load title image: " + e.getMessage());
        }

        searchField = new JTextField("Search Element", 24);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search Element")) {
                    searchField.setText("");
                }
            }
        });
        
        JButton searchBtn = new JButton("Find");
        searchBtn.setFocusPainted(false);
        searchBtn.setBackground(new Color(173, 216, 230));
        searchBtn.setForeground(ElementGUI.DARK_BLUE);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 13));
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            Element found = frame.logic.findElement(query);
            frame.getTablePanel().filterGrid(found);
            backBtn.setVisible(true);
        });

        backBtn = new JButton("Back");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(255, 204, 153));
        backBtn.setForeground(ElementGUI.DARK_BLUE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setVisible(false); 
        backBtn.addActionListener(e -> {
            searchField.setText("Search Element");
            backBtn.setVisible(false);
            frame.getTablePanel().resetGrid();
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 18));
        searchPanel.setOpaque(false);
        searchPanel.add(backBtn);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        add(searchPanel, BorderLayout.EAST);
    }
}