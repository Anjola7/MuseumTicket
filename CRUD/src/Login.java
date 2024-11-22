import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JTextField emriTextField, mbiemriTextField, idTextField;

    public Login() {
        setTitle("Faqja e Regjistrimit");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        //Vendosja e backgroundit 
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //Vendosim fotografin dhe permasat perkatese
                ImageIcon originalIcon = new ImageIcon("C:\\Users\\CTS\\Downloads\\castle.jpg");
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                g.drawImage(scaledIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        add(backgroundPanel);
        //Vendosim titullin
        JLabel titleLabel = new JLabel("Regjistrohu");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setBounds(170, 30, 100, 30);
        backgroundPanel.add(titleLabel);
        //Krijojme labels perkatese
        String[] labels = {"Emri:", "Mbiemri:", "Numri i ID:"};
        int y = 100;
        for (String labelText : labels) {
            JLabel label = new JLabel(labelText);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBounds(50, y, 80, 20);
            backgroundPanel.add(label);
            y += 30;
        }

        emriTextField = new JTextField(20);
        emriTextField.setBounds(150, 100, 200, 20);
        backgroundPanel.add(emriTextField);

        mbiemriTextField = new JTextField(20);
        mbiemriTextField.setBounds(150, 130, 200, 20);
        backgroundPanel.add(mbiemriTextField);

        idTextField = new JTextField(20);
        idTextField.setBounds(150, 160, 200, 20);
        backgroundPanel.add(idTextField);
        //Krijojme butonat
    

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(150, 220, 100, 30);
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (emriTextField.getText().isEmpty() || mbiemriTextField.getText().isEmpty() || idTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ploteso fushat e kerkuara!");
                } else {
                    String emri = emriTextField.getText();
                    String mbiemri = mbiemriTextField.getText();
                    String id = idTextField.getText();
                   

                    // Krijoni lidhjen me bazën e të dhënave
                    String url = "jdbc:mysql://localhost:3308/myDB";
                    String perdoruesi = "root";
                    String fjalekalimi = "root";
                    try (Connection lidhja = DriverManager.getConnection(url, perdoruesi, fjalekalimi)) {
                        // Përgatiti një kërkesë SQL për të verifikuar kredencialet
                        String query = "SELECT * FROM perdoruesit WHERE emri = ? AND mbiemri = ? AND id = ? ";
                        PreparedStatement ps = lidhja.prepareStatement(query);
                        ps.setString(1, emri);
                        ps.setString(2, mbiemri);
                        ps.setString(3, id);
                   
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            // Kredencialet e verifikuara janë të sakta, hapni faqen e re
                            new Muzeumet().setVisible(true);
                            dispose(); // Mbyllja e faqes së loginit
                        } else {
                            JOptionPane.showMessageDialog(null, "Kredencialet e gabuara!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Gabim në lidhje me bazën e të dhënave!");
                    }
                }
            }
        });
        backgroundPanel.add(continueButton);
    }

    //actionPerformed(ActionEvent e) është një metodë që përdoret për 
    //të definuar veprimet që duhet të ndodhin kur një ngjarje (event) si
    //shtypja e një butoni. Ndërsa metoda main(String[] args) është metoda hyrëse (entry point) e
    //aplikacionit, e cila fillon ekzekutimin e programit.
    public void actionPerformed(ActionEvent e) {
        // Do something
    }

    public static void main(String[] args) {
        //kodi i dhënë në brendësi të run() do të ekzekutohet në një thread të veçantë
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
