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

import static dao.UserDAO.*;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    public static final String CREATE = "create";
    public static final String ACTION = "action";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    public static final String EMPTY = "";
    public static final String DISABLED = "disabled";
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION);
        if (action == null) {
            action = EMPTY;
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
            case "page": {
                showPage(request, response);
                break;
            }
            default: {
//                showListUser(request, response);
                showPage(request, response);
                break;

            }
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int LIMIT = 6;
        int totalItem = userService.countRecord();
        int totalPage;
        if (totalItem % LIMIT == 0) {
            totalPage = totalItem / LIMIT;
        } else {
            totalPage = totalItem / LIMIT + 1;
        }
        String previous = EMPTY;
        String next = EMPTY;
        request.setAttribute("totalPage", totalPage);
        String page = request.getParameter("page");

        if (page == null) {
            page = "1";
        }
        int pageOut = Integer.parseInt(page);

        if (pageOut == 1) {
            previous = DISABLED;
        } else if (pageOut == totalPage) {
            next = DISABLED;
        }

        int offset = (pageOut - 1) * LIMIT;
        List<User> users = userService.findUserByOffset(offset, LIMIT);
        request.setAttribute("users", users);
        String active = "active";
        request.setAttribute("active", active);
        request.setAttribute("pageOut", pageOut);
        request.setAttribute("previous", previous);
        request.setAttribute("next", next);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
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
        String action = request.getParameter(ACTION);
        if (action == null) {
            action = EMPTY;
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
//                showListUser(request, response);
                showPage(request, response);
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
        int id = Integer.parseInt(request.getParameter(ID));

        String name = request.getParameter(NAME);
        String address = request.getParameter(ADDRESS);
        String email = request.getParameter(EMAIL);

        boolean isUpdate;
        if (name.equals(EMPTY) || address.equals(EMPTY) || email.equals(EMPTY)) {
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
        String name = request.getParameter(NAME);
        String address = request.getParameter(ADDRESS);
        String email = request.getParameter(EMAIL);

        boolean isSave;
        if (name.equals(EMPTY) || address.equals(EMPTY) || email.equals(EMPTY)) {
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
