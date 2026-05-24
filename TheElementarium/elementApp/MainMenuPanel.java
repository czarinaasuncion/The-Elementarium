package TheElementarium.elementApp;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    private Image logoImage;
    @SuppressWarnings("UseSpecificCatch")
    public MainMenuPanel(ActionListener onSeeTableClicked, ActionListener onLabClicked) {
        setBackground(ElementGUI.REGAL_NAVY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 20, 6, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        try {
            URL logoURL = getClass().getResource("/TheElementarium/elements/elementGraphics/logo.png");
            if (logoURL != null) {
                logoImage = ImageIO.read(logoURL);
            }
        } catch (Exception e) {
            System.err.println("Could not load main menu logo: " + e.getMessage());
        }

        JComponent logoComponent;
        if (logoImage != null) {
            logoComponent = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.drawImage(logoImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            };
            logoComponent.setPreferredSize(new Dimension(360, 360));
            logoComponent.setMinimumSize(new Dimension(360, 360)); 
            logoComponent.setOpaque(false);
        } else {
            logoComponent = new JLabel("THE ELEMENTARIUM");
            ((JLabel) logoComponent).setFont(new Font("SansSerif", Font.BOLD, 54));
            logoComponent.setForeground(ElementGUI.LEMON_CHIFFON); 
        }
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 20, 6, 20); 
        add(logoComponent, gbc);
        gbc.insets = new Insets(6, 20, 6, 20);
        
        JLabel subtitleLabel = new JLabel("Interactive Periodic Table & Learning Tool");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setForeground(ElementGUI.LEMON_CHIFFON);
        gbc.gridy = 1;
        add(subtitleLabel, gbc);
        gbc.gridy = 2;
        add(Box.createVerticalStrut(15), gbc); 
        gbc.gridy = 3;
        add(createButtonWrapper("See Periodic Table", onSeeTableClicked), gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 6, 20); 
        add(createButtonWrapper("Elements Lab", onLabClicked), gbc);
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(Box.createVerticalGlue(), gbc);
    }
    
    private JPanel createButtonWrapper(String text, ActionListener actionListener) {
        Dimension containerSize = new Dimension(380, 78);
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setPreferredSize(containerSize);
        wrapper.setMinimumSize(containerSize);
        wrapper.setOpaque(false);
        JButton button = createMenuButton(text);
        button.addActionListener(actionListener);
        wrapper.add(button);
        return wrapper;
    }
    
    private JButton createMenuButton(String text) {
        Dimension baseSize = new Dimension(340, 65);
        Dimension hoverSize = new Dimension(360, 72);

        JButton button = new JButton(text) {
            private boolean isHovered = false;
            private boolean isPressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHovered = true;
                        setPreferredSize(hoverSize);
                        revalidate();
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHovered = false;
                        isPressed = false;
                        setPreferredSize(baseSize);
                        revalidate();
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            isPressed = true;
                            repaint();
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        isPressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isPressed) {
                    g2.setColor(getBackground().darker());
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isHovered || isPressed) {
                    Color glowColor = isPressed ? Color.WHITE : new Color(255, 255, 255, 200);
                    g2.setStroke(new BasicStroke(7.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 40));
                    g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 20, 20);
                    g2.setStroke(new BasicStroke(4.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 90));
                    g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 20, 20);
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 240));
                    g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 20, 20);
                } else {
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.setColor(getBackground().darker());
                    g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 20, 20);
                }
                g2.dispose();
            }
        };

        button.setPreferredSize(baseSize);
        button.setMinimumSize(baseSize); 
        button.setFont(new Font("SansSerif", Font.BOLD, 20)); 
        button.setBackground(ElementGUI.LEMON_CHIFFON);
        button.setForeground(ElementGUI.REGAL_NAVY);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }
}