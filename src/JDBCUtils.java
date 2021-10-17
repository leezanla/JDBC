import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCUtils {
    private static DataSource source;
    static{
        try {
            Properties pros = new Properties();
            //读取druid.properties中的配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            pros.load(is);

            source = DruidDataSourceFactory.createDataSource(pros); //创建数据库连接池
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() { //获取数据库连接池中的连接
        Connection conn = null;
        try {
            conn = source.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {  //关闭数据库的连接
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static void dbutilsClose() {

    }




//    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
//        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
//        Properties pros = new Properties();
//        pros.load(is);
//        String user = pros.getProperty("jdbc.username");
//        String password = pros.getProperty("jdbc.password");
//        String url = pros.getProperty("jdbc.url");
//        String driverClass = pros.getProperty("jdbc.driverClassName");
//        Class.forName(driverClass);
//        Connection conn = DriverManager.getConnection(url, user, password);
//        return conn;
//    }
}
