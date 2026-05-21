package TheElementarium.elementApp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LegendPanel extends JPanel {
    public LegendPanel() {
        setLayout(new GridLayout(2, 5, 8, 6));
        setOpaque(false);

        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ElementGUI.HEADER_LINE, 1), "Element Groups Legend"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 12));
        border.setTitleColor(ElementGUI.HEADER_LINE);
        setBorder(border);

        String[] groups = {
            "Alkali metals", "Alkaline earth metals", "Transition metals", "Post-transition metals", "Metalloids",
            "Reactive nonmetals", "Halogens", "Noble gases", "Lanthanides", "Actinides"
        };

        for (String group : groups) {
            JPanel item = new JPanel(new GridBagLayout());
            item.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(14, 14));
            colorBox.setMinimumSize(new Dimension(14, 14));
            colorBox.setBackground(ElementGUI.getGroupColor(group));
            colorBox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

            JTextArea labelArea = new JTextArea(group);
            labelArea.setFont(new Font("Arial", Font.BOLD, 11));
            labelArea.setForeground(Color.WHITE);
            labelArea.setEditable(false);
            labelArea.setFocusable(false);
            labelArea.setOpaque(false);
            labelArea.setLineWrap(true);
            labelArea.setWrapStyleWord(true);
            labelArea.setSize(new Dimension(100, 30)); 

            gbc.gridx = 0; gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 4, 0, 6); 
            item.add(colorBox, gbc);

            gbc.gridx = 1; gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 0, 0, 2);
            item.add(labelArea, gbc);

            add(item);
        }
    }
}