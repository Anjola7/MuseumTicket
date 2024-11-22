import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Muzeumet extends JFrame {
    private double totalAmount = 0.0; // Shuma totale e çmimit
    private JLabel lblTotalAmount; // Etiketa për shumën përfundimtare të çmimit

    public Muzeumet() {
        setTitle("Rezervo biletat");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        String[] imagePaths = {
            "C:\\Users\\CTS\\Downloads\\image.jpg\\image1.jpg.jpg",
            "C:\\Users\\CTS\\Downloads\\image.jpg\\image2.jpg.jpg",
            "C:\\Users\\CTS\\Downloads\\image.jpg\\image3.jpg.jpg",
            "C:\\Users\\CTS\\Downloads\\image.jpg\\image4.jpg.jpg",
            "C:\\Users\\CTS\\Downloads\\image.jpg\\image5.jpg.jpg"
        };

        for (int i = 0; i < imagePaths.length; i++) {
            ImageIcon imageIcon = new ImageIcon(imagePaths[i]);
            Image scaledImage = imageIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(scaledIcon);
            panel.add(imageLabel);

            String[] buttonLabels = {"Kalaja e Gjirokastres - 2 Euro", "Muzeumet e kalase - 2 Euro", "Muzeu etnografik - 2 Euro", "Shtepia e Ismail Kadares - 2 Euro", "Shtepia e Zekateve - 2 Euro"};
            JButton button = new JButton(buttonLabels[i]);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Kontrollo nëse butoni është klikuar më herët
                    if (!button.isEnabled()) {
                        JOptionPane.showMessageDialog(null, "Keni zgjedhur tashmë këtë opsion!");
                        return;
                    }
                    totalAmount += 2.0;
                    // Rifresko shumën përfundimtare të çmimit në etiketën e përshtatshme
                    lblTotalAmount.setText("Shuma totale: " + totalAmount + " Euro");
                    // Ndërpris veprimin e butonit për të parandaluar klikime të dyfishta
                    button.setEnabled(false);
                }
            });
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);
            panel.add(button);
        }

        // Shtoni etiketën për shumën përfundimtare të çmimit në fund të panelit
        lblTotalAmount = new JLabel("Shuma totale: " + totalAmount + " Euro");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTotalAmount);

        // Shtoni butonin "Paguaj" në fund të panelit
        JButton btnPay = new JButton("Contiune");
        btnPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Shuma totale është paguar: " + totalAmount + " Euro");
                // Pastro shumën përfundimtare të çmimit pas pagesës
                totalAmount = 0.0;
                lblTotalAmount.setText("Shuma totale: " + totalAmount + " Euro");
                // Lejo klikimin e butonave për të bërë rezervime të reja
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof JButton) {
                        comp.setEnabled(true);
                    }
                }
                // Hapja e dritares së pagesës pas pagesës së kryer
                new Ticket().setVisible(true);
            }
        });
        btnPay.setFont(new Font("Arial", Font.BOLD, 16));
        btnPay.setForeground(Color.WHITE);
        btnPay.setBackground(Color.GREEN);
        panel.add(btnPay);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Muzeumet();
            }
        });
    }
}

