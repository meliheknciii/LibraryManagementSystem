import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public static Connection connect() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/kutuphane_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = ""; // WAMP varsayılan

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL bağlantısı başarılı!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MySQL bağlantısı başarısız!");
        }

        return conn;
    }
}
