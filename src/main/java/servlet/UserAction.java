package servlet;

import util.Db;
import util.Error;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lixuanyu
 * on 2017/6/15.
 */
@WebServlet(urlPatterns = "/user")
public class UserAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("register".equals(action)) {
            register(req,resp);
            return;
        }
        if ("login".equals(action)) {
            login(req,resp);
            return;
        }
        if ("logout".equals(action)) {
            logout(req,resp);
            return;
        }
        Error.showError(req,resp);

    }
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password");

        Connection connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM javaee_library.user WHERE username = ? AND password = ?";
        try {
            if (connection != null) {
                statement = connection.prepareStatement(sql);
            } else {
                Error.showError(req,resp);
                return;
            }
            statement.setString(1,username);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                req.getSession().setAttribute("username", username);
                req.getSession().setAttribute("role", role);
                if ("用户".equals(role)) {
                    resp.sendRedirect("index.jsp");
                    return;
                }
                if ("管理员".equals(role)) {
                    resp.sendRedirect("book?action=queryAll");
                    return;
                }
                Error.showError(req, resp);
            } else {
                req.setAttribute("message","用户名或密码错误");
                req.getRequestDispatcher("default.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(resultSet,statement,connection);
        }
    }
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password");
        Connection connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT *FROM javaee_library.user WHERE username = ?";
        try {
            if (connection != null) {
                statement = connection.prepareStatement(sql);
            } else {
                Error.showError(req, resp);
                return;
            }
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                req.setAttribute("message", "用户名存在");
                return;
            }
            sql = "INSERT INTO javaee_library.user(username,password) VALUES (?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            resp.sendRedirect("default.jsp  ");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(resultSet,statement,connection);
        }
    }
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
