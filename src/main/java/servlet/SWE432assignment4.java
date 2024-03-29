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
  static String Style = "http://mason.gmu.edu/~dtran22/mystyle.css";
  ArrayList<String> selected = new ArrayList<String>();

  public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      String names = request.getParameter("names");
      String random = request.getParameter("randomSelect");
      String wReplacement = request.getParameter("replacementRandom");
      String woReplacement = request.getParameter("woReplacementRandom");
      String sorted = request.getParameter("sorted");
      String reversed = request.getParameter("reversed");
      String randomResult = new String();
      String woReplacementResult = new String();
      String wReplacementResult = new String();
      String sortedResult = new String();
      String reversedResult = new String();
      String result = new String();
      if (random != null) {
        String[] namesArr = names.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        randomResult = "Random result: " + namesArr[i];
        result += randomResult + "<br>";
      }
      if(wReplacement != null) {
        String[] namesArr = names.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        if(namesArr.length == selected.size()) {
          wReplacementResult = "Random with replacement result: All names have been selected.";
        }
        else if(selected.contains(namesArr[i])) {
          while(selected.contains(namesArr[i]) == true){
            i = r.nextInt(namesArr.length);
          }
          wReplacementResult = "Random with replacement result: " + namesArr[i];
          selected.add(namesArr[i]);
        }
        else{
          wReplacementResult = "Random with replacement result: " + namesArr[i];
          selected.add(namesArr[i]);
        }
        result += wReplacementResult + "<br>";
      }
      if(woReplacement != null) {
        String[] namesArr = names.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        woReplacementResult = "Random without replacement result: " + namesArr[i];
        result += woReplacementResult + "<br>";
      }
      if(sorted != null) {
        String[] namesArr = names.split(" ");
        Arrays.sort(namesArr);
        sortedResult = "Sorted order result: ";
        for(int i = 0; i < namesArr.length; i++){
          sortedResult += namesArr[i] + " ";
        }
        result += sortedResult + "<br>";
      }
      if(reversed != null) {
        String[] namesArr = names.split(" ");
        Arrays.sort(namesArr, Collections.reverseOrder());
        reversedResult = "Reversed sorted order result: ";
        for(int i = 0; i < namesArr.length; i++){
          reversedResult += namesArr[i] + " ";
        }
        result += reversedResult + "<br>";
      }
      //result = randomResult + "<br>" + wReplacementResult + "<br>" + woReplacementResult + "<br>" + sortedResult + "<br>" + reversedResult;
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      PrintHead(out);
      PrintBody(out, names, result);
      PrintTail(out);
    }

  public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      PrintHead(out);
      PrintBody(out);
      PrintTail(out);
    }

  private void PrintHead (PrintWriter out)
  {
    out.println("<html>");
    out.println("");
    out.println("<head>");
    out.println("<title>SWE 432 assignment4</title>");
    out.println("<style>");
    out.println(" body {");
    out.println("   background-color: lightblue;");
    out.println("   text-align: center; }");
    out.println(" h1 {");
    out.println("   font-size: 55px;");
    out.println("   text-align: center;");
    out.println("   color: navy; }");
    out.println(" h2 {");
    out.println("   margin-top: -35px;");
    out.println("   text-align: center;");
    out.println("   margin-bottom: 30px; }");
    out.println(" p {");
    out.println("   margin: 8px;");
    out.println("   text-align: center; }");
    out.println(" form {");
    out.println("   margin: auto;");
    out.println("   border-style: groove;");
    out.println("   font-size: 20px;");
    out.println("   text-align: center; }");
    out.println(" .inner {");
    out.println("   display: inline-block;");
    out.println("   text-align: left;");
    out.println("   margin-left: 10px; }");
    out.println(" .outer {");
    out.println("   margin: auto;");
    out.println("   width: 50%; }");
    out.println(" .center {");
    out.println("   text-align: center;");
    out.println("   font-size: 15px; }");
    out.println(" .display {");
    out.println("   line-height: 250%;");
    out.println("   margin: 35px;");
    out.println("   font-size: 30px; }");
    out.println("</style>");
    out.println("</head>");
    out.println("");
  }

  private void PrintBody (PrintWriter out, String names, String result)
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
    out.println("action=\"/assignment4\" onsubmit=\"return (checkValid())\">");
    out.println(" Names: <br>");
    out.println(" <input type=\"text\" id=\"name\" name=\"names\" value=\"" + names + "\"><br><br>");
    out.println(" <div class=\"inner\">");
    out.println(" <label for=\"random\">");
    out.println(" <input type=\"checkbox\" id=\"random\" name=\"randomSelect\" value=\"random\">");
    out.println(" Random select</label><br>");
    out.println(" <label for=\"wreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"wreplacement\" name=\"replacementRandom\" value=\"replacement\">");
    out.println(" Random select with replacement</label><br>");
    out.println(" <label for=\"woreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"woreplacement\" name=\"woReplacementRandom\" value=\"woReplacement\">");
    out.println(" Random select without replacement</label><br>");
    out.println(" <label for=\"sorted\">");
    out.println(" <input type=\"checkbox\" id=\"sorted\" name=\"sorted\" value=\"sorted\">");
    out.println(" Sorted order</label><br>");
    out.println(" <label for=\"reversed\">");
    out.println(" <input type=\"checkbox\" id=\"reversed\" name=\"reversed\" value=\"reversed\">");
    out.println(" Reversed order</label><br><br>");
    //out.println(" <input type=\"text\" id=\"result\" name=\"result\" value=\"" + result + "\"><br><br>");
    out.println(" </div><br><br>");
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
     PrintBody(out, "", "");
  }

  private void PrintTail (PrintWriter out)
  {
     out.println("");
     out.println("</html>");
  }

}
