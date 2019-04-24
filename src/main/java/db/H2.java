package db;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class H2<T> {
    protected Connection connection;
    protected Server server;
    public static final String DATABASE = "jdbc:h2:mem:~/milestone_planner";

    static Connection getConnection(String database) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        try{
            String ifExists = ";IFEXISTS=TRUE";
            return DriverManager.getConnection(database + ifExists, "sa", "");
        } catch (Exception e){
            return DriverManager.getConnection(database, "sa", "");
        }

    }

    public H2(){
        this(DATABASE);
        this.setUp();
    }

    public H2(String database){
        try {
            // start the TCP Server
            this.server = Server.createTcpServer().start();

            connection = getConnection(database);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer(){
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    public static String getFormattedDate(Calendar calendar){
        if(calendar == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public static Calendar setDate(String dateAsString) throws ParseException {
        if(dateAsString.equals("")){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateAsString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    abstract void setUp();
    abstract void add(T newItem);
    abstract T find(int id);
    abstract boolean remove(int id);
    abstract List<T> all();
}
