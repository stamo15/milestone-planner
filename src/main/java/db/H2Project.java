package db;

import model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class H2Project extends H2<Project> {
    public static final String TABLE = "projects";

    public H2Project(){
        super();
        this.setUp();
    }

    @Override
    public void setUp() {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS "+ TABLE + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "userId INT," +
                    "name VARCHAR(255) NOT NULL ," +
                    "createdAt VARCHAR(25)," +
                    " FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE) " );
        } catch (SQLException e) {
            System.out.println("Actually have an error");
            e.printStackTrace();
        }
    }

    @Override
    public void add(Project newItem) {
        String addProjectQuery = "INSERT INTO " + TABLE + " (userId, name, createdAt) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addProjectQuery)) {
            preparedStatement.setInt(1, newItem.getUserId());
            preparedStatement.setString(2, newItem.getName());
            preparedStatement.setString(3, H2.getFormattedDate(newItem.getCreatedAt()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project find(int id) {
        String findProjectQuery = "SELECT id, userId, name, createdAt  FROM " + TABLE + " WHERE id = " + id;
        return findWith(findProjectQuery);
    }

    public List<Project> findByUserId(int userId) {
        String listProjectsQuery = "SELECT id, userId, name, createdAt  FROM " + TABLE+ " WHERE userId = " + userId;
        List<Project> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listProjectsQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(new Project(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        H2.setDate(resultSet.getString(4))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public boolean remove(int id) {
        String deleteProjectQuery = "DELETE FROM " + TABLE + " WHERE id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteProjectQuery)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(int id, String newName) {
        String updateProjectQuery = "UPDATE " + TABLE + " SET name = '" + newName + "' WHERE id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProjectQuery)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Project findWith(String query){
        Project output = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                output = new Project(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        H2.setDate(resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public List<Project> all() {
        String listProjectsQuery = "SELECT id, name, createdAt  FROM" + TABLE;
        List<Project> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listProjectsQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(new Project(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        H2.setDate(resultSet.getString(4))));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}