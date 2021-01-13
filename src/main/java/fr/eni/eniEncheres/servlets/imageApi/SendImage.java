package fr.eni.eniEncheres.servlets.imageApi;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "SendImage", urlPatterns = {"/uploads/images/"})
public class SendImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("servlet SendImage doGET----------------------------------------------------------");
        String imageName = (String) request.getAttribute("imageName");
        System.out.println("image name: " +imageName);
        ServletContext sc = getServletContext();

        try (InputStream is = sc.getResourceAsStream("/uploads/images/" + imageName)) {

            System.out.println(is.toString());
            // it is the responsibility of the container to close output stream
            OutputStream os = response.getOutputStream();

            if (is == null) {

                response.setContentType("text/plain");
                os.write("Failed to send image".getBytes());
            } else {

                response.setContentType("image/jpeg");

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {

                    os.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
