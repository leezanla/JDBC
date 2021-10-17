import com.alibaba.druid.util.JdbcUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestDruid {

    @Test
    public void testConnect() throws Exception {
        System.out.println(JDBCUtils.getConnection());
    }


    @Test
    public void testQuery() throws SQLException, IOException, ClassNotFoundException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from book_name where id = ?";
        BeanHandler<Book> handler = new BeanHandler<>(Book.class);
        Book book = runner.query(conn, sql ,handler, 4);
        System.out.println(book);
        JDBCUtils.close(conn);//结束的时候不要忘记关闭，（不然会造成内存的泄漏）
    }

    /**
     * BeanListHandler集合的测试
     * @throws SQLException
     */
    @Test
    public void testQueryList() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from book_name";
        Connection conn = JDBCUtils.getConnection();
        BeanListHandler<Book> bookList = new BeanListHandler<>(Book.class);//实例化一个BeanListHandler集合对象
        List<Book> list = runner.query(conn, sql, bookList);
        list.forEach(System.out::println);//打印list集合当中的每条数据
        JDBCUtils.close(conn);//结束的时候不要忘记关闭，（不然会造成内存的泄漏）
    }

    @Test
    public void testQueryMapListHandler() {
        Connection conn = JDBCUtils.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "select * from book_name";
            MapListHandler mapList = new MapListHandler();
            List<Map<String, Object>> query = runner.query(conn, sql, mapList);
            query.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn);
        }
    }



    @Test
    public void testQueryScalarHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        ScalarHandler<Object> handler = new ScalarHandler<>();

//        String sql = "select count(*) from book_name";
//        long count = (long)runner.query(conn, sql, handler)

        String sql = "select Max(id) from book_name";
        int count = (int)runner.query(conn, sql, handler);
        System.out.println("表中有" + count + "条记录");
    }
    @Test
    public void testUpdate() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from book_name where id = ?";
        try {
            int count = runner.update(conn, sql, 4);
            System.out.println("有" + count + "记录改变了");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn);
        }
        DbUtils.close(conn);
        DbUtils.closeQuietly(conn);
    }
}
