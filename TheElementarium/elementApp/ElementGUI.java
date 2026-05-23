package TheElementarium.elementApp;

import TheElementarium.elements.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ElementGUI extends JFrame {
    public static final Color REGAL_NAVY = new Color(13, 59, 102);
    public static final Color LEMON_CHIFFON = new Color(250, 240, 202);
    public static final Color DARK_BLUE = REGAL_NAVY;
    public static final Color HEADER_LINE = LEMON_CHIFFON;
    public static final Color DEFAULT_PASTEL = LEMON_CHIFFON;

    public final Elementarium logic = new Elementarium();
    private final List<JButton> elementButtons = new ArrayList<>();

    private final CardLayout cardLayout;
    private final JPanel mainContainer;

    private HeaderPanel headerPanel;
    private PeriodicTablePanel tablePanel;
    private final JPanel periodicTableScreen;
    private final ElementLab elementLabPanel;

    public ElementGUI() {
        logic.initializeSystem();
        setupFrame();
        
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        MainMenuPanel menuPanel = new MainMenuPanel(
            e -> switchToFullScreen("PeriodicTable"),
            e -> switchToFullScreen("StudyChem")
        );

        elementLabPanel = new ElementLab(e -> switchToMenuScreen(), e -> switchToFullScreen("PeriodicTable"));
        periodicTableScreen = createPeriodicTableScreen();

        mainContainer.add(menuPanel, "MainMenu");
        mainContainer.add(periodicTableScreen, "PeriodicTable");
        mainContainer.add(elementLabPanel, "StudyChem");

        add(mainContainer, BorderLayout.CENTER);
        
        switchToMenuScreen();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("The Elementarium");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void switchToMenuScreen() {
        setExtendedState(JFrame.NORMAL);
        
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Rectangle screenBounds = gc.getBounds();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        int usableHeight = screenBounds.height - (screenInsets.top + screenInsets.bottom);
        
        int lockedWidth = 600;
        int maximumWindowHeight = (int) (usableHeight * 0.98); 

        setMinimumSize(new Dimension(lockedWidth, 650));
        setSize(lockedWidth, maximumWindowHeight);
        
        int xPosition = (screenBounds.width - lockedWidth) / 2;
        int yPosition = screenInsets.top + (usableHeight - maximumWindowHeight) / 2;
        setLocation(xPosition, yPosition); 
        
        cardLayout.show(mainContainer, "MainMenu");
    }

    private void switchToFullScreen(String targetCardName) {
        setMinimumSize(new Dimension(1200, 850));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        cardLayout.show(mainContainer, targetCardName);
        
        if ("StudyChem".equals(targetCardName) && elementLabPanel != null) {
            elementLabPanel.revalidate();
            elementLabPanel.repaint();
        }
    }

    private JPanel createPeriodicTableScreen() {
        JPanel screen = new JPanel(new BorderLayout());
        screen.setBackground(REGAL_NAVY);
        
        tablePanel = new PeriodicTablePanel(this, e -> switchToMenuScreen(), e -> switchToFullScreen("StudyChem"));
        tablePanel.setBackground(REGAL_NAVY);
        
        headerPanel = new HeaderPanel(this);
        headerPanel.setBackground(REGAL_NAVY);

        screen.add(headerPanel, BorderLayout.NORTH);
        screen.add(tablePanel, BorderLayout.CENTER);

        return screen;
    }

    public List<JButton> getElementButtons() { return elementButtons; }
    public PeriodicTablePanel getTablePanel() { return tablePanel; }

    public static Element createElement(String name, String symbol, int atomicNumber, double atomicWeight, 
                                        String group, String electronConfig, String applications) {
        return switch (group) {
            case "Alkali metals" -> new AlkaliMetal(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Alkaline earth metals" -> new AlkalineEarthMetal(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Transition metals" -> new TransitionMetal(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Post-transition metals" -> new PostTransitionMetal(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Metalloids" -> new Metalloid(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Reactive nonmetals" -> new ReactiveNonmetal(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Halogens" -> new Halogen(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Noble gases" -> new NobleGas(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Lanthanides" -> new Lanthanide(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            case "Actinides" -> new Actinide(name, symbol, atomicNumber, atomicWeight, electronConfig, applications);
            default -> new Element(name, symbol, atomicNumber, atomicWeight, group, electronConfig, applications);
        };
    }

    public static Color getGroupColor(String group) {
        return switch (group) {
            case "Alkali metals" -> new Color(255, 153, 153);
            case "Alkaline earth metals" -> new Color(255, 204, 153);
            case "Transition metals" -> new Color(255, 255, 153);
            case "Post-transition metals" -> new Color(153, 255, 204);
            case "Metalloids" -> new Color(204, 153, 255);
            case "Reactive nonmetals" -> new Color(255, 153, 204);
            case "Halogens" -> new Color(255, 153, 255);
            case "Noble gases" -> new Color(153, 255, 255);
            case "Lanthanides" -> new Color(255, 204, 229);
            case "Actinides" -> new Color(255, 204, 178);
            default -> DEFAULT_PASTEL;
        };
    }

    public static Color lightenColor(Color color, double factor) {
        int r = (int) Math.min(255, color.getRed() + (255 - color.getRed()) * factor);
        int g = (int) Math.min(255, color.getGreen() + (255 - color.getGreen()) * factor);
        int b = (int) Math.min(255, color.getBlue() + (255 - color.getBlue()) * factor);
        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElementGUI::new);
    }
}