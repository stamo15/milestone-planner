package db;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2User extends H2<User> {
    public static final String TABLE = "users";

    public H2User(){
        super();
        this.setUp();
    }

    @Override
    public void setUp() {
        try{
            Statement statement = connection.createStatement();

            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS "+ TABLE + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "firstName VARCHAR(255) NOT NULL ," +
                    "lastName VARCHAR(255) NOT NULL ," +
                    "email VARCHAR(255) NOT NULL ," +
                    "salt VARCHAR(255) NOT NULL ," +
                    "password VARCHAR(255) NOT NULL )" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User newItem) {
        String addUserQuery = "INSERT INTO " + TABLE + " (firstName, lastName, email, password, salt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery)) {
            preparedStatement.setString(1, newItem.getFirstName());
            preparedStatement.setString(2, newItem.getLastName());
            preparedStatement.setString(3, newItem.getEmail());
            preparedStatement.setString(4, HashingUtil.getHashedPassword(newItem.getPassword(), newItem.getSalt()));
            preparedStatement.setString(5, newItem.getSalt());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User find(int id) {
        String findUserQuery = "SELECT id, firstName, lastName, email, password, salt  FROM " + TABLE + " WHERE id = " + id;
        return findWith(findUserQuery);
    }

    @Override
    public boolean remove(int id) {
        String deleteUserQuery = "DELETE FROM " + TABLE + " WHERE id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmailPassword(String email, String password){
        String findByEmailQuery = "SELECT id, firstName, lastName, email, password, salt  FROM " + TABLE +
                " WHERE email = '" + email + "'";

        User user = findWith(findByEmailQuery);
        if(user == null || !user.getPassword().equals(HashingUtil.getHashedPassword(password, user.getSalt()))){
            return null;
        }
        return user;

    }

    private User findWith(String query){
        User output = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                output = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    @Override
    public List<User> all() {
        String listUsersQuery = "SELECT id, firstName, lastName, email, password, salt  FROM" + TABLE;
        List<User> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listUsersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return output;
    }
}
