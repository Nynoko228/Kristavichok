import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@MultipartConfig //Можно указать ограничения на размер файла и location
@WebServlet("/webFilterServlet")
public class WebFilterServlet extends HttpServlet {

    @Inject Bean bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Статистика запросов</title></head><body>");
        out.println("<h1>Статистика запросов</h1>");
        out.println("<ul>");
        for (Map.Entry<String, Integer> entry : bean.GetCnt().entrySet()) {
            out.println("<li>" + entry.getKey() + ": " + entry.getValue() + "</li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
    }

}
