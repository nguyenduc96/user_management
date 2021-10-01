package controller;


import model.User;
import service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    public static final String CREATE = "create";
    public static final String ACTION = "action";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION);
        if (action == null) {
            action = "";
        }
        switch (action) {
            case CREATE: {
                showCreateUser(request, response);
                break;
            }
            case EDIT: {
                showEditInfoUser(request, response);
                break;
            }
            case DELETE: {
                deleteUserInfo(request, response);
                break;
            }
            default: {
                showListUser(request, response);
                break;
            }
        }
    }

    private void showEditInfoUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findById(id);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(request, response);

    }


    private void showCreateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showListUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.findAll();

        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case CREATE: {
                try {
                    createUserInfo(request, response);
                } catch (SQLException e) {
                    request.setAttribute("message", "Create fails!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
                    dispatcher.forward(request, response);
                }
                break;
            }
            case EDIT: {
                try {
                    editUserInfo(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case DELETE: {
                deleteUserInfo(request, response);
                break;
            }
            default: {
                showListUser(request, response);
                break;
            }
        }
    }

    private void deleteUserInfo(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            userService.delete(id);
            response.sendRedirect("/users");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void editUserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        boolean isUpdate;
        if (name.equals("") || address.equals("") || email.equals("")) {
            isUpdate = false;
        } else {
            isUpdate = userService.update(new User(id, name, address, email));
        }
        if (isUpdate) {
            response.sendRedirect("/users");
        } else {
            request.setAttribute("message", "Edit fails");
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void createUserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        boolean isSave;
        if (name.equals("") || address.equals("") || email.equals("")) {
            isSave = false;
        } else {
            isSave = userService.save(new User(name, address, email));
        }
        if (isSave) {
            request.setAttribute("message", "User is save!");
        } else {
            request.setAttribute("message", "Create fails");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(request, response);
    }
}
