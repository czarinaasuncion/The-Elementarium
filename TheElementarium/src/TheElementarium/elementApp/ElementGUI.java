package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class ElementGUI extends JFrame {
    private final Color DARK_BLUE = new Color(42, 70, 116);
    private final Color HEADER_LINE = new Color(173, 216, 230);
    private final Color DEFAULT_PASTEL = new Color(246, 154, 162);

    public Elementarium logic = new Elementarium();

    JLayeredPane centerPanel;
    private JPanel detailPanel;
    private JLabel lblImage, lblName, lblGroup;
    private JTextArea txtDetails, txtApp;
    private final List<JButton> elementButtons = new ArrayList<>();
    
    private boolean isPanelLocked = false;
    private String currentActiveSymbol = "";

    private final int BLOCK_WIDTH = 55;
    private final int BLOCK_HEIGHT = 70;

    private Color lightenColor(Color color, double factor) {
    int r = (int) Math.min(255, color.getRed() + (255 - color.getRed()) * factor);
    int g = (int) Math.min(255, color.getGreen() + (255 - color.getGreen()) * factor);
    int b = (int) Math.min(255, color.getBlue() + (255 - color.getBlue()) * factor);
    return new Color(r, g, b);
}

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

    public ElementGUI() {
        logic.initializeSystem();
        setupFrame();
        setupHeader();
        setupCenterPanel();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("The Elementarium");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 760));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void setupHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK_BLUE);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, HEADER_LINE));

        JTextField searchField = new JTextField("Search Element", 15);
        searchField.addFocusListener((FocusListener) new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        if (searchField.getText().equals("Search Element")) {
            searchField.setText("");
        }
    }
});
        JButton searchBtn = new JButton("Find");
        searchBtn.addActionListener(e -> performSearch(searchField.getText().trim()));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 18));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        header.add(searchPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }

    private void setupCenterPanel() {
    // Use JLayeredPane instead of JPanel
    centerPanel = new JLayeredPane();
    centerPanel.setBackground(DARK_BLUE);
    centerPanel.setOpaque(true);
    add(centerPanel, BorderLayout.CENTER);

    setupDetailPanel();

    for (String[] data : elementData) {
        String symbol = data[0];
        JButton btn = createElementButton(symbol);
        btn.putClientProperty("col", Integer.valueOf(data[1]));
        btn.putClientProperty("row", Integer.valueOf(data[2]));
        elementButtons.add(btn);

        centerPanel.add(btn, JLayeredPane.DEFAULT_LAYER);
    }

    centerPanel.add(detailPanel, JLayeredPane.PALETTE_LAYER);

    centerPanel.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            recalculateResponsiveGrid();
        }
    });
}
    private Color getGroupColor(String group) {
    return switch (group) {
        case "Alkali metals" -> new Color(255, 153, 153);       // pastel coral red
        case "Alkaline earth metals" -> new Color(255, 204, 153); // pastel orange-peach
        case "Transition metals" -> new Color(255, 255, 153);     // pastel yellow
        case "Post-transition metals" -> new Color(153, 255, 204); // pastel turquoise
        case "Metalloids" -> new Color(204, 153, 255);           // pastel violet-purple
        case "Reactive nonmetals" -> new Color(255, 153, 204);   // pastel pink-rose
        case "Halogens" -> new Color(255, 153, 255);             // pastel magenta-lavender
        case "Noble gases" -> new Color(153, 255, 255);           // pastel cyan
        case "Lanthanides" -> new Color(255, 204, 229);          // pastel blush pink
        case "Actinides" -> new Color(255, 204, 178);            // pastel apricot
        default -> DEFAULT_PASTEL;
    };
}


    private void recalculateResponsiveGrid() {
        int panelWidth = centerPanel.getWidth();
        int panelHeight = centerPanel.getHeight();
        if (panelWidth <= 0 || panelHeight <= 0) return;

        int leftMargin = 40;
        int topMargin = 20;

        double totalAvailableWidth = panelWidth - (leftMargin * 2) - BLOCK_WIDTH;
        double hGap = Math.max(4, totalAvailableWidth / 17.0 - BLOCK_WIDTH);

        double totalAvailableHeight = panelHeight - (topMargin * 2) - BLOCK_HEIGHT;
        double vGap = Math.max(4, Math.min(10, totalAvailableHeight / 8.8 - BLOCK_HEIGHT));

        for (JButton btn : elementButtons) {
            int col = (int) btn.getClientProperty("col");
            int row = (int) btn.getClientProperty("row");

            int x = (int) (leftMargin + (col - 1) * (BLOCK_WIDTH + hGap));
            int y = (int) (topMargin + (row - 1) * (BLOCK_HEIGHT + vGap));

            if (row >= 8) {
                y += 20;
            }

            btn.setBounds(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        }
        
        if (detailPanel != null && detailPanel.isVisible()) {
            for (JButton btn : elementButtons) {
                if (btn.getText().equals(currentActiveSymbol)) {
                    positionDetailPanel(btn.getX(), btn.getY(), detailPanel.getWidth(), detailPanel.getHeight());
                    break;
                }
            }
        }
    }

    private JButton createElementButton(String symbol) {
    JButton btn = new JButton(symbol);
    btn.setSize(BLOCK_WIDTH, BLOCK_HEIGHT);
    btn.setContentAreaFilled(false);
    btn.setOpaque(true);

    Element element = logic.findElement(symbol);
    btn.setBackground(element != null ? getGroupColor(element.getGroup()) : DEFAULT_PASTEL);

    btn.setFocusPainted(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setFont(new Font("Arial", Font.BOLD, 16));
    btn.setForeground(DARK_BLUE);
    btn.setMargin(new Insets(0, 0, 0, 0));

    btn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (isPanelLocked) return;
            Element el = logic.findElement(symbol);
            if (el != null) {
                currentActiveSymbol = symbol;
                positionDetailPanel(btn.getX(), btn.getY(), 260, 380);
                showElementDetails(el);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!isPanelLocked) {
                detailPanel.setVisible(false);
                centerPanel.repaint();
            }
        }
    });

    btn.addActionListener(e -> {
        if (element != null) {
            if (isPanelLocked && currentActiveSymbol.equals(symbol)) {
                isPanelLocked = false;
                detailPanel.setVisible(false);
                centerPanel.repaint();
            } else {
                isPanelLocked = true;
                currentActiveSymbol = symbol;
                positionDetailPanel(btn.getX(), btn.getY(),300, 400); // bigger size
                showElementDetails(element);
            }
        }
    });

    return btn;
}


    private void setupDetailPanel() {
    detailPanel = new JPanel();
    detailPanel.setLayout(new BorderLayout(5, 5));
    Color baseColor = HEADER_LINE;
    detailPanel.setBackground(lightenColor(baseColor, 0.3));
    detailPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    detailPanel.setVisible(false);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.setOpaque(false);

    lblImage = new JLabel();
    lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

    lblName = new JLabel("Name", SwingConstants.CENTER);
    lblName.setFont(new Font("Arial", Font.BOLD, 20));
    lblName.setForeground(DARK_BLUE);
    lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

    lblGroup = new JLabel("Group Type", SwingConstants.CENTER);
    lblGroup.setFont(new Font("Arial", Font.ITALIC, 14));
    lblGroup.setForeground(Color.DARK_GRAY);
    lblGroup.setAlignmentX(Component.CENTER_ALIGNMENT);

    topContainer.add(lblImage);
    topContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    topContainer.add(lblName);
    topContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    topContainer.add(lblGroup);

    txtDetails = new JTextArea();
    txtDetails.setEditable(false);
    txtDetails.setLineWrap(true);
    txtDetails.setWrapStyleWord(true);
    txtDetails.setFont(new Font("Arial", Font.PLAIN, 13));
    txtDetails.setOpaque(false);

    txtApp = new JTextArea();
    txtApp.setEditable(false);
    txtApp.setLineWrap(true);
    txtApp.setWrapStyleWord(true);
    txtApp.setFont(new Font("Arial", Font.PLAIN, 13));
    txtApp.setOpaque(false);

    JPanel textContainer = new JPanel();
    textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
    textContainer.setOpaque(false);
    textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    textContainer.add(txtDetails);
    textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    textContainer.add(txtApp);

    JScrollPane scrollPane = new JScrollPane(textContainer,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);
    scrollPane.setPreferredSize(new Dimension(300, 200));


    detailPanel.add(topContainer, BorderLayout.NORTH);
    detailPanel.add(scrollPane, BorderLayout.CENTER);
    centerPanel.add(detailPanel, JLayeredPane.PALETTE_LAYER);
    }

    private void positionDetailPanel(int elementX, int elementY, int panelWidth, int panelHeight) {
        int panelX;
        int spacing = 12;

        if (elementX > (centerPanel.getWidth() / 2)) {
            panelX = elementX - panelWidth - spacing;
        } else {
            panelX = elementX + BLOCK_WIDTH + spacing;
        }

        int panelY = elementY;
        if (panelY + panelHeight > centerPanel.getHeight() && centerPanel.getHeight() > 0) {
            panelY = centerPanel.getHeight() - panelHeight - 10;
        }
        if (panelY < 10) panelY = 10;

        detailPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
        
        int imgWidth = panelWidth - 24;
        int imgHeight = (int) (imgWidth * 0.45);
        lblImage.setPreferredSize(new Dimension(imgWidth, imgHeight));
        lblImage.setMaximumSize(new Dimension(imgWidth, imgHeight));
        
        centerPanel.setComponentZOrder(detailPanel, 0);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showElementDetails(Element element) {
    lblName.setText(element.getSymbol() + " - " + element.getName());
    lblGroup.setText(element.getGroup());

    txtDetails.setText(String.format(
        "• Atomic Number: %d\n• Atomic Mass: %.3f u\n• Configuration: %s",
        element.getAtomicNumber(), element.getAtomicWeight(), element.getElectronConfig()
    ));
    txtApp.setText("Applications:\n" + element.getApplications());

    Color baseColor = getGroupColor(element.getGroup());
    detailPanel.setBackground(lightenColor(baseColor, 0.5));

    detailPanel.setVisible(true);
    detailPanel.revalidate();
    detailPanel.repaint();
    centerPanel.revalidate();
    centerPanel.repaint();
}

    protected void performSearch(String query) {
        Element found = logic.findElement(query);
        for (JButton btn : elementButtons) {
            btn.setVisible(found == null || btn.getText().equalsIgnoreCase(found.getSymbol()));
        }
        centerPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElementGUI::new);
    }
}
