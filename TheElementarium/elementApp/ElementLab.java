package TheElementarium.elementApp;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class ElementLab extends JPanel {
    private final List<String> activeSelectionSlots = new ArrayList<>();
    private final Map<String, String> combinationCSVDatabase = new HashMap<>();
    private final JLabel formulaDisplayLabel;
    private final JTextArea resultsDisplayLog;
    private final JPanel gridSelectionContainer;
    private final JButton mixElementsBtn;

    public ElementLab(ActionListener onBackClicked, ActionListener onOpenPeriodicTable) {
        this.setBackground(ElementGUI.REGAL_NAVY);
        this.setLayout(new BorderLayout(25, 25));
        this.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        loadExternalCSVDatabase();

        JPanel topHubPanel = new JPanel(new GridBagLayout());
        topHubPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.CENTER;

        try {
            URL titleURL = getClass().getResource("/TheElementarium/elements/elementGraphics/title.png");
            if (titleURL != null) {
                ImageIcon titleIcon = new ImageIcon(titleURL);
                Image scaledImg = titleIcon.getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH);
                JLabel titleImgLabel = new JLabel(new ImageIcon(scaledImg));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.0;
                gbc.insets = new Insets(0, 0, 0, 20);
                topHubPanel.add(titleImgLabel, gbc);
            }
        } catch (Exception e) {
            System.err.println("Could not load title image in ElementLab: " + e.getMessage());
        }

        JLabel titleLabel = new JLabel("Reaction Predictor");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(ElementGUI.LEMON_CHIFFON);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 20);
        topHubPanel.add(titleLabel, gbc);

        formulaDisplayLabel = new JLabel("Select 2 or 3 elements to begin...", SwingConstants.CENTER);
        formulaDisplayLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        formulaDisplayLabel.setForeground(ElementGUI.LEMON_CHIFFON);
        formulaDisplayLabel.setBackground(new Color(5, 28, 51)); 
        formulaDisplayLabel.setOpaque(true);
        formulaDisplayLabel.setBorder(BorderFactory.createLineBorder(ElementGUI.LEMON_CHIFFON, 1));
        formulaDisplayLabel.setPreferredSize(new Dimension(450, 50));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 20);
        topHubPanel.add(formulaDisplayLabel, gbc);

        JPanel rightActionStrip = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightActionStrip.setOpaque(false);
        mixElementsBtn = createDynamicUtilityButton("MIX ELEMENTS", ElementGUI.LEMON_CHIFFON, ElementGUI.REGAL_NAVY); 
        mixElementsBtn.setEnabled(false);
        mixElementsBtn.addActionListener(e -> executeReactionLookup());
        
        JButton clearBtn = createDynamicUtilityButton("Reset Slots", new Color(214, 75, 75), Color.WHITE);
        clearBtn.addActionListener(e -> clearWorkspaceDeck());
        rightActionStrip.add(mixElementsBtn);
        rightActionStrip.add(clearBtn);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        topHubPanel.add(rightActionStrip, gbc);
        this.add(topHubPanel, BorderLayout.NORTH);

        JPanel splitWorkspace = new JPanel(new GridLayout(1, 2, 35, 0));
        splitWorkspace.setOpaque(false);
        gridSelectionContainer = new JPanel(new GridLayout(2, 4, 12, 12));
        gridSelectionContainer.setOpaque(false);
        populateElementSelectionDeck();
        
        JPanel deckWrapper = new JPanel(new BorderLayout());
        deckWrapper.setOpaque(false);
        deckWrapper.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ElementGUI.LEMON_CHIFFON, 1), " ELEMENTS OF COMMON COMPOUNDS ", 0, 0, new Font("SansSerif", Font.BOLD, 12), ElementGUI.LEMON_CHIFFON));
        
        JPanel layoutSpacedDeck = new JPanel(new BorderLayout());
        layoutSpacedDeck.setOpaque(false);
        layoutSpacedDeck.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        layoutSpacedDeck.add(gridSelectionContainer, BorderLayout.CENTER);
        deckWrapper.add(layoutSpacedDeck, BorderLayout.CENTER);
        splitWorkspace.add(deckWrapper);

        RoundedSolidPanel reportContainer = new RoundedSolidPanel(new BorderLayout(), 16, new Color(5, 28, 51));
        reportContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ElementGUI.LEMON_CHIFFON, 1)," SYNTHESIS REPORT ", 0, 0, new Font("SansSerif", Font.BOLD, 12), ElementGUI.LEMON_CHIFFON));
        resultsDisplayLog = new JTextArea("\n\nSystem Ready.\n\n1. Click 2 or 3 element blocks to load standby deck.\n2. Click 'MIX ELEMENTS' to synthesize target compounds.");
        resultsDisplayLog.setFont(new Font("Arial", Font.PLAIN, 25));
        resultsDisplayLog.setForeground(ElementGUI.LEMON_CHIFFON);
        resultsDisplayLog.setOpaque(false); 
        resultsDisplayLog.setEditable(false);
        resultsDisplayLog.setLineWrap(true);
        resultsDisplayLog.setWrapStyleWord(true);
        resultsDisplayLog.setMargin(new Insets(20, 20, 20, 20));
        
        JScrollPane logsScrollPane = new JScrollPane(resultsDisplayLog);
        logsScrollPane.setOpaque(false);
        logsScrollPane.getViewport().setOpaque(false);
        logsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        reportContainer.add(logsScrollPane, BorderLayout.CENTER);
        splitWorkspace.add(reportContainer);
        this.add(splitWorkspace, BorderLayout.CENTER);

        JPanel bottomStrip = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottomStrip.setOpaque(false);
        
        JButton backBtn = createInteractiveMenuButton("Back to Main Menu", onBackClicked);
        JButton seePeriodicBtn = createInteractiveMenuButton("See Periodic Table", onOpenPeriodicTable);
        bottomStrip.add(backBtn);
        bottomStrip.add(seePeriodicBtn);
        this.add(bottomStrip, BorderLayout.SOUTH);
    }

    private void loadExternalCSVDatabase() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/TheElementarium/elements/reactions.csv")))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (columns.length >= 5) {
                    String lookupKey = canonicalizeLookupKey(columns[0]);
                    String productCompound = columns[1].trim();
                    String reactionType = columns[2].trim();
                    String balancedEquation = columns[3].trim();
                    String applicationDetails = columns[4].trim();
                    String reportBody = String.format(
                        "Product Compound : %s\nReaction Type    : %s\nBalanced Formula : %s\n\nApplication Profiles:\n• %s",
                        productCompound, reactionType, balancedEquation, applicationDetails.replace(". ", ".\n• "));
                    combinationCSVDatabase.put(lookupKey, reportBody);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read system resource file reactions.csv! " + e.getMessage());
            combinationCSVDatabase.put("H,O", "Product Compound : H2O (Water)\nReaction Type    : Fallback Operational Mode\nBalanced Formula : 2H2 + O2 -> 2H2O");
        }
    }

    private void populateElementSelectionDeck() {
        String[][] elementsToLoad = {
            {"H", "Hydrogen", "Reactive nonmetals"},
            {"Na", "Sodium", "Alkali metals"},
            {"Fe", "Iron", "Transition metals"},
            {"C", "Carbon", "Reactive nonmetals"},
            {"N", "Nitrogen", "Reactive nonmetals"},
            {"O", "Oxygen", "Reactive nonmetals"},
            {"Cl", "Chlorine", "Halogens"},
            {"S", "Sulfur", "Reactive nonmetals"}
        };
        for (String[] el : elementsToLoad) {
            gridSelectionContainer.add(new StandardizedBlockCard(el[0], el[1], el[2]));
        }
    }

    private void handleElementClicked(String symbol) {
        if (activeSelectionSlots.size() >= 3) {resultsDisplayLog.setText("\n\nSlots Maximized!\nAt most 3 components can be combined at once. Click 'Reset Slots' or mix elements.");
            return;
        }
        activeSelectionSlots.add(symbol);
        updateInterfaceOutputs();
    }

    private void updateInterfaceOutputs() {
        if (activeSelectionSlots.isEmpty()) {
            formulaDisplayLabel.setText("Select 2 or 3 elements to begin...");
            mixElementsBtn.setEnabled(false);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < activeSelectionSlots.size(); i++) {
            sb.append(activeSelectionSlots.get(i));
            if (i < activeSelectionSlots.size() - 1) sb.append(" + ");
        }
        formulaDisplayLabel.setText(sb.toString());
        mixElementsBtn.setEnabled(activeSelectionSlots.size() >= 2);
    }

    private void executeReactionLookup() {
        if (activeSelectionSlots.size() < 2) return;
        List<String> sortedQueryList = new ArrayList<>(activeSelectionSlots);
        sortedQueryList.sort(String::compareTo);
        
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < sortedQueryList.size(); i++) {
            keyBuilder.append(sortedQueryList.get(i));
            if (i < sortedQueryList.size() - 1) keyBuilder.append(",");
        }
        String generatedLookupKey = keyBuilder.toString();

        if (combinationCSVDatabase.containsKey(generatedLookupKey)) {
            resultsDisplayLog.setText("\n=== MATCHED REACTION FOUND ===\n\n" + combinationCSVDatabase.get(generatedLookupKey));
        } else {
            resultsDisplayLog.setText("\n=== COMBINATION RESULT ===\n\nUnknown Compound Profile\n\nNo stable matching reaction path cataloged in current records.\n\nTry active paths like:\n(C,H,O) (C,N,Na) (C,O) (Cl,Na)\n(Fe,O) (H,N) (H,N,O) (H,O)\n(H,O,S) (Cl,H) (N,O) (O,S)\n(C,H,S) (Na,O,S) (Fe,S) (C,Na,O)\n(N,H,S) (Fe,Cl) (C,N,O)");
        }
    }

    private void clearWorkspaceDeck() {
        activeSelectionSlots.clear();
        updateInterfaceOutputs();
        resultsDisplayLog.setText("\nSystem Ready.\n\n1. Click 2 or 3 element blocks to load standby deck.\n2. Click 'MIX ELEMENTS' to synthesize target compounds.");
    }

    private static String canonicalizeLookupKey(String rawKey) {
        if (rawKey == null) return "";
        String cleaned = rawKey.replace("\"", "").trim();
        String[] parts = cleaned.split("\\s*,\\s*");
        java.util.Arrays.sort(parts, String.CASE_INSENSITIVE_ORDER);
        return String.join(",", parts);
    }

    private static class RoundedSolidPanel extends JPanel {
        private final int cornerRadius;
        private final Color solidBgColor;

        public RoundedSolidPanel(LayoutManager layout, int radius, Color bgColor) {
            super(layout);
            this.cornerRadius = radius;
            this.solidBgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(solidBgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private class StandardizedBlockCard extends JPanel {
        private boolean isHovered = false;
        private boolean isPressed = false;
        private final JLabel symbolLbl;
        private final JLabel nameLbl;

        public StandardizedBlockCard(String symbol, String name, String groupName) {
            this.setOpaque(false);
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
            setPreferredSize(new Dimension(100, 100));

            symbolLbl = new JLabel(symbol, SwingConstants.CENTER);
            symbolLbl.setFont(new Font("SansSerif", Font.BOLD, 30));
            symbolLbl.setForeground(ElementGUI.REGAL_NAVY);

            nameLbl = new JLabel(name, SwingConstants.CENTER);
            nameLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            nameLbl.setForeground(ElementGUI.REGAL_NAVY);

            this.add(symbolLbl, BorderLayout.CENTER);
            this.add(nameLbl, BorderLayout.SOUTH);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { handleElementClicked(symbol); }
                @Override
                public void mouseEntered(MouseEvent e) { isHovered = true; symbolLbl.setFont(symbolLbl.getFont().deriveFont(Font.BOLD, 32f)); revalidate(); repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { isHovered = false; isPressed = false; symbolLbl.setFont(symbolLbl.getFont().deriveFont(Font.BOLD, 24f)); revalidate(); repaint(); }
                @Override
                public void mousePressed(MouseEvent e) { isPressed = true; symbolLbl.setFont(symbolLbl.getFont().deriveFont(Font.BOLD, 36f)); revalidate(); repaint(); }
                @Override
                public void mouseReleased(MouseEvent e) { isPressed = false; symbolLbl.setFont(symbolLbl.getFont().deriveFont(Font.BOLD, isHovered ? 32f : 24f)); revalidate(); repaint(); }});
            
            this.setBackground(ElementGUI.getGroupColor(groupName));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int offset = isPressed ? 3 : (isHovered ? 2 : 0);
            int width = getWidth() - (offset * 2);
            int height = getHeight() - (offset * 2);

            g2.setColor(getBackground());
            g2.fillRoundRect(offset, offset, width, height, 8, 8);

            if (isHovered) {
                g2.setStroke(new BasicStroke(isPressed ? 5.0f : 5.0f));
                g2.setColor(new Color(255, 255, 255, isPressed ? 200 : 140));
                g2.drawRoundRect(offset + 1, offset + 1, width - 3, height - 3, 8, 8);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(offset - 4, offset - 4, width + 8, height + 8, 12, 12);
            } else {
                g2.setStroke(new BasicStroke(1.0f));
                g2.setColor(new Color(0, 0, 0, 30));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
            g2.dispose();
        }
    }

    private JButton createDynamicUtilityButton(String text, Color baseColor, Color textColor) {
        JButton btn = new JButton(text) {
            private boolean isHovered = false;
            private boolean isPressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { isHovered = true; setPreferredSize(new Dimension(155, 48)); revalidate(); repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { isHovered = false; isPressed = false; setPreferredSize(new Dimension(145, 44)); revalidate(); repaint(); }
                    @Override
                    public void mousePressed(MouseEvent e) { isPressed = true; setPreferredSize(new Dimension(150, 46)); revalidate(); repaint(); }
                    @Override
                    public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); } });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (!this.isEnabled()) {
                    g2.setColor(new Color(25, 73, 117));
                } else {
                    g2.setColor(isPressed ? baseColor.darker() : baseColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                if (isHovered && this.isEnabled()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.setColor(Color.WHITE);
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 8, 8);
                    g2.dispose();
                }
            }
        };

        btn.setPreferredSize(new Dimension(145, 44));
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(textColor);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        return btn;
    }

    private JButton createInteractiveMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text) {
            private boolean hovered = false;
            private boolean pressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { hovered = false; pressed = false; repaint(); }
                    @Override
                    public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                    @Override
                    public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hovered || pressed) {
                    Color glow = new Color(255, 255, 255, pressed ? 120 : 80);
                    g2.setColor(glow);
                    g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 14, 14);
                }
                g2.setColor(pressed ? getBackground().darker() : getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                super.paintComponent(g2);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (pressed) {
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(new Color(255, 255, 255, 200));
                } else if (hovered) {
                    g2.setStroke(new BasicStroke(2.5f));
                    g2.setColor(new Color(255, 255, 255, 180));
                } else {
                    g2.setStroke(new BasicStroke(1.2f));
                    g2.setColor(new Color(0, 0, 0, 40));
                }
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };

        button.setPreferredSize(new Dimension(210, 45));
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(ElementGUI.DARK_BLUE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBackground(ElementGUI.LEMON_CHIFFON);
        button.addActionListener(action);
        return button;
    }
}
