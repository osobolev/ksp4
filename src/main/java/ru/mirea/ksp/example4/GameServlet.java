package ru.mirea.ksp.example4;

import ru.mirea.ksp.example4.dao.GameDao;
import ru.mirea.ksp.example4.dao.User;
import ru.mirea.ksp.example4.view.GameView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameServlet extends HttpServlet {

    private DataSource dataSource;

    public GameServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUser user = (SessionUser) req.getSession().getAttribute(SessionUser.CURRENT_USER);
        try (Connection connection = dataSource.getConnection()) {
            GameDao dao = new GameDao(connection);
            int clicks = dao.getClicks(user.id);
            int maxClicks = dao.getMaxClicks();
            List<User> winners = dao.getWinners(maxClicks);
            new GameView().view(user, clicks, winners, maxClicks, resp.getWriter());
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUser user = (SessionUser) req.getSession().getAttribute(SessionUser.CURRENT_USER);
        try (Connection connection = dataSource.getConnection()) {
            GameDao dao = new GameDao(connection);
            dao.addClick(user.id);
            connection.commit();
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        resp.sendRedirect("game.html");
    }
}
