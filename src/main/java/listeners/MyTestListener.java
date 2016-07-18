package listeners;

import org.testng.*;
import java.sql.*;
import java.util.Date;

import static listeners.MyClassListener.k;
import static listeners.MyClassListener.key;

public class MyTestListener extends TestListenerAdapter {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private Timestamp ts_begin, ts_end;
    private Date beginning, ending;
    private int time_completion;

    @Override
    public void onTestStart(ITestResult testResult)
    {
        beginning = new Date();
        ts_begin = new Timestamp(beginning.getTime());
    }
    @Override
    public void onTestSuccess(ITestResult testResult)
    {
        ending = new Date();
        ts_end = new Timestamp(ending.getTime());
        time_completion =(int) ((ending.getTime()-beginning.getTime())/1000);
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = " insert into res_test.step (ID_test, begining, ending, status, time_completion)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt         (1, key);
            preparedStmt.setTimestamp   (2, ts_begin);
            preparedStmt.setTimestamp   (3, ts_end);
            preparedStmt.setInt         (4, testResult.getStatus());
            preparedStmt.setInt         (5, time_completion);

            preparedStmt.execute();

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onTestFailure(ITestResult testResult)
    {
        k = false;
        ending = new Date();
        ts_end = new Timestamp(ending.getTime());
        time_completion =(int) ((ending.getTime()-beginning.getTime())/1000);
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = " insert into res_test.step (ID_test, begining, ending, status, time_completion)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt         (1, key);
            preparedStmt.setTimestamp   (2, ts_begin);
            preparedStmt.setTimestamp   (3, ts_end);
            preparedStmt.setInt         (4, testResult.getStatus());
            preparedStmt.setInt         (5, time_completion);

            preparedStmt.execute();

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();

        }
    }
}