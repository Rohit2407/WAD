import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FirstServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws
       ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String name= req.getParameter("t1");
        out.println("<html><body>");
        out.println("<p>Hello"+ name + "</p>");
        out.println("</body></html>");

        out.close();

       }
}