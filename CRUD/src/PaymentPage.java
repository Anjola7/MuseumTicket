import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentPage {
    // Metoda për të krijuar lidhjen me bazën e të dhënave
    public static Connection connect() throws SQLException {
        // Parametrat për lidhjen me bazën e të dhënave
        String url = "jdbc:mysql://localhost:3308/mydb"; // Emri i bazës së të dhënave "perdoruesit"
        String perdoruesi = "root"; // Ndryshoni perdoruesin e bazës së të dhënave sipas nevojës
        String fjalekalimi = "root"; // Ndryshoni fjalëkalimin e bazës së të dhënave sipas nevojës

        // Krijo lidhjen me bazën e të dhënave
        Connection lidhja = DriverManager.getConnection(url, perdoruesi, fjalekalimi);
        System.out.println("Lidhja me bazën e të dhënave u krye me sukses!");
        return lidhja;
    }

    public void initialize() {
        // Krijimi i dritares JFrame
        JFrame frame = new JFrame("Faqja e bankes");

        // Vendosja e madhësisë së dritares
        int frameWidth = 800;
        int frameHeight = 600;
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(true); 
        frame.setLocationRelativeTo(null);

        // Vendosja e opsionit për mbyllje të aplikacionit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Vendosja e fotografise si background
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\CTS\\Downloads\\credins.jpg");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH); // Zvogëlimi në përmasat e dritares
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Krijimi i një JLabel për sfondin
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, frameWidth, frameHeight); // Vendosja e pozicionit dhe madhësisë së sfondit

        // Krijimi i një shtrese të panelit për të vendosur elementët mbi sfondin
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        // Shtimi i titullit "Pagesa" në panel
        JLabel titleLabel = new JLabel("Pagesa");
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(frameWidth / 2 - 50, 30, 100, 30); // Vendosja e pozicionit dhe madhësisë së titullit

        // Shtimi i fushave për emrin, mbiemrin, numrin e ID-së, numrin e kartës së kreditit
        JLabel nameLabel = new JLabel("Emri:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setBounds(100, 100, 80, 20);
        layeredPane.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(200, 100, 200, 20);
        layeredPane.add(nameField);

        JLabel surnameLabel = new JLabel("Mbiemri:");
        surnameLabel.setForeground(Color.WHITE);
        surnameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        surnameLabel.setBounds(100, 150, 80, 20);
        layeredPane.add(surnameLabel);

        JTextField surnameField = new JTextField();
        surnameField.setBounds(200, 150, 200, 20);
        layeredPane.add(surnameField);

        JLabel idLabel = new JLabel("Nr ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setBounds(100, 200, 80, 20);
        layeredPane.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(200, 200, 200, 20);
        layeredPane.add(idField);

        JLabel creditCardLabel = new JLabel("Nr Kartës së Kreditit:");
        creditCardLabel.setForeground(Color.WHITE);
        creditCardLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        creditCardLabel.setBounds(100, 250, 150, 20);
        layeredPane.add(creditCardLabel);

        JTextField creditCardField = new JTextField();
        creditCardField.setBounds(250, 250, 200, 20);
        layeredPane.add(creditCardField);

        // Shtimi i butonit "Paguaj"
        JButton payButton = new JButton("Paguaj");
        payButton.setBounds(250, 300, 100, 30);
        layeredPane.add(payButton);

        // Shtimi i butonit "Anulo"
        JButton cancelButton = new JButton("Anulo");
        cancelButton.setBounds(370, 300, 100, 30);
        layeredPane.add(cancelButton);

        // Shtimi i elementeve në shtresën e panelit
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER); // Shtimi i titullit në shtresën e sipërme
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // Shtimi i sfondit në shtresën e fundit

        // Vendosja e  panelit në dritare
        frame.add(layeredPane);

        // Bërja e dritares dukshme
        frame.setVisible(true);

        // ActionListener për butonin "Paguaj" 
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lidhja me bazën e të dhënave
                    Connection lidhja = connect();

                    // Kontrolli i plotesimit të fushave
                    boolean fieldsCompleted = !nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !idField.getText().isEmpty() && !creditCardField.getText().isEmpty();

                    // Nëse fushat nuk janë të plotësuar, shfaq një mesazh
                    if (!fieldsCompleted) {
                        JOptionPane.showMessageDialog(null, "Ploteso fushat e kerkuara!");
                    } else {
                        // Verifikoni në bazën e të dhënave nëse klienti ekziston
                        String query = "SELECT * FROM perdoruesit WHERE emri = ? AND mbiemri = ? AND id = ? AND nr_kartes = ?";
                        PreparedStatement pst = lidhja.prepareStatement(query);
                        pst.setString(1, nameField.getText());
                        pst.setString(2, surnameField.getText());
                        pst.setString(3, idField.getText());
                        pst.setString(4, creditCardField.getText());
                        ResultSet rs = pst.executeQuery();

                        // Nëse klienti ekziston, kryej pagesën, në të kundërt shfaq një mesazh
                        if (rs.next()) {
                            // Pagesa u krye!
                            JOptionPane.showMessageDialog(null, "Pagesa u krye!");
                            frame.dispose(); // Mbylle dritaren e pagesës
                        } else {
                            JOptionPane.showMessageDialog(null, "Kredencialet jane te gabuara");
                        }

                        // Mbylle ResultSet, PreparedStatement dhe lidhjen me bazën e të dhënave
                        rs.close();
                        pst.close();
                    }
                    // Mbylle lidhjen me bazën e të dhënave
                    lidhja.close();
                } catch (SQLException ex) {
                    // Kapeni dhe printoni gabimin nëse ndodh një problem gjatë lidhjes
                    System.err.println("Gabim gjatë lidhjes me bazën e të dhënave: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Gabim në lidhje me bazën e të dhënave!");
                }
            }
        });

        // ActionListener për butonin "Anulo"
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Jeni të sigurtë që doni të anuloni veprimin?", "Konfirmo", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Veprimi u anulua.");
                }
            }
        });
    }

    //Ruan radhen e ekzekutimit te threadeve , qe mos kemi perplasje
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PaymentPage().initialize();
            }
        });
    }
}
