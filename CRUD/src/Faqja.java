import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BackgroundPanel extends JPanel {
    private Image image;

    public BackgroundPanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vendosja e imazhit si sfond
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}


public class Faqja {

    public static void main(String[] args) {
        // Krijimi i JFrame
        JFrame frame = new JFrame("Faqja e fillimit");

        // Vendosja e madhësisë së dritares
        frame.setSize(400, 300);

        // Vendosja e opsionit për mbyllje të aplikacionit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Vendosja e nje fotografie si background 
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon("C:\\Users\\CTS\\Downloads\\gjirokastra.jpg").getImage());
        panel.setLayout(new BorderLayout());

        // Titulli
        JLabel titleLabel = new JLabel("MIRESERDHET NE GJIROKASTER");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE); 
        panel.add(titleLabel, BorderLayout.NORTH);

        // Shtimi i një rreshti për tekstin "Rezervo biletën" 
        JLabel reserveLabel = new JLabel("REZERVONI BILETEN TUAJ");
        reserveLabel.setHorizontalAlignment(JLabel.CENTER);
        reserveLabel.setFont(new Font("Arial", Font.BOLD, 32));// ndryshimi i madhësisë së shkrimit
        reserveLabel.setForeground(Color.WHITE);
        panel.add(reserveLabel, BorderLayout.CENTER);

        // Krijimi i një paneli të vogël për butonat Log In dhe Sign Up
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); //Percakton nqs paneli eshte transparent apo jo
        buttonPanel.setLayout(new FlowLayout());

        // Krijimi i butonit Continue dhe shtimi i një ActionListener i cili percakton veprimin qe ndodh pas klikimit
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Kalojme te klasa SignUpForm pas klikimit te butonit 
                new SignUpForm().setVisible(true);
            }
        });
        continueButton.setFont(new Font("Arial", Font.PLAIN, 24)); // ndryshimi i madhësisë së shkrimit
        continueButton.setForeground(Color.WHITE); // ndryshimi i ngjyrës së tekstit
        continueButton.setBackground(Color.BLACK); // vendosja e sfondit të zi
        buttonPanel.add(continueButton);

        // Shtimi i panelit të butonave në panelin kryesor
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Shtimi i panelit të konfiguruar si sfond në dritare
        frame.setContentPane(panel);

        // Bërja e dritares dukshme
        frame.setVisible(true);
    }

    // Klasa për faqen e log-init
    static class LoginPage extends JFrame {
        public LoginPage() {
            setTitle("Faqja e Loginit");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            setLocationRelativeTo(null);
        }
    }
}


   