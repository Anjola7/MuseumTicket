import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static void main(String[] args) {
        Connection con = null;
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Bejme lidhjen me databazen
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/myDb", "root", "root");
            
            // Nqs lidhja eshte e sukseshme shfaqet mesazhi
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
          
            e.printStackTrace();
        } catch (SQLException e) {
          
            e.printStackTrace();
        } finally {
            
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
