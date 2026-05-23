package TheElementarium.elementApp;

import java.awt.*;
import javax.swing.*;

public class LegendPanel extends JPanel {

    private final String[] groups = {
        "Alkali metals", "Alkaline earth metals", "Transition metals",
        "Post-transition metals", "Metalloids", "Reactive nonmetals",
        "Halogens", "Noble gases", "Lanthanides", "Actinides"
    };

    public LegendPanel() {
        setOpaque(false);
        setLayout(new GridLayout(2, 4, 8, 6)); 
        setBorder(BorderFactory.createEmptyBorder(22, 15, 12, 15)); 

        for (String group : groups) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            itemPanel.setOpaque(false);

            JPanel colorBox = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ElementGUI.getGroupColor(group));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2.setColor(ElementGUI.getGroupColor(group).darker());
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                    g2.dispose();
                }
            };
            colorBox.setPreferredSize(new Dimension(14, 14));
            colorBox.setOpaque(false);

            JLabel groupLabel = new JLabel(group);
            groupLabel.setForeground(Color.WHITE);
            groupLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            groupLabel.setPreferredSize(new Dimension(120, 20));

            itemPanel.add(colorBox);
            itemPanel.add(groupLabel);
            add(itemPanel);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color themeColor = ElementGUI.HEADER_LINE;
        g2.setColor(themeColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 10, getWidth() - 2, getHeight() - 12, 12, 12);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(ElementGUI.DARK_BLUE);
        g2.fillRect(12, 2, 140, 15);
        g2.setColor(themeColor);
        g2.drawString("Element Groups Legend", 16, 13);

        g2.dispose();
    }
}