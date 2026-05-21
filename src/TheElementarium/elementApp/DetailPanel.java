package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.awt.*;
import javax.swing.*;

public class DetailPanel extends JPanel {
    private JLabel lblImage, lblName, lblGroup;
    private JTextArea txtDetails, txtApp;

    public DetailPanel() {
        setLayout(new BorderLayout(5, 5));
        setBackground(ElementGUI.lightenColor(ElementGUI.HEADER_LINE, 0.3));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setVisible(false);
        initComponents();
    }

    private void initComponents() {
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        lblImage = new JLabel();
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblName = new JLabel("Name", SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 18));
        lblName.setForeground(ElementGUI.DARK_BLUE);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblGroup = new JLabel("Group Type", SwingConstants.CENTER);
        lblGroup.setFont(new Font("Arial", Font.ITALIC, 13));
        lblGroup.setForeground(Color.DARK_GRAY);
        lblGroup.setAlignmentX(Component.CENTER_ALIGNMENT);

        topContainer.add(lblImage);
        topContainer.add(Box.createRigidArea(new Dimension(0, 6)));
        topContainer.add(lblName);
        topContainer.add(Box.createRigidArea(new Dimension(0, 3)));
        topContainer.add(lblGroup);

        txtDetails = new JTextArea();
        txtDetails.setEditable(false);
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        txtDetails.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDetails.setOpaque(false);

        txtApp = new JTextArea();
        txtApp.setEditable(false);
        txtApp.setLineWrap(true);
        txtApp.setWrapStyleWord(true);
        txtApp.setFont(new Font("Arial", Font.PLAIN, 12));
        txtApp.setOpaque(false);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setOpaque(false);
        textContainer.add(Box.createRigidArea(new Dimension(0, 6)));
        textContainer.add(txtDetails);
        textContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        textContainer.add(txtApp);

        JScrollPane scrollPane = new JScrollPane(textContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void populateData(Element element) {
        lblName.setText(element.getSymbol() + " - " + element.getName());
        lblGroup.setText(element.getGroup());

        txtDetails.setText(String.format(
            "• Atomic Number: %d\n• Atomic Mass: %.3f u\n• Configuration: %s",
            element.getAtomicNumber(), element.getAtomicWeight(), element.getElectronConfig()
        ));
        txtApp.setText("Applications:\n" + element.getApplications());

        Color baseColor = ElementGUI.getGroupColor(element.getGroup());
        setBackground(ElementGUI.lightenColor(baseColor, 0.5));

        setVisible(true);
        revalidate();
        repaint();
    }

    public void updateLayout(int panelWidth) {
        int imgWidth = panelWidth - 24;
        int imgHeight = (int) (imgWidth * 0.42);
        lblImage.setPreferredSize(new Dimension(imgWidth, imgHeight));
        lblImage.setMaximumSize(new Dimension(imgWidth, imgHeight));
        
        int maxTextWidth = panelWidth - 24;
        txtDetails.setSize(maxTextWidth, 1);
        txtApp.setSize(maxTextWidth, 1);
    }
}