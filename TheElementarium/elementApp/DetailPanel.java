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
        
        //initialize panel to hold image, and element details
        JPanel topContainer = new JPanel(); 
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS)); //y-axis para pa vertical/portrait siya
        topContainer.setOpaque(false); //set to false para di transparent

        lblImage = new JLabel() { //subclass of JLabel, instead na icon/image lang ihohold niya, may sarili siyang logic
            @Override //override when the component needs to rendered (for example, if nagresize yung window etc.)
            protected void paintComponent(Graphics g) { //nag override siya from java.swing.*, specifically java.swing.*
                // protected since yung mga classes ng package na to gagamit din ng paintComponent()
                //matik na icacall to
                super.paintComponent(g); //dito nagpeperform yung Java ng standard clean up (like clearing the bg) bago mag execute/draw yung custom code mo
                if (currentGroupImage != null) { 
                    //yung currentGroupImage naghohold siya ng image object
                    //null means "pointing to nothing", in short, wala pang naka assign na image or yung attempt na iload yung image nag fail
                    //if statement, bale tinatanong niya na "may image ba talaga to?", 
                    //!= means not, so ibig sabihin if may naka assign na image, magproproceed siya sa sunod na codes, if null siya edi skip niya yung mga susunod na codes
                    Graphics2D g2d = (Graphics2D) g.create(); 
                    //Graphics object na nagproprovide ng advanced control sa rendering features
                    //yung rendering features yung mga susunod na codes

                    //sinasabi ng setRenderingHint kung pano idradraw ni Java yung image
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //if naka shrink or enlarge yung image, magiiba din yung pixels to adjust para di mukang pixelated ung image
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); //para maganda quality ng image
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //ginagamit to para smooth yung edges ng mga round shapes or curves sa image, para walang stair-step effect (aliasing), in other words, para di ule mukang pixelated
                    g2d.drawImage(currentGroupImage, 0, 0, getWidth(), getHeight(), this); //ginagamit to para ma force yung image na magmatch sa size ng JLabel, para dynamic pa din kahit resize mo window
                    g2d.dispose();} //memory management, para di magconsume ng sobrang memory yung app, bale di magdradraw ng image if di naman gagamitin
                //for example, di naman hinover yung ibang element blocks, so yung image nila di naman need. Need lang ng image if naka hover or click
            }
        };
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT); //para magcenter yung image
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //para may gray border yung image

        lblName = new JLabel("Name", SwingConstants.CENTER); //initialize yung yung name label, lilitaw yung "Name"
        lblName.setFont(new Font("Arial", Font.BOLD, 22)); //ganto font nung "Name"
        lblName.setForeground(ElementGUI.DARK_BLUE); //eto yung text color (see ElementGUI.java to see color initialization)
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT); //para naka center yung text

        //same lang den sa lblName, iba lang yung text nakalagay
        lblGroup = new JLabel("Group Type", SwingConstants.CENTER);
        lblGroup.setFont(new Font("Arial", Font.ITALIC, 15));
        lblGroup.setForeground(Color.DARK_GRAY);
        lblGroup.setAlignmentX(Component.CENTER_ALIGNMENT);

        //dito na aadd yung image and labels
        //nakaindicate din dito yung spacer areas para mas mukang maayos layout
        topContainer.add(lblImage);
        topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        topContainer.add(lblName);
        topContainer.add(Box.createRigidArea(new Dimension(0, 4)));
        topContainer.add(lblGroup);

        //nainitialize yung text area
        txtDetails = new JTextArea(); //yung text area eto yung element details, yung atomic number, e config, atomic mass
        txtDetails.setEditable(false); //naka false to para di maedit ng user yung text
        txtDetails.setLineWrap(true); //para hindi isang mahabang line yung text, fofollow ng text yung text area, di siya lalagpas
        txtDetails.setWrapStyleWord(true); //same lang den sa setLineWrap
        txtDetails.setFont(new Font("Arial", Font.PLAIN, 14)); //font ng element details
        txtDetails.setOpaque(false); //para di siya transparent

        //same lang den sa txtDetails, pero element applications naman nakalagay
        txtApp = new JTextArea();
        txtApp.setEditable(false);
        txtApp.setLineWrap(true);
        txtApp.setWrapStyleWord(true);
        txtApp.setFont(new Font("Arial", Font.PLAIN, 14));
        txtApp.setOpaque(false);

        //para may lalagyan yung texts
        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS)); //y-axis den para mafollow niya yung detail panel
        textContainer.setOpaque(false);
        textContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        textContainer.add(txtDetails); //para lumitaw yung element details
        textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        textContainer.add(txtApp); //para lumitaw element applications

        //Adds a scrollbar to the text area if the information is too long to fit
        //di naman to masyadong nagamit since nakawordwrap naman yung texts, kaya naka _NEVER yung horizontal scrollbar
        //kaso nga lang ang wordwrap pang horizontal lang so pede lumagpas text vertically
        //in case lang naman na mas dagdagan yung texts, edi mavieview pa din since may vertical scrollbar (AS_NEEDED)
        JScrollPane scrollPane = new JScrollPane(textContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false); //transparent sha, pag nagscroll ka di siya lilitaw bes
        scrollPane.getViewport().setOpaque(false); 
        scrollPane.setBorder(null); //null, walang border

        //need to para ma add sa detail panel
        add(topContainer, BorderLayout.NORTH); //north - sa taas sha ng detail panel
        add(scrollPane, BorderLayout.CENTER);} //center - gitna ng detail panel

    //eto logic for UI
    @SuppressWarnings("UseSpecificCatch") //para macatch ng compiler specific error if meron, @suprress warning since di siya magwawarn na may error
    public void populateData(Element element) { //pag may clinick ka sa periodic table, nagrurun tong method para ma update yung view
        lblName.setText(element.getSymbol() + " - " + element.getName()); //ex. H - Hydrogen, ganto lilitaw sa detail panel
        lblGroup.setText(element.getGroup()); //lilitaw group name ng element

        txtDetails.setText(String.format("• Atomic Number: %d\n• Atomic Mass: %.3f u\n• Configuration: %s", element.getAtomicNumber(), element.getAtomicWeight(), element.getElectronConfig())); //pang spacing yung %d, next line naman \n, lilitaw dito yung element details
        txtApp.setText("Applications:\n" + element.getApplications()); //under ng element details, lilitaw naman applications

        //ginagamit try-catch block for image loading
        //try - tatry hanapin yung image file sa project structure mo based sa binigay na source/path
        //catch - eto lilitaw pag di nagload image
        //under ni try, may if else
        //if - pag nahanap image file, mareread sha and maassign sa currentGroupImage
        //else - pag di nahanap yung image
        //gingagamit to just in case mabura yung file or magka error sa image file
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

        Color baseColor = element.getGroupColor(); //kukunin baseColor ng detail panel through yung getter (to see group color, see Element.java)
        setBackground(ElementGUI.lightenColor(baseColor, 0.5)); //eto yung bg ng detail panel (lightened color ng groupcolor, see ElementGUI.java)
        setVisible(true); //true, para makita sha bes
        revalidate(); //Swing command te, inuutusan niya layout manager na irecalculate yung sizes if may changes sa size
        //nilagay to since maraming changes sa sizes simula pa nung pinaka raw code niya
        //inuupdate din niya yung UI hierarchy
        repaint(); //command din te, pero eto naman uutusan na mag redraw yung components in case n may bagong data, same reason din kay revalidate on why andito sha
    }

    public void updateLayout(int panelWidth) { //responsive design tong method be
        //ginagamit to para if ever na gusto iresize ng user yung window (minimize kunyare) yung mga components ng detail panel magaadjust sila
        int contentPadding = 24; //spacing lang bes
        int maxTextWidth = panelWidth - contentPadding; //eto para di lumagpas text sa labas ng panel
        int imgWidth = maxTextWidth; //para kasize ng image yung width ng text, mas mukang maayos
        int imgHeight = (int) (imgWidth * 0.42); //heignt ng image
        lblImage.setPreferredSize(new Dimension(imgWidth, imgHeight)); //eto size ng image pag naka default yung window
        lblImage.setMaximumSize(new Dimension(imgWidth, imgHeight)); //size ng image pag nakafullscreen, wala naman din pinagbago since default ng window sa periodic table ay fullscreen

        //basta sizes lang to guys para di lang yung container ng texts yung nagresize, dapat yung texts din
        txtDetails.setSize(maxTextWidth, Short.MAX_VALUE);
        txtApp.setSize(maxTextWidth, Short.MAX_VALUE);
        txtDetails.setPreferredSize(new Dimension(maxTextWidth, txtDetails.getUI().getPreferredSize(txtDetails).height));
        txtApp.setPreferredSize(new Dimension(maxTextWidth, txtApp.getUI().getPreferredSize(txtApp).height));
    }
}
