package dao;

import config.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    public static final String SELECT_ALL = "select * from user";
    public static final String CREATE_USER = "insert into user (name, address, email) value (?, ?, ?)";
    public static final String UPDATE_USER = "update user set id = ?, name = ? , address = ?, email = ? where id = ?";
    public static final String DELETE_USER = "delete from user where id = ?";
    public static final String FIND_BY_ID = "select id, name, address, email from user where id = ?";
    private Connection connection = DBConnection.getConnection();

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                users.add(new User(id, name, address, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean save(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_USER);

        statement.setString(1, user.getName());
        statement.setString(2, user.getAddress());
        statement.setString(3, user.getEmail());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);

        statement.setInt(1, user.getId());
        statement.setString(2, user.getName());
        statement.setString(3, user.getAddress());
        statement.setString(4, user.getEmail());
        statement.setInt(5,user.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean isDelete;
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, id);
        isDelete = statement.executeUpdate() > 0;
        return isDelete;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String email = resultSet.getString("email");

            user = new User(id, name, address, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
