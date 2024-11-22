import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpForm extends JFrame implements ActionListener {
    private JTextField txtID, txtName, txtSurname, txtEmail, txtAge, txtPhoneNumber, txtCardNumber;
    private JButton btnSignUp, btnLogin;
    private Connection conn;

    public SignUpForm() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/myDb", "root", "root");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gabim në lidhjen me bazën e të dhënave!");
            System.exit(1);
        }

        setTitle("Regjistrimi");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon originalIcon = new ImageIcon("C:\\Users\\CTS\\Downloads\\castle.jpg");
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                g.drawImage(scaledIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridLayout(9, 2));
        add(backgroundPanel, BorderLayout.CENTER);

        JLabel lblID = new JLabel("ID:");
        Font fontID = lblID.getFont();
        lblID.setFont(new Font(fontID.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblID);

        txtID = new JTextField();
        backgroundPanel.add(txtID);

        JLabel lblName = new JLabel("Emri:");
        Font font = lblName.getFont();
        lblName.setFont(new Font(font.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblName);

        txtName = new JTextField();
        backgroundPanel.add(txtName);

        JLabel lblSurname = new JLabel("Mbiemri:");
        Font fontSurname = lblSurname.getFont();
        lblSurname.setFont(new Font(fontSurname.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblSurname);

        txtSurname = new JTextField();
        backgroundPanel.add(txtSurname);

        JLabel lblEmail = new JLabel("Email:");
        Font fontEmail = lblEmail.getFont();
        lblEmail.setFont(new Font(fontEmail.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblEmail);

        txtEmail = new JTextField();
        backgroundPanel.add(txtEmail);

        JLabel lblAge = new JLabel("Mosha:");
        Font fontAge = lblAge.getFont();
        lblAge.setFont(new Font(fontAge.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblAge);

        txtAge = new JTextField();
        backgroundPanel.add(txtAge);

        JLabel lblPhoneNumber = new JLabel("Numri i telefonit:");
        Font fontPhoneNumber = lblPhoneNumber.getFont();
        lblPhoneNumber.setFont(new Font(fontPhoneNumber.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblPhoneNumber);

        txtPhoneNumber = new JTextField();
        backgroundPanel.add(txtPhoneNumber);

        JLabel lblCardNumber = new JLabel("Numri i kartës:");
        Font fontCardNumber = lblCardNumber.getFont();
        lblCardNumber.setFont(new Font(fontCardNumber.getFontName(), Font.BOLD, 32));
        backgroundPanel.add(lblCardNumber);

        txtCardNumber = new JTextField();
        backgroundPanel.add(txtCardNumber);

        btnSignUp = new JButton("Regjistrohu");
        btnSignUp.setFont(new Font("Arial", Font.BOLD, 14));
        btnSignUp.setBackground(Color.BLACK);
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.addActionListener(this);
        backgroundPanel.add(btnSignUp);

        btnLogin = new JButton("Log in");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(this);
        backgroundPanel.add(btnLogin);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignUp) {
            int id = Integer.parseInt(txtID.getText());
            String name = txtName.getText();
            String surname = txtSurname.getText();
            String email = txtEmail.getText();
            int age = Integer.parseInt(txtAge.getText());
            String phoneNumber = txtPhoneNumber.getText();
            String cardNumber = txtCardNumber.getText();

            addUser(id, name, surname, email, age, phoneNumber, cardNumber);

            clearFields();

            JOptionPane.showMessageDialog(this, "Përdoruesi u regjistrua me sukses!");
        } else if (e.getSource() == btnLogin) {
            new Login().setVisible(true);
            dispose();
        }
    }

    private void addUser(int id, String name, String surname, String email, int age, String phoneNumber, String cardNumber) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO perdoruesit (id, emri, mbiemri, email, mosha, numri, nr_kartes) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, email);
            stmt.setInt(5, age);
            stmt.setString(6, phoneNumber);
            stmt.setString(7, cardNumber);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gabim gjatë shtimit të përdoruesit në bazën e të dhënave!");
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtSurname.setText("");
        txtEmail.setText("");
        txtAge.setText("");
        txtPhoneNumber.setText("");
        txtCardNumber.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpForm::new);
    }
}
