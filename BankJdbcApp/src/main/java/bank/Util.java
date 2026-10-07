package bank;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {
    public static void respond(HttpServletResponse resp, String msg) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println("<html><body><h3>" + msg + "</h3><a href='index.html'>Back</a></body></html>");
    }
}
