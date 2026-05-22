package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PeriodicTablePanel extends JLayeredPane {
    private final ElementGUI frame;
    private final DetailPanel detailPanel;
    private final LegendPanel legendPanel;
    private final JButton menuButton;
    private boolean isPanelLocked = false;
    private String currentActiveSymbol = "";
    private final int BLOCK_WIDTH = 55;
    private final int BLOCK_HEIGHT = 70;

    private final String[][] elementData = {
        {"H", "1", "1"}, {"He", "18", "1"},
        {"Li", "1", "2"}, {"Be", "2", "2"}, {"B", "13", "2"}, {"C", "14", "2"}, {"N", "15", "2"}, {"O", "16", "2"}, {"F", "17", "2"}, {"Ne", "18", "2"},
        {"Na", "1", "3"}, {"Mg", "2", "3"}, {"Al", "13", "3"}, {"Si", "14", "3"}, {"P", "15", "3"}, {"S", "16", "3"}, {"Cl", "17", "3"}, {"Ar", "18", "3"},
        {"K", "1", "4"}, {"Ca", "2", "4"}, {"Sc", "3", "4"}, {"Ti", "4", "4"}, {"V", "5", "4"}, {"Cr", "6", "4"}, {"Mn", "7", "4"}, {"Fe", "8", "4"}, {"Co", "9", "4"}, {"Ni", "10", "4"}, {"Cu", "11", "4"}, {"Zn", "12", "4"}, {"Ga", "13", "4"}, {"Ge", "14", "4"}, {"As", "15", "4"}, {"Se", "16", "4"}, {"Br", "17", "4"}, {"Kr", "18", "4"},
        {"Rb", "1", "5"}, {"Sr", "2", "5"}, {"Y", "3", "5"}, {"Zr", "4", "5"}, {"Nb", "5", "5"}, {"Mo", "6", "5"}, {"Tc", "7", "5"}, {"Ru", "8", "5"}, {"Rh", "9", "5"}, {"Pd", "10", "5"}, {"Ag", "11", "5"}, {"Cd", "12", "5"}, {"In", "13", "5"}, {"Sn", "14", "5"}, {"Sb", "15", "5"}, {"Te", "16", "5"}, {"I", "17", "5"}, {"Xe", "18", "5"},
        {"Cs", "1", "6"}, {"Ba", "2", "6"}, {"Hf", "4", "6"}, {"Ta", "5", "6"}, {"W", "6", "6"}, {"Re", "7", "6"}, {"Os", "8", "6"}, {"Ir", "9", "6"}, {"Pt", "10", "6"}, {"Au", "11", "6"}, {"Hg", "12", "6"}, {"Tl", "13", "6"}, {"Pb", "14", "6"}, {"Bi", "15", "6"}, {"Po", "16", "6"}, {"At", "17", "6"}, {"Rn", "18", "6"},
        {"Fr", "1", "7"}, {"Ra", "2", "7"}, {"Rf", "4", "7"}, {"Db", "5", "7"}, {"Sg", "6", "7"}, {"Bh", "7", "7"}, {"Hs", "8", "7"}, {"Mt", "9", "7"}, {"Ds", "10", "7"}, {"Rg", "11", "7"}, {"Cn", "12", "7"}, {"Nh", "13", "7"}, {"Fl", "14", "7"}, {"Mc", "15", "7"}, {"Lv", "16", "7"}, {"Ts", "17", "7"}, {"Og", "18", "7"},
        {"La", "4", "8"}, {"Ce", "5", "8"}, {"Pr", "6", "8"}, {"Nd", "7", "8"}, {"Pm", "8", "8"}, {"Sm", "9", "8"}, {"Eu", "10", "8"}, {"Gd", "11", "8"}, {"Tb", "12", "8"}, {"Dy", "13", "8"}, {"Ho", "14", "8"}, {"Er", "15", "8"}, {"Tm", "16", "8"}, {"Yb", "17", "8"}, {"Lu", "18", "8"},
        {"Ac", "4", "9"}, {"Th", "5", "9"}, {"Pa", "6", "9"}, {"U", "7", "9"}, {"Np", "8", "9"}, {"Pu", "9", "9"}, {"Am", "10", "9"}, {"Cm", "11", "9"}, {"Bk", "12", "9"}, {"Cf", "13", "9"}, {"Es", "14", "9"}, {"Fm", "15", "9"}, {"Md", "16", "9"}, {"No", "17", "9"}, {"Lr", "18", "9"}
    };

    public PeriodicTablePanel(ElementGUI frame, ActionListener onBackToMenu) {
        this.frame = frame;
        setBackground(ElementGUI.DARK_BLUE);
        setOpaque(true);
        detailPanel = new DetailPanel();
        legendPanel = new LegendPanel();
        menuButton = new JButton("RETURN TO MAIN MENU") {
            private boolean isHovered = false;
            private boolean isPressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e) { isHovered = false; isPressed = false; repaint(); }
                    @Override public void mousePressed(MouseEvent e) { if (SwingUtilities.isLeftMouseButton(e)) { isPressed = true; repaint(); } }
                    @Override public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color baseColor = isPressed ? ElementGUI.DEFAULT_PASTEL.darker() : ElementGUI.DEFAULT_PASTEL;
                g2.setColor(baseColor);
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 16, 16);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isHovered || isPressed) {
                    Color glowColor = isPressed ? Color.WHITE : ElementGUI.HEADER_LINE;
                    g2.setStroke(new BasicStroke(6.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 40));
                    g2.drawRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 16, 16);
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 120));
                    g2.drawRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 16, 16);
                } else {
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.setColor(ElementGUI.DEFAULT_PASTEL.darker());
                    g2.drawRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 16, 16);
                }
                g2.dispose();
            }
        };

        menuButton.setFont(new Font("Arial", Font.BOLD, 15));
        menuButton.setForeground(ElementGUI.DARK_BLUE);
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setOpaque(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.addActionListener(onBackToMenu);
        add(menuButton, JLayeredPane.DEFAULT_LAYER);

        for (String[] data : elementData) {
            String symbol = data[0];
            JButton btn = createElementButton(symbol);
            btn.putClientProperty("col", Integer.valueOf(data[1]));
            btn.putClientProperty("row", Integer.valueOf(data[2]));
            frame.getElementButtons().add(btn);
            add(btn, JLayeredPane.DEFAULT_LAYER);
        }
        add(detailPanel, JLayeredPane.PALETTE_LAYER);
        add(legendPanel, JLayeredPane.DEFAULT_LAYER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                recalculateResponsiveGrid();
            }
        });
    }

    private JButton createElementButton(String symbol) {
        JButton btn = new JButton(symbol) {
            private boolean isHovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean isSelected = isPanelLocked && currentActiveSymbol.equals(getText());

                if (isHovered || isSelected) {
                    Color glowColor = isSelected ? Color.WHITE : ElementGUI.HEADER_LINE;
                    
                    g2.setStroke(new BasicStroke(4.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 60));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 200));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
                } else {
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.setColor(getBackground().darker());
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                }
                g2.dispose();
            }
        };

        btn.setSize(BLOCK_WIDTH, BLOCK_HEIGHT);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        Element element = frame.logic.findElement(symbol);
        btn.setBackground(element != null ? ElementGUI.getGroupColor(element.getGroup()) : ElementGUI.DEFAULT_PASTEL);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(ElementGUI.DARK_BLUE);
        btn.setMargin(new Insets(0, 0, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isPanelLocked || !currentActiveSymbol.equals(symbol)) {
                    int centerX = btn.getX() + btn.getWidth() / 2;
                    int centerY = btn.getY() + btn.getHeight() / 2;
                    int newWidth = BLOCK_WIDTH + 10;
                    int newHeight = BLOCK_HEIGHT + 10;
                    btn.setBounds(centerX - newWidth / 2, centerY - newHeight / 2, newWidth, newHeight);
                    btn.setFont(new Font("Arial", Font.BOLD, 20));
                    setComponentZOrder(btn, 1);
                }
                if (!isPanelLocked && element != null) {
                    currentActiveSymbol = symbol;
                    positionDetailPanel(btn.getX(), btn.getY(), 260, 380);
                    detailPanel.populateData(element);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isPanelLocked || !currentActiveSymbol.equals(symbol)) {
                    int centerX = btn.getX() + btn.getWidth() / 2;
                    int centerY = btn.getY() + btn.getHeight() / 2;
                    btn.setBounds(centerX - BLOCK_WIDTH / 2, centerY - BLOCK_HEIGHT / 2, BLOCK_WIDTH, BLOCK_HEIGHT);
                    btn.setFont(new Font("Arial", Font.BOLD, 16));
                }
                if (!isPanelLocked) {
                    detailPanel.setVisible(false);
                    repaint();
                }
            }
        });

        btn.addActionListener(e -> {
            if (element != null) {
                if (isPanelLocked && currentActiveSymbol.equals(symbol)) {
                    isPanelLocked = false;
                    detailPanel.setVisible(false);
                    int centerX = btn.getX() + btn.getWidth() / 2;
                    int centerY = btn.getY() + btn.getHeight() / 2;
                    btn.setBounds(centerX - BLOCK_WIDTH / 2, centerY - BLOCK_HEIGHT / 2, BLOCK_WIDTH, BLOCK_HEIGHT);
                    btn.setFont(new Font("Arial", Font.BOLD, 16));
                } else {
                    for (JButton otherBtn : frame.getElementButtons()) {
                        int cx = otherBtn.getX() + otherBtn.getWidth() / 2;
                        int cy = otherBtn.getY() + otherBtn.getHeight() / 2;
                        otherBtn.setBounds(cx - BLOCK_WIDTH / 2, cy - BLOCK_HEIGHT / 2, BLOCK_WIDTH, BLOCK_HEIGHT);
                        otherBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    }
                    isPanelLocked = true;
                    currentActiveSymbol = symbol;
                    int centerX = btn.getX() + btn.getWidth() / 2;
                    int centerY = btn.getY() + btn.getHeight() / 2;
                    int newWidth = BLOCK_WIDTH + 10;
                    int newHeight = BLOCK_HEIGHT + 10;
                    btn.setBounds(centerX - newWidth / 2, centerY - newHeight / 2, newWidth, newHeight);
                    btn.setFont(new Font("Arial", Font.BOLD, 22));
                    setComponentZOrder(btn, 1);
                    positionDetailPanel(btn.getX(), btn.getY(), 300, 400);
                    detailPanel.populateData(element);
                }
                repaint();
            }
        });
        return btn;
    }

    private void recalculateResponsiveGrid() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        if (panelWidth <= 0 || panelHeight <= 0) return;
        int leftMargin = 40;
        int topMargin = 20;
        double totalAvailableWidth = panelWidth - (leftMargin * 2) - BLOCK_WIDTH;
        double hGap = Math.max(4, totalAvailableWidth / 17.0 - BLOCK_WIDTH);
        double totalAvailableHeight = panelHeight - (topMargin * 2) - BLOCK_HEIGHT;
        double vGap = Math.max(4, Math.min(10, totalAvailableHeight / 8.8 - BLOCK_HEIGHT));
        int row7Y = 0;

        for (JButton btn : frame.getElementButtons()) {
            int col = (int) btn.getClientProperty("col");
            int row = (int) btn.getClientProperty("row");
            int x = (int) (leftMargin + (col - 1) * (BLOCK_WIDTH + hGap));
            int y = (int) (topMargin + (row - 1) * (BLOCK_HEIGHT + vGap));
            if (row >= 8) y += 20;
            btn.setBounds(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
            if (row == 7) row7Y = y;
        }

        if (menuButton != null && row7Y > 0) {
            int buttonY = row7Y + BLOCK_HEIGHT + 15;
            int buttonWidth = (int)((3 * BLOCK_WIDTH) + (2 * hGap)); 
            menuButton.setBounds(leftMargin - 4, buttonY, buttonWidth + 8, 70);
        }

        if (legendPanel != null) {
            int legX = (int) (leftMargin + (4 - 1) * (BLOCK_WIDTH + hGap));
            int legY = (int) (topMargin + (0.15 * (BLOCK_HEIGHT + vGap))); 
            int legW = (int) ((11 - 3 + 1) * (BLOCK_WIDTH + hGap) - hGap) - 5;
            int legH = (int) (2.5 * (BLOCK_HEIGHT + vGap)); 
            legendPanel.setBounds(legX, legY, legW, legH);
            legendPanel.revalidate();
        }
        
        if (detailPanel != null && detailPanel.isVisible()) {
            for (JButton btn : frame.getElementButtons()) {
                if (btn.getText().equals(currentActiveSymbol)) {
                    positionDetailPanel(btn.getX(), btn.getY(), detailPanel.getWidth(), detailPanel.getHeight());
                    break;
                }
            }
        }
    }

    private void positionDetailPanel(int elementX, int elementY, int panelWidth, int panelHeight) {
        int panelX;
        int spacing = 16;
        int activeBtnWidth = BLOCK_WIDTH;

        for (JButton btn : frame.getElementButtons()) {
            if (btn.getText().equals(currentActiveSymbol)) {
                activeBtnWidth = btn.getWidth();
                break;
            }
        }

        if (elementX > (getWidth() / 2)) {
            panelX = elementX - panelWidth - spacing;
        } else {
            panelX = elementX + activeBtnWidth + spacing;
        }

        int panelY = elementY;
        if (panelY + panelHeight > getHeight() && getHeight() > 0) {
            panelY = getHeight() - panelHeight - 10;
        }
        if (panelY < 10) panelY = 10;

        detailPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
        detailPanel.updateLayout(panelWidth);
        
        setComponentZOrder(detailPanel, 0);
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    public void filterGrid(Element found) {
        for (JButton btn : frame.getElementButtons()) {
            btn.setVisible(found == null || btn.getText().equalsIgnoreCase(found.getSymbol()));
        }
        repaint();
    }

    public void resetGrid() {
        for (JButton btn : frame.getElementButtons()) {
            btn.setVisible(true);
        }
        if (!isPanelLocked) {
            detailPanel.setVisible(false);
        }
        repaint();
    }
}