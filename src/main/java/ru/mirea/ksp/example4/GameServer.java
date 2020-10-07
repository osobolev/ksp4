package ru.mirea.ksp.example4;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.util.EnumSet;

public class GameServer {

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(false);
        config.setJdbcUrl("jdbc:h2:~/game_db");
        DataSource ds = new HikariDataSource(config);

        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        handler.addFilter(EncodingFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/app/*", EnumSet.of(DispatcherType.REQUEST));

        handler.addServlet(new ServletHolder(new LoginServlet(ds)), "/login.html");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout.html");
        handler.addServlet(new ServletHolder(new GameServlet(ds)), "/app/game.html");

        server.setHandler(handler);
        server.start();
    }
}
