// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;
import java.util.Date;
@WebServlet(name = "attributeServlet", urlPatterns = {"/attributeServlet"})
public class attributeServlet extends HttpServlet
{
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
  String action = request.getParameter("action");

  if(action != null && action.equals("invalidate")) {
    // Get session object
    HttpSession session = request.getSession();
    session.invalidate();

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();


    out.println("<html>");
    // no-cache lets the page reload by clicking on the reload link
    out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
    out.println("<head>");
    out.println(" <title>Session lifecycle</title>");
    out.println("</head>");
    out.println("");

    out.println("<body>");
    out.println("<p>Your session has been invalidated.</P>");

    String lifeCycleURL = "/attributeServlet";
    out.println("<a href=\"" + lifeCycleURL + "?action=newSession\">");
    out.println("Create new session</A>");

    out.println("</body>");
    out.println("</html>");
    out.close();

  }
  else {
     // Get session object
     HttpSession session = request.getSession();

     String name   = request.getParameter("attrib_name");
     String value  = request.getParameter("attrib_value");
     String age    = request.getParameter("attrib_age");
     String ageValue = request.getParameter("attrib_ageValue");
     String remove = request.getParameter("attrib_remove");

     if (remove != null && remove.equals("on"))
     {
        session.removeAttribute(name);
        session.removeAttribute(age);
     }
     else
     {
        if ((name != null && name.length() > 0) && (value != null && value.length() > 0) && (age != null && age.length() > 0) && (ageValue != null && ageValue.length() > 0))
        {
           session.setAttribute(name, value);
           session.setAttribute(age, ageValue);
        }

     }

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();


     out.println("<html>");
     // no-cache lets the page reload by clicking on the reload link
     out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
     out.println("<head>");
     out.println(" <title>Session lifecycle</title>");
     out.println("</head>");
     out.println("");

     out.println("<body>");
     out.println("<h1><center>Session attributes</center></h1>");
     out.print  ("<BR>Session status: ");
     if(session.isNew()) {
       out.println("New session.");
     }
     else {
       out.println ("Old session.");
     }

     // Get the session ID
     out.print  ("<br>Session ID: ");
     out.println(session.getId());
     // Get the created time, convert it to a Date object
     out.print  ("<br>Creation time: ");
     out.println(new Date (session.getCreationTime()));
     // Get the last time it was accessed
     out.print  ("<br>Last accessed time: ");
     out.println(new Date(session.getLastAccessedTime()));
     // Get the max-inactive-interval setting
     out.print  ("<br>Maximum inactive interval (seconds): <br><br>");
     out.println(session.getMaxInactiveInterval());

     out.println("Enter name and value of an attribute");

     // String url = response.encodeURL ("offutt/servlet/attributeServlet");
     String url = response.encodeURL("attributeServlet");
     out.println("<form action=\"" + url + "\" method=\"GET\">");
     out.println(" Name: ");
     out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

     out.println(" Value: ");
     out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");

     out.println(" Age: ");
     out.println(" <input type=\"text\" size=\"10\" name=\"attrib_age\">");

     out.println(" Age Value: ");
     out.println(" <input type=\"text\" size=\"10\" name=\"attrib_ageValue\">");

     out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
     out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
     out.println("</form>");

     String lifeCycleURL = "/attributeServlet";
     out.print  ("<br><br><a href=\"" + lifeCycleURL + "?action=invalidate\">");
     out.println("Invalidate the session</a>");
     out.print  ("<br><a href=\"" + lifeCycleURL + "\">");
     out.println("Reload this page</a>");
     out.println("<hr>");

     out.println("Attributes in this session:");
     Enumeration e = session.getAttributeNames();
     while (e.hasMoreElements())
     {
        String att_name  = (String) e.nextElement();
        String att_age   = (String) e.nextElement();
        String att_value = (String) session.getAttribute(att_name);
        String att_ageValue = (String) session.getAttribute(att_age);

        out.print  ("<br><b>Name:</b> ");
        out.println(att_name);
        out.print  ("<br><b>Value:</b> ");
        out.println(att_value);
        out.print  ("<br><b>Age:</b> ");
        out.println(att_age);
        out.print  ("<br><b>Age Value:</b> ");
        out.println(att_ageValue);
     } //end while

     out.println("</body>");
     out.println("</html>");
     out.close();
   }
} // End doGet
} //End  SessionLifeCycle
