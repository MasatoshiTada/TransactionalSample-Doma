package com.example.jpa;

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
 * Created by tada on 2015/11/01.
 */
@WebServlet("/jpa-cdi-required")
public class JpaCdiRequiredTestServlet extends HttpServlet {
    @Inject
    private JpaCdiRequiredTestDao testDao;

    private static final String COMMIT = "commit";
    private static final String ROLLBACK = "rollback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeQuietly(() -> testDao.deleteAll());

        executeQuietly(() -> testDao.insert01RollbackOnNoDontRollbackonNoThrowsNPE(
                new TestEntity(1, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> testDao.insert02RollbackOnNoDontRollbackonNoThrowsIOE(
                new TestEntity(2, "IOE", "{}", "{}", COMMIT)));
        executeQuietly(() -> testDao.insert03RollbackOnNoDontRollbackonNPEThrowsNPE(
                new TestEntity(3, "NPE", "{}", "NPE", COMMIT)));
        executeQuietly(() -> testDao.insert04RollbackOnNoDontRollbackonNPEThrowsIOE(
                new TestEntity(4, "IOE", "{}", "NPE", COMMIT)));
        executeQuietly(() -> testDao.insert05RollbackOnNoDontRollbackonIOEThrowsNPE(
                new TestEntity(5, "NPE", "{}", "IOE", ROLLBACK)));
        executeQuietly(() -> testDao.insert06RollbackOnNoDontRollbackonIOEThrowsIOE(
                new TestEntity(6, "IOE", "{}", "IOE", COMMIT)));
        executeQuietly(() -> testDao.insert07RollbackOnNPEDontRollbackonNoThrowsNPE(
                new TestEntity(7, "NPE", "NPE", "{}", ROLLBACK)));
        executeQuietly(() -> testDao.insert08RollbackOnNPEDontRollbackonNoThrowsIOE(
                new TestEntity(8, "IOE", "NPE", "{}", COMMIT)));
        executeQuietly(() -> testDao.insert09RollbackOnIOEDontRollbackonNoThrowsNPE(
                new TestEntity(9, "NPE", "IOE", "{}", ROLLBACK)));
        executeQuietly(() -> testDao.insert10RollbackOnIOEDontRollbackonNoThrowsIOE(
                new TestEntity(10, "IOE", "IOE", "{}", ROLLBACK)));
        executeQuietly(() -> testDao.insert11RollbackOnNPEDontRollbackonIOEThrowsNPE(
                new TestEntity(11, "NPE", "NPE", "IOE", ROLLBACK)));
        executeQuietly(() -> testDao.insert12RollbackOnNPEDontRollbackonIOEThrowsIOE(
                new TestEntity(12, "IOE", "NPE", "IOE", COMMIT)));
        executeQuietly(() -> testDao.insert13RollbackOnIOEDontRollbackonNPEThrowsNPE(
                new TestEntity(13, "NPE", "IOE", "NPE", COMMIT)));
        executeQuietly(() -> testDao.insert14RollbackOnIOEDontRollbackonNPEThrowsIOE(
                new TestEntity(14, "IOE", "IOE", "NPE", ROLLBACK)));

        List<TestEntity> list = testDao.findAll();
        int countActual = list.size();
        int countExpected = 7;

        PrintWriter out = resp.getWriter();
        out.println("count = " + countActual + "<br>");
        boolean allCommitted = list.stream()
                .map(test -> test.getExpected())
                .allMatch(expected -> expected.equals(COMMIT));
        if (countActual == countExpected && allCommitted == true) {
            out.println("OK");
        } else {
            out.println("NG");
        }
    }

    @FunctionalInterface
    private static interface ThrowableRunnable {
        void execute() throws Exception;
    }

    private static void executeQuietly(ThrowableRunnable tr) {
        try {
            tr.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
