import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig //Можно указать ограничения на размер файла и location
@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uploadPath = getUploadBase();
//        String uploadPath = "C:/Users/User/Desktop/IVM-24/Industrial_development/Warka/uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        for (Part part : req.getParts()) {
            //Если путь относительный, то будет использован location
            part.write(uploadPath + File.separator
                    + part.getSubmittedFileName());
        }
        req.getRequestDispatcher("fileUpload.html").forward(req, resp);
    }
    private String getUploadBase() {
       return System.getProperty("Zadani1");
    }
}
