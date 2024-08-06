package day;

import java.sql.*;

public class C02_ReadData {
    /*
        ResultSet ve Cursor Mantığı:

        ResultSet: Java'da, ResultSet nesnesi bir sorguya yanıt olarak bir veritabanından
                   alınan satırların kümesini temsil eder.

        Cursor: Bir cursor, ResultSet'in geçerli satırını işaret eden dahili bir işaretçidir.
                Başlangıçta ilk satırın öncesindeki bir konumu işaret eder.

        Cursor Hareketi:
        İleri Hareket: Cursor, ResultSet'in satırları arasında ileri hareket eder.
                       İlk satırın öncesinden başlar ve next() yöntemi kullanılarak
                       ardışık satırlara sırayla hareket edebilir.

        Mutlak Hareket: Cursor ayrıca absolute(int row) yöntemi kullanılarak doğrudan
                        belirli bir satıra da hareket edebilir. Satırlar 1'den başlayarak numaralandırılır.
                        Bunu yapabilmek için aşağıdaki kodu yazıp cursoru scroll edebilir hale getirmeliyiz.

                         "createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)"

        Göreli Hareket: Göreli hareket, cursor'un geçerli konumuna göre ileriye veya geriye
                        belirli sayıda satır hareket etmesine olanak tanır, relative(int rows) yöntemi kullanarak.

        Verilerin Alınması:

        Cursor istenen satıra hareket ettikten sonra, o satırdaki veriler çeşitli get yöntemleri
           kullanılarak alınabilir, örneğin getInt(), getString(), vb., sütun indeksini veya sütun adını belirterek.

        ResultSet Üzerinde Yineleme:

        ResultSet üzerinde yineleme yapmak, cursor'un satırları arasında hareket etmesini içerir.
        Tipik olarak, ResultSet'teki tüm satırları yinelemek için next() yöntemi ile bir while döngüsü kullanılır.
        Döngü, next() false döndürene kadar devam eder, bu da daha fazla satır olmadığını gösterir.
         */

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://localhost:3306/javacan";      // format kullanılan DBMS göre değişir, bu MySql için kullanılılan format
        String user="root";
        String password="1234";
        String query = "SELECT * FROM personel;";

        // 1- Create Connection
        Connection conn = DriverManager.getConnection(url,user,password);
        // 2- Create Statement/Query
        Statement st =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); // serbestçe scroll yapabilirim
        // 3- Execute Statement/Query
        // 4- Store results in ResultSet
        ResultSet rs = st.executeQuery(query);

        // Birinci recordun birinci sutununu okuyalım
        rs.next();
        int id = rs.getInt(1); // 1111
        int id2 = rs.getInt("personel_id");

        System.out.println("id = " + id);
        System.out.println("id2 = " + id2);
/*
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
        System.out.println(rs.getInt(4));

 */

        // ikinci recordun tüm sutunlarını okuyun
        System.out.println("************* ikinci recordun tüm sutunlarını okuyun **************");
        rs.next();
        System.out.println(rs.getInt(1) + "   "+
                           rs.getString(2)+ "   "+
                           rs.getString("meslek")+ "   "+
                           rs.getInt(4)+ "   "+
                           rs.getInt(5)+ "   "+
                           rs.getInt(6)+ "   "
                );



        // Birinci recordun personel_isim değerini yazdırınız
        System.out.println("********** Birinci recordun personel_isim değerini yazdırınız ***********");
        rs.previous();
        System.out.println("rs.getString(\"personel_isim\") = " + rs.getString("personel_isim"));
        // 4. recorda ait tum datayı yazdırın
        System.out.println("********* 4. recorda ait tum datayı yazdırın *********");
        rs.absolute(4); // 4. satıra gittim
        System.out.println(rs.getInt(1) + "   "+
                rs.getString(2)+ "   "+
                rs.getString("meslek")+ "   "+
                rs.getInt(4)+ "   "+
                rs.getInt(5)+ "   "+
                rs.getInt(6)+ "   "
        );

        rs.relative(2); // 4. satırda idi bu yüzden 2 satır ileri gider yani 6. satıra gider
        System.out.println(rs.getString(2));

        // Tum personelin isimlerini yazdırın
        System.out.println("*************** Tum personelin isimlerini yazdırın*************");
        rs.absolute(0);
        while(rs.next()){
            System.out.println(rs.getString(2));
        }

        // Tüm personelin maas larını yazdırın
        System.out.println("*************** Tum personelin maas larını yazdırın*************");
        rs.absolute(0);
        while(rs.next()){
            System.out.println(rs.getString("maas"));
        }
        // Tüm tabloyu yazdırın  _ consolun düzgün görülmesi için kullanılabilir --> "%-6d  %-10s %-10s %-10s %-10s %-6d\n"
        rs.absolute(0);
        while (rs.next()){
           /* System.out.println(rs.getInt(1) + "   "+
                    rs.getString(2)+ "   "+
                    rs.getString("meslek")+ "   "+
                    rs.getInt(4)+ "   "+
                    rs.getInt(5)+ "   "+
                    rs.getInt(6)+ "   "
            );
            */
            System.out.printf("%-6d  %-10s %-10s %-10s %-10s %-6d\n",rs.getInt(1),
                                                                     rs.getString(2),
                                                                     rs.getString(3),
                                                                     rs.getInt(4),
                                                                     rs.getInt(5),
                                                                     rs.getInt(6)
            );
        }

        // 5- Close Connection
        st.close();
        conn.close();


    }

}
