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
@WebServlet("/doma-cdi-requires-new")
public class TestCdiRequiresNewServlet extends HttpServlet {

    @Inject
    private TestCdiRequiresNewService testCdiRequiresNewService;

    private static final String COMMIT = "commit";
    private static final String ROLLBACK = "rollback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        executeQuietly(() -> testCdiRequiresNewService.deleteAll());

        executeQuietly(() -> testCdiRequiresNewService.insert01RollbackOnNoDontRollbackonNoThrowsNPE(
                new TestEntity(1, "NPE", "{}", "{}", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert02RollbackOnNoDontRollbackonNoThrowsIOE(
                new TestEntity(2, "IOE", "{}", "{}", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert03RollbackOnNoDontRollbackonNPEThrowsNPE(
                new TestEntity(3, "NPE", "{}", "NPE", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert04RollbackOnNoDontRollbackonNPEThrowsIOE(
                new TestEntity(4, "IOE", "{}", "NPE", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert05RollbackOnNoDontRollbackonIOEThrowsNPE(
                new TestEntity(5, "NPE", "{}", "IOE", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert06RollbackOnNoDontRollbackonIOEThrowsIOE(
                new TestEntity(6, "IOE", "{}", "IOE", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert07RollbackOnNPEDontRollbackonNoThrowsNPE(
                new TestEntity(7, "NPE", "NPE", "{}", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert08RollbackOnNPEDontRollbackonNoThrowsIOE(
                new TestEntity(8, "IOE", "NPE", "{}", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert09RollbackOnIOEDontRollbackonNoThrowsNPE(
                new TestEntity(9, "NPE", "IOE", "{}", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert10RollbackOnIOEDontRollbackonNoThrowsIOE(
                new TestEntity(10, "IOE", "IOE", "{}", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert11RollbackOnNPEDontRollbackonIOEThrowsNPE(
                new TestEntity(11, "NPE", "NPE", "IOE", ROLLBACK)));
        executeQuietly(() -> testCdiRequiresNewService.insert12RollbackOnNPEDontRollbackonIOEThrowsIOE(
                new TestEntity(12, "IOE", "NPE", "IOE", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert13RollbackOnIOEDontRollbackonNPEThrowsNPE(
                new TestEntity(13, "NPE", "IOE", "NPE", COMMIT)));
        executeQuietly(() -> testCdiRequiresNewService.insert14RollbackOnIOEDontRollbackonNPEThrowsIOE(
                new TestEntity(14, "IOE", "IOE", "NPE", ROLLBACK)));


        List<TestEntity> list = testCdiRequiresNewService.selectAll();
        int countActual = list.size();
        int countExpected = 7;
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
