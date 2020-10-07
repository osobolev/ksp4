package ru.mirea.ksp.example4;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ru.mirea.ksp.example4.dao.LoginDao;
import ru.mirea.ksp.example4.dao.LoginUser;
import ru.mirea.ksp.example4.view.LoginView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    private BCrypt.Verifyer verifyer = BCrypt.verifyer();
    private DataSource dataSource;

    public LoginServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        new LoginView().view(null, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try (Connection connection = dataSource.getConnection()) {
            LoginDao dao = new LoginDao(connection);
            LoginUser user = dao.login(login);
            if (user != null) {
                BCrypt.Result result = verifyer.verify(password.toCharArray(), user.passwordHash);
                if (result.verified) {
                    req.getSession().setAttribute(SessionUser.CURRENT_USER, new SessionUser(user.id, user.displayName));
                    resp.sendRedirect("app/game.html");
                }
            }
            new LoginView().view("Неправильный логин или пароль", resp.getWriter());
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
