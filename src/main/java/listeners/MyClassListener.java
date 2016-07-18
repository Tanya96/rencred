package listeners;

import java.sql.*;
import java.util.Date;

/**
 * Created by andrey on 18.07.2016.
 */
public class MyClassListener{
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static Timestamp ts_begin;
    private static Timestamp ts_end;
    private static Date beginning;
    private static Date ending;
    private static int time_completion;
    public static String status;
    public static  int key;
    public static boolean k;

    public static void onBeforeClass(String name) {
        k = true;
        status = "In progress";
        beginning = new Date();
        ts_begin = new Timestamp(beginning.getTime());
        System.out.println("test start");
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            String query = " insert into res_test.test (name, begining, ending, status, time_completion)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString      (1, name);
            preparedStmt.setTimestamp   (2, ts_begin);
            preparedStmt.setTimestamp   (3, ts_begin);
            preparedStmt.setString      (4, status);
            preparedStmt.setInt         (5, 0);

            preparedStmt.execute();

            ResultSet rs = preparedStmt.getGeneratedKeys();
            rs.next();
            key = rs.getInt(1);
            preparedStmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void onAfterClass() {
        System.out.println("test end");
        if(k)
            status = "Success";
        ending = new Date();
        ts_end = new Timestamp(ending.getTime());
        time_completion =(int) ((ending.getTime()-beginning.getTime())/1000);
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "UPDATE res_test.test set ending = ?, status=?, time_completion=? WHERE test_ID = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setTimestamp   (1, ts_end);
            preparedStmt.setString      (2, status);
            preparedStmt.setInt         (3, time_completion);
            preparedStmt.setInt         (4, key);
            preparedStmt.execute();
            preparedStmt.close();

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();

        }
    }

}
