package com.example.doma;

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
@WebServlet("/doma-ejb")
public class TestEjbServlet extends HttpServlet {

    @Inject
    private TestEjbService testEjbService;

    private static final String COMMIT = "commit";
    private static final String ROLLBACK = "rollback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        executeQuietly(() -> testEjbService.deleteAll());

        executeQuietly(() -> testEjbService.insertRequiresNewThrowNPE(
                new TestEntity(1, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> testEjbService.insertRequiresNewThrowIOE(
                new TestEntity(2, "IOE", "{}", "{}", COMMIT)));
        executeQuietly(() -> testEjbService.insertRequiredThrowNPE(
                new TestEntity(3, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> testEjbService.insertRequiredThrowIOE(
                new TestEntity(4, "IOE", "{}", "{}", COMMIT)));

        List<TestEntity> list = testEjbService.selectAll();
        int countActual = list.size();
        int countExpected = 2;
        boolean allCommited = list.stream()
                .map(t -> t.expected)
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
