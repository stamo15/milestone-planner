package db;

import model.Milestone;
import model.Priority;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class H2Milestone extends H2<Milestone> {
    public static final String TABLE = "milestones";

    public H2Milestone(){
        super();
        this.setUp();
    }

    @Override
    void setUp() {
        try{
            Statement statement = connection.createStatement();

            //Creating milestones table
            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS "+ TABLE + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "projectId INT NOT NULL ," +
                    "description VARCHAR(1000) NOT NULL ," +
                    "priority VARCHAR(25) NOT NULL ," +
                    "completionDate VARCHAR(25) ," +
                    "dueDate VARCHAR(25) NOT NULL," +
                    "isCompleted BOOL, " +
                    "FOREIGN KEY(projectId) REFERENCES projects(id) ON DELETE CASCADE)  " );

            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS milestone_sharings (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "sharerUserId INT NOT NULL ," +
                    "receiverUserId INT," +
                    "milestoneId INT NOT NULL ," +
                    "url VARCHAR(255) NOT NULL ," +
                    "FOREIGN KEY(milestoneId) REFERENCES milestones(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(sharerUserId) REFERENCES users(id) ON DELETE CASCADE)  " );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Milestone newItem) {
        String addMilestoneQuery = "INSERT INTO " + TABLE + " " +
                "(projectId, description, priority, completionDate, dueDate, isCompleted) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addMilestoneQuery)) {
            preparedStatement.setInt(1, newItem.getProjectId());
            preparedStatement.setString(2, newItem.getDescription());
            preparedStatement.setString(3, newItem.getPriority().toString());
            preparedStatement.setString(4, H2.getFormattedDate(newItem.getCompletionDate()));
            preparedStatement.setString(5, H2.getFormattedDate(newItem.getDueDate()));
            preparedStatement.setBoolean(6, newItem.isCompleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Milestone find(int id) {
        String findMilestoneQuery = "SELECT id, projectId, description, priority, completionDate, dueDate, isCompleted FROM " +
                TABLE + " WHERE id = " + id;
        return findWith(findMilestoneQuery);
    }

    public boolean update(int id, String newDescription, Priority newPriority,
                          Calendar newDueDate, boolean newIsCompleted) {
        String updateProjectQuery = "UPDATE " + TABLE + " SET description = '" + newDescription + "',"+
                " priority = '"+ newPriority.toString() + "'," +
                " dueDate = '"+ H2.getFormattedDate(newDueDate) + "'," +
                " isCompleted = '"+ newIsCompleted + "'";
        if(newIsCompleted){
            updateProjectQuery += ", completionDate = '"+ H2.getFormattedDate(Calendar.getInstance()) + "'";
        }
        updateProjectQuery += " WHERE id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProjectQuery)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Milestone findWith(String query){
        Milestone output = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                output = new Milestone(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        this.stringToPriority(resultSet.getString(4)),
                        H2.setDate(resultSet.getString(5)),
                        H2.setDate(resultSet.getString(6)),
                        resultSet.getBoolean(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public List<Milestone> findByProjectId(int projectId) {
        String listMilestonesQuery = "SELECT id, projectId, description, priority, completionDate, dueDate, isCompleted FROM "
                + TABLE + " WHERE projectId = " + projectId;
        List<Milestone> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listMilestonesQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(new Milestone(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        this.stringToPriority(resultSet.getString(4)),
                        H2.setDate(resultSet.getString(5)),
                        H2.setDate(resultSet.getString(6)),
                        resultSet.getBoolean(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public List<Milestone> findSharedMilestones(int userId) {
        List<Integer> milestoneIds = findMilestoneIds(userId);
        List<Milestone> output = new ArrayList<>();
        for(int milestoneId : milestoneIds){
            Milestone milestone = this.find(milestoneId);
            if(milestone != null){
                output.add(milestone);
            }
        }
        return output;
    }

    public void addMilestoneSharing(int milestoneId, int userId) {
        String url = HashingUtil.getHashedPassword(Integer.toString(milestoneId), "");

        String addMilestoneSharingQuery = "INSERT INTO milestone_sharings " +
                "(milestoneId, sharerUserId, url) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addMilestoneSharingQuery)) {
            preparedStatement.setInt(1, milestoneId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, url);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addMilestoneReceiver(int receiverId, String url){
        String listMilestoneIds = "SELECT id, sharerUserId, milestoneId, url FROM milestone_sharings";
        int milestoneSharingId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(listMilestoneIds)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString(4).equals(url)){
                    milestoneSharingId = resultSet.getInt(1);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(milestoneSharingId == -1){
            return false;
        } else {
            String updateMilestoneSharingQuery = "UPDATE milestone_sharings SET receiverUserId = '" + receiverId + "' WHERE id = " + milestoneSharingId;
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateMilestoneSharingQuery)) {
                return preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Integer> findMilestoneIds(int receiverId) {
        String listMilestoneIds = "SELECT id, sharerUserId, milestoneId, url FROM milestone_sharings WHERE receiverUserId = " + receiverId;
        List<Integer> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listMilestoneIds)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public Priority stringToPriority(String priority){
        for(Priority p : Priority.values()){
            if(p.toString().equals(priority)){
                return p;
            }
        }
        return Priority.MEDIUM;
    }

    @Override
    public List<Milestone> all() {
        String listUsersQuery = "SELECT id, description, priority, completionDate, dueDate, isCompleted FROM" + TABLE;
        List<Milestone> output = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(listUsersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                output.add(new Milestone(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        this.stringToPriority(resultSet.getString(4)),
                        H2.setDate(resultSet.getString(5)),
                        H2.setDate(resultSet.getString(6)),
                        resultSet.getBoolean(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}
