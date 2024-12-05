import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Для запуска PostgreSQL ищием pgAdmin4

@WebServlet("/base")
public class BaseServlet extends HttpServlet {
    @Inject
    TestServiceBean bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<Student> students = bean.getAllStudents();
        req.setAttribute("students", students);
        req.getRequestDispatcher("Simple Form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String mail_name = req.getParameter("email");
//        if (name == null || mail_name == null) {
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
        String action = req.getParameter("submitAction");
        if ("add".equals(action)) {
            bean.addStudent(name, mail_name);
        } else if ("delete".equals(action)) {
            name = req.getParameter("student_id");
            bean.delStudent(Long.parseLong(name));

        }
        resp.sendRedirect(req.getContextPath() + "/base");
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            req.getRequestDispatcher("Simple Form.jsp").forward(req, resp);
    }
}
