package com.example.jdbc;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by tada on 2015/10/31.
 */
@WebServlet("/jdbc-ejb")
public class JdbcEjbTestServlet extends HttpServlet {

    @Inject
    private JdbcEjbTestDao jdbcEjbTestDao;

    private static final String COMMIT = "commit";
    private static final String ROLLBACK = "rollback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeQuietly(() -> jdbcEjbTestDao.deleteAll());

        executeQuietly(() -> jdbcEjbTestDao.insertRequiresNewThrowsNPE(
                new TestEntity(1, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> jdbcEjbTestDao.insertRequiresNewThrowsIOE(
                new TestEntity(2, "IOE", "{}", "{}", COMMIT)));
        executeQuietly(() -> jdbcEjbTestDao.insertRequiredThrowsNPE(
                new TestEntity(3, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> jdbcEjbTestDao.insertRequiredThrowsIOE(
                new TestEntity(4, "IOE", "{}", "{}", COMMIT)));

        List<TestEntity> list = jdbcEjbTestDao.findAll();
        int countActual = list.size();
        int countExpected = 2;
        boolean allCommited = list.stream()
                .map(t -> t.getExpected())
                .allMatch(e -> e.equals(COMMIT));

        PrintWriter out = resp.getWriter();
        out.println("count = " + countActual + "<br>");

        if (countExpected == countActual && allCommited == true) {
            out.println("OK");
        } else {
            out.println("NG");
        }
    }

    private static void executeQuietly(ThrowableRunnable tr) {
        try {
            tr.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FunctionalInterface
    private static interface ThrowableRunnable {
        void execute() throws Exception;
    }
}
