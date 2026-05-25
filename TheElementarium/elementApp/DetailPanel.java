//eto yung detail panel
//eto yung lumilitaw pag naka hover sa element block

package TheElementarium.elementApp;

//imports
import TheElementarium.elements.Element; //need to import since dito kukuha ng element info, see Element.java
import java.awt.*; //abstract window toolkit, for GUI to, meron siyang classes and methods na for GUI
import java.net.URL; //for handling image
import javax.imageio.ImageIO; //for handling image, para maload yung image from the other files ng project
import javax.swing.*; //awt is heavy-weight, eto naman lightweight. kung awt platform-dependent, eto independent. eto yung ginagamit for buttons and scrollbar

public class DetailPanel extends JPanel { //may word na extends to indicate na magiinherit siya sa JPanel, ibig sabihin, pede siya maghold ng iba pang UI elements.
    //ang JPanel ay part ng Java Swing and ginagamit to as container for a group of components.
    //for example, dito sa program naten, ang JPanel for detailpanel nagcocontain ng element details & applications

    //initialization ng mga gagamitin na Java Swing components
    //gumamit dito ng encaps since may keyword na private, meaning, yung mga to dito lang sa class na to magagamit
    private JLabel lblImage, lblName, lblGroup; //JLabel- object that can contain either text or image
    private JTextArea txtDetails, txtApp; //JTextArea, mostly textual paragraph dito
    private Image currentGroupImage; //eto yung image na lilitaw sa detail panel

    public DetailPanel() { //constructor
        setLayout(new BorderLayout(5, 5)); //may ganito para maayos and iisang size yung panel, 5 pixel gap sa x and y niya
        setBackground(ElementGUI.lightenColor(ElementGUI.HEADER_LINE, 0.3)); //sinet dito yung bg color for detail panel using a helper method na galing sa ElementGUI (see ElementGUI.java) to see
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); //may invisible border here para magstay ung components sa loob ng panel
        setVisible(false); //pag sinet mo to ng true, lilitaw yung detailpanel kahit di pindutin or hover yung element block, kaya naka false ito para invisible muna sha then saka siya lilitaw
        initComponents();} //call helper method to for UI hierarchy, kumbaga parang sa canva, nakalayer yung graphic elemnts para di lumitaw sa unahan yung dapat nasa likuran

    private void initComponents() { //initializations for UI
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
                    g2d.dispose();}
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

        JScrollPane scrollPane = new JScrollPane(textContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);}

    @SuppressWarnings("UseSpecificCatch")
    public void populateData(Element element) {
        lblName.setText(element.getSymbol() + " - " + element.getName());
        lblGroup.setText(element.getGroup());

        txtDetails.setText(String.format("• Atomic Number: %d\n• Atomic Mass: %.3f u\n• Configuration: %s", element.getAtomicNumber(), element.getAtomicWeight(), element.getElectronConfig()));
        txtApp.setText("Applications:\n" + element.getApplications());

        try {
            String imageName = element.getGroupImageName();
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

        Color baseColor = element.getGroupColor();
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
