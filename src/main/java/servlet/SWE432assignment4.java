import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "assignment4",
        urlPatterns = {"/assignment4"}
    )

public class SWE432assignment4 extends HttpServlet
{
  static String Style = "mystyle.css";

  public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      String input = "";
      String result = "";
      String names = request.getParameter("names");
      String random = "random";
      String rRandom = "random select with replacement";
      String woRandom = "random select without replacement";
      String sorted = "sorted order";
      String reversed = "reversed order";
      if ((names != null) && (names.length() > 0)) {
        input = new String(names);
      }
      //if(request.getParameter("Try") != null) {
      String[] namesArr = names.split(" ");
      if (request.getParameter("random select") != null) {
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        random = namesArr[i];
        result = result + "Randomly Selected: " + (namesArr[i]) + "\n";
      }
      if(request.getParameter("random select with replacement") != null) {
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        rRandom = namesArr[i];
        result = result + "Randomly Selected: " + (namesArr[i]) + "\n";
      }
      if(request.getParameter("sorted order") != null) {
        Arrays.sort(namesArr);
        sorted = Arrays.toString(namesArr);
        result = result + "Sorted order: " + Arrays.toString(namesArr) + "\n";
      }
      if(request.getParameter("reversed order") != null) {
        Arrays.sort(namesArr, Collections.reverseOrder());
        reversed = Arrays.toString(namesArr);
        result = result + "Reversed order: " + Arrays.toString(namesArr) + "\n";
      }
      //}

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      PrintHead(out);
      PrintBody(out, names, random, rRandom, woRandom, sorted, reversed, result);
      PrintTail(out);
    }

  public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      String input = "";
      String result = "";
      String names = request.getParameter("names");
      String random = "random";
      String rRandom = "random select with replacement";
      String woRandom = "random select without replacement";
      String sorted = "sorted order";
      String reversed = "reversed order";
      if ((names != null) && (names.length() > 0)) {
        input = new String(names);
      }
      //if(request.getParameter("Try") != null) {
      String[] namesArr = input.split(" ");
      if (request.getParameter("random select") != null) {
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        random = namesArr[i];
        result = result + "Randomly Selected: " + (namesArr[i]) + "\n";
      }
      if(request.getParameter("random select with replacement") != null) {
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        rRandom = namesArr[i];
        result = result + "Randomly Selected: " + (namesArr[i]) + "\n";
      }
      if(request.getParameter("sorted order") != null) {
        Arrays.sort(namesArr);
        sorted = Arrays.toString(namesArr);
        result = result + "Sorted order: " + Arrays.toString(namesArr) + "\n";
      }
      if(request.getParameter("reversed order") != null) {
        Arrays.sort(namesArr, Collections.reverseOrder());
        reversed = Arrays.toString(namesArr);
        result = result + "Reversed order: " + Arrays.toString(namesArr) + "\n";
      }
      //}

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      PrintHead(out);
      PrintBody(out, names, random, rRandom, woRandom, sorted, reversed, result);
      PrintTail(out);
    }

  private void PrintHead (PrintWriter out)
  {
    out.println("<html>");
    out.println("");

    out.println("<head>");
    out.println("<title>SWE 432 assignment4</title>");
    out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
    out.println("</head>");
    out.println("");
  }

  private void PrintBody (PrintWriter out, String names, String random, String rRandom, String woRandom, String sorted, String reversed, String result)
  {
    out.println("<body>");
    out.println("<h1>SWE 432</h1>");
    out.println("<h2>By: Du Tran</h2>");
    out.println("<p>Instructions:</p>");
    out.println("<p>The following form will allow you to input a list of names and perform the following 5 actions of your choosing.</p>");
    out.println("<p>You can select multiple options and click the Try button once finished to see the magic happen!</p>");
    out.println("<p>Enter the list of names in the box with each name followed by a space. For example (\"John Karen Sam Allen Tom\")</p>");
    out.println("<div class=\"outer\">");
    out.print("<form id=\"form1\" name=\"nameForm\" method=\"post\"");
    out.println("action=\"https://cs.gmu.edu:8443/offutt/servlet/formHandler\" onsubmit=\"return (checkValid())\">");
    out.println(" Names: <br>");
    out.println(" <input type=\"text\" id=\"name\" name=\"names\" value=\"" + names + "\"><br><br>");
    out.println(" <div class=\"inner\">");
    out.println(" <label for=\"random\">");
    out.println(" <input type=\"checkbox\" id=\"random\" name=\"random select\" value=\"" + random + "\">");
    out.println(" Random select</label><br>");
    out.println(" <label for=\"wreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"wreplacement\" name=\"random select with replacement\" value=\"" + rRandom + "\">");
    out.println(" Random select with replacement</label><br>");
    out.println(" <label for=\"woreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"woreplacement\" name=\"random select without replacement\" value=\"" + woRandom + "\">");
    out.println(" Random select without replacement</label><br>");
    out.println(" <label for=\"sorted\">");
    out.println(" <input type=\"checkbox\" id=\"sorted\" name=\"sorted order\" value=\"" + sorted + "\">");
    out.println(" Sorted order</label><br>");
    out.println(" <label for=\"reversed\">");
    out.println(" <input type=\"checkbox\" id=\"reversed\" name=\"reversed order\" value=\"" + reversed + "\">");
    out.println(" Reversed order</label>");
    out.println(" </div><br><br>");
    out.println(" <input class=\"center\" type=\"button\" value=\"Try\" name=\"Try\">");
    out.println(" <br><br>");
    out.println(" <input class=\"center\" type=\"button\" onclick=\"reset()\" value=\"Clear\">");
    out.println(" <br><br>");
    out.println(" <input class=\"center\" type=\"submit\" value=\"Submit\">");
    out.println(" <br><br>");
    out.println("</form>");
    out.println("</div>");
    out.println("<p id=\"display\" class=\"display\" name=\"display\">" + result + "</p>");
    out.println("<script>");
    out.println(" function reset() {");
    out.println("   document.getElementById(\"form1\").reset();");
    out.println("   document.getElementById(\"display\").innerHTML = \"\";");
    out.println(" }");
    out.println(" function checkValid() {");
    out.println("   var form = document.getElementById(\"form1\");");
    out.println("   if(form.elements[0].value == \"\") {");
    out.println("     alert(\"Please enter a value\");");
    out.println("     return (false);");
    out.println("   }");
    out.println("   return (true);");
    out.println(" }");
    out.println("</script>");
    out.println("</body>");
  }

  private void PrintBody (PrintWriter out)
  {
     PrintBody(out, "", "", "", "", "", "", "");
  }

  private void PrintTail (PrintWriter out)
  {
     out.println("");
     out.println("</html>");
  }

}
