import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig //Можно указать ограничения на размер файла и location
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("fileDownload.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fileType = req.getParameter("mySelectt");

        String filePath = System.getProperty("Zadanie2") + File.separator + fileType;
        File file = new File(filePath);

        resp.setHeader("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));


        byte[] buffer = new byte[1024 * 1024];
        try (FileInputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = resp.getOutputStream()) {
            int count;
            while ((count = inputStream.read(buffer)) >= 0) {
                resp.getOutputStream().write(buffer, 0, count);
            }
            outputStream.flush();
        } catch (IOException e) {
            resp.getWriter().write("Ошибка загрузки файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
