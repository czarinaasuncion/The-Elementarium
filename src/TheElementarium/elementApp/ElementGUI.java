package TheElementarium.elementApp;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ElementGUI extends JFrame {
    public static final Color DARK_BLUE = new Color(42, 70, 116);
    public static final Color HEADER_LINE = new Color(173, 216, 230);
    public static final Color DEFAULT_PASTEL = new Color(246, 154, 162);

    public final Elementarium logic = new Elementarium();
    private final List<JButton> elementButtons = new ArrayList<>();
    
    private final HeaderPanel headerPanel;
    private final PeriodicTablePanel tablePanel;

    public ElementGUI() {
        logic.initializeSystem();
        setupFrame();
        
        tablePanel = new PeriodicTablePanel(this);
        headerPanel = new HeaderPanel(this);

        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("The Elementarium");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 850));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    public List<JButton> getElementButtons() {
        return elementButtons;
    }

    public PeriodicTablePanel getTablePanel() {
        return tablePanel;
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