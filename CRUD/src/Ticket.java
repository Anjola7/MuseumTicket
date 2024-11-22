import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ticket extends JFrame {
    private double totalAmount = 0.0; // Shuma totale e çmimit të biletave

    public Ticket() {

        setTitle("Blerja e biletave");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Vendosim imazhin si background
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\CTS\\Downloads\\museemum.jpg");
        int imageWidth = originalIcon.getIconWidth();
        int imageHeight = originalIcon.getIconHeight();

        JLabel backgroundLabel = new JLabel(originalIcon);
        backgroundLabel.setBounds(0, 0, imageWidth, imageHeight);

        JPanel layeredPane = new JPanel();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(imageWidth, imageHeight));
        // Vendosim titullin
        JLabel titleLabel = new JLabel("Blerja e biletave");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(imageWidth / 2 - 100, 30, 200, 30);
        layeredPane.add(titleLabel);
        // Krijojme label-at perkates dhe rregullojme permasen , ngjyren shkrimin
        String[] labels = {"Emri:", "Mbiemri:", "Mosha:", "Email:", "Numri i telefonit:"};
        int y = 80;
        JTextField[] textFields = new JTextField[labels.length]; // Array për të ruajtur fushat e tekstit

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBounds(50, y, 120, 20);
            y += 30;
            JTextField textField = new JTextField(20);
            textField.setBounds(180, y - 30, 300, 20);
            layeredPane.add(label);
            layeredPane.add(textField);
            textFields[i] = textField;
        }
        // Krijojme butonat
        JButton paguajButton = new JButton("Paguaj");
        paguajButton.setBounds(240, y, 100, 30);
        layeredPane.add(paguajButton);

        JButton anuloButton = new JButton("Anulo");
        anuloButton.setBounds(360, y, 100, 30);
        layeredPane.add(anuloButton);

        layeredPane.add(backgroundLabel);

        add(layeredPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        // Vendosim ActionListener per te mundesuar veprimin pas klikimit te
        paguajButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Kontrolli i plotesimit të fushave
                boolean fieldsCompleted = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        fieldsCompleted = false;
                        break;
                    }
                }
                // Nëse fushat nuk janë të plotësuar, shfaq një mesazh
                if (!fieldsCompleted) {
                    JOptionPane.showMessageDialog(null, "Ploteso fushat e kerkuara!");
                } else {
                    String emri = textFields[0].getText();
                    String mbiemri = textFields[1].getText();
                    String mosha = textFields[2].getText();
                    String email = textFields[3].getText();
                    String numriTelefonit = textFields[4].getText();

                    if (verifyCredentials(emri, mbiemri, mosha, email, numriTelefonit)) {
                        JOptionPane.showMessageDialog(null, "Duke kaluar në faqen e bankës...");
                        new PaymentPage().initialize();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Kredencialet e gabuara!");
                    }
                }
            }
        });
        // Butoni anulo
        anuloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Jeni të sigurtë që doni të anuloni veprimin?", "Konfirmo", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Veprimi u anulua.");
                    dispose();
                }
            }
        });
    }

    private boolean verifyCredentials(String emri, String mbiemri, String mosha, String email, String numri) {
        // Lidhja me bazën e të dhënave
        String url = "jdbc:mysql://localhost:3308/myDB";
        String perdoruesi = "root";
        String fjalekalimi = "root";
        try (Connection lidhja = DriverManager.getConnection(url, perdoruesi, fjalekalimi)) {
            // Përgatitja e kërkesës SQL për të verifikuar kredencialet
            String query = "SELECT * FROM perdoruesit WHERE emri = ? AND mbiemri = ? AND mosha = ? AND email = ? AND numri= ?";
            PreparedStatement ps = lidhja.prepareStatement(query);
            ps.setString(1, emri);
            ps.setString(2, mbiemri);
            ps.setString(3, mosha);
            ps.setString(4, email);
            ps.setString(5, numri);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Kthen true nëse gjendet një përdorues me kredencialet e dhëna
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gabim në lidhje me bazën e të dhënave!");
            return false; // Kthen false në rast se ka ndodhur një gabim në lidhje me bazën e të dhënave
        }
    }

    // Ruan radhen e ekzekutimit te threadeve , qe mos kemi perplasje
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Ticket();
            }
        });
    }
}
