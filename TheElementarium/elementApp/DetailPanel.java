package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DetailPanel extends JPanel {
    private JLabel lblImage, lblName, lblGroup;
    private JTextArea txtDetails, txtApp;
    private Image currentGroupImage;

    public DetailPanel() {
        setLayout(new BorderLayout(5, 5));
        setBackground(ElementGUI.lightenColor(ElementGUI.HEADER_LINE, 0.3));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setVisible(false);
        initComponents();
    }

    private void initComponents() {
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        lblImage = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentGroupImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2d.drawImage(currentGroupImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblName = new JLabel("Name", SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 22));
        lblName.setForeground(ElementGUI.DARK_BLUE);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblGroup = new JLabel("Group Type", SwingConstants.CENTER);
        lblGroup.setFont(new Font("Arial", Font.ITALIC, 15));
        lblGroup.setForeground(Color.DARK_GRAY);
        lblGroup.setAlignmentX(Component.CENTER_ALIGNMENT);

        topContainer.add(lblImage);
        topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        topContainer.add(lblName);
        topContainer.add(Box.createRigidArea(new Dimension(0, 4)));
        topContainer.add(lblGroup);

        txtDetails = new JTextArea();
        txtDetails.setEditable(false);
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        txtDetails.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDetails.setOpaque(false);

        txtApp = new JTextArea();
        txtApp.setEditable(false);
        txtApp.setLineWrap(true);
        txtApp.setWrapStyleWord(true);
        txtApp.setFont(new Font("Arial", Font.PLAIN, 14));
        txtApp.setOpaque(false);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setOpaque(false);
        textContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        textContainer.add(txtDetails);
        textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
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

    @SuppressWarnings("UseSpecificCatch")
    public void populateData(Element element) {
        lblName.setText(element.getSymbol() + " - " + element.getName());
        lblGroup.setText(element.getGroup());

        txtDetails.setText(String.format(
            "• Atomic Number: %d\n• Atomic Mass: %.3f u\n• Configuration: %s",
            element.getAtomicNumber(), element.getAtomicWeight(), element.getElectronConfig()
        ));
        txtApp.setText("Applications:\n" + element.getApplications());

        try {
            String imageName = element.getGroup() + ".png";
            URL imgURL = getClass().getResource("/TheElementarium/elements/elementGraphics/" + imageName);
            if (imgURL != null) {
                currentGroupImage = ImageIO.read(imgURL);
            } else {
                currentGroupImage = null;
                System.err.println("Group image file not found: " + imageName);
            }
        } catch (Exception e) {
            System.err.println("Could not load group image: " + e.getMessage());
            currentGroupImage = null;
        }

        Color baseColor = ElementGUI.getGroupColor(element.getGroup());
        setBackground(ElementGUI.lightenColor(baseColor, 0.5));

        setVisible(true);
        revalidate();
        repaint();
    }

    public void updateLayout(int panelWidth) {
        int contentPadding = 24;
        int maxTextWidth = panelWidth - contentPadding;

        int imgWidth = maxTextWidth;
        int imgHeight = (int) (imgWidth * 0.42);
        lblImage.setPreferredSize(new Dimension(imgWidth, imgHeight));
        lblImage.setMaximumSize(new Dimension(imgWidth, imgHeight));
        
        txtDetails.setSize(maxTextWidth, Short.MAX_VALUE);
        txtApp.setSize(maxTextWidth, Short.MAX_VALUE);
        txtDetails.setPreferredSize(new Dimension(maxTextWidth, txtDetails.getUI().getPreferredSize(txtDetails).height));
        txtApp.setPreferredSize(new Dimension(maxTextWidth, txtApp.getUI().getPreferredSize(txtApp).height));
    }
}