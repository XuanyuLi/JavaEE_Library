package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by lixuanyu
 * on 2017/6/16.
 */
public class TransactionTest {
    public static void main(String[] args) {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "";
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
