import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton startButton;
    private JLabel logoLabel;
    private Image backgroundImage;
    private ImageIcon startIcon;
    private ImageIcon logoIcon;

    int frameWidth = 360;
    int frameHeight = 640;
    int startIconWidth = 120;
    int startIconHeight = 40;
    int logoWidth = 180;
    int logoHeight = 45;

    public MainMenu() {
        setTitle("Flappy Bird");
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // load gambar
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("assets/logo.png")).getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH));
        startIcon = new ImageIcon(new ImageIcon(getClass().getResource("assets/start.png")).getImage().getScaledInstance(startIconWidth, startIconHeight, Image.SCALE_SMOOTH));

        // panel background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        // label logo
        logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds((frameWidth - logoWidth)/2, (frameHeight - logoHeight)/4, logoWidth, logoHeight);
        panel.add(logoLabel);

        // start button
        startButton = new JButton(startIcon);
        startButton.setBounds((frameWidth - startIconWidth)/2, (frameHeight - startIconHeight)/2, startIconWidth, startIconHeight);
        startButton.setBorderPainted(false); // Hilangkan border
        startButton.setContentAreaFilled(false); // Hilangkan background
        startButton.setFocusPainted(false); // Hilangkan outline fokus

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        panel.add(startButton);

        add(panel);
    }

    private void startGame() {
        // Tutup menu
        this.dispose();

        // Buka game
        JFrame gameFrame = new JFrame("Flappy Bird Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(360, 640);
        gameFrame.setLocationRelativeTo(null);

        FlappyBird game = new FlappyBird();
        gameFrame.add(game);
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}