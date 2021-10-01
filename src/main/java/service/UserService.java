package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService implements IUserService{
    private UserDAO userDAO = new UserDAO();

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public boolean save(User user) throws SQLException {
        return userDAO.save(user);
    }

    @Override
    public boolean update( User user) throws SQLException {
        return userDAO.update(user);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return userDAO.delete(id);
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }
}
