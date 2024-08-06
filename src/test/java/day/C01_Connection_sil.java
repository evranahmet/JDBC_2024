package day;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class C01_Connection_sil {

    public static void main(String[] args) throws SQLException {
       String url = "jdbc:mysql://localhost:3306/javaCan";   // Syntax kullanılan DBMS göre değişir
       String user = "root";
       String password = "1234";

        // 1- Create Connection
        Connection conn = DriverManager.getConnection(url,user,password);
        // 2- Create Statement/Query
        // 3- Execute Statement/Query
        // 4- Store results in ResultSet
        // 5- Close Connection
        conn.close();

    }

}
