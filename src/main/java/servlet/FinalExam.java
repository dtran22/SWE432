import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "finalexam",
        urlPatterns = {"/finalexam"}
    )

public class FinalExam extends HttpServlet
{
  public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      String numbers = request.getParameter("Numbers");
      String mean = request.getParameter("Mean");
      String median = request.getParameter("Median");
      String mode = request.getParameter("Mode");
      String standardD = request.getParameter("StandardD");
      String meanResult = new String();
      String medianResult = new String();
      String modeResult = new String();
      String standardDResult = new String();
      String result = new String();
      String error = "The following could not be added as it is not an integer: ";
      String[] strArr = numbers.split(" ");
      int[] numArr = new int[strArr.length];
      int index = 0;
      int z = 0;
      for(int i = 0; i < strArr.length; i++) {
        try {
          numArr[index] = Integer.parseInt(strArr[i]);
          index++;
        }
        catch(NumberFormatException exception) {
          error += strArr[i] + " ";
        }
      }
      numArr = Arrays.copyOf(numArr, index);
      result = "Your inputs are: ";
      for(z = 0; z < numArr.length - 1; z++) {
        String str = String.valueOf(numArr[z]);
        result += numArr[z] + ", ";
      }
      String last = String.valueOf(numArr[z]);
      result += numArr[z];
      result += "<br>";
      if(error.equals("The following could not be added as it is not an integer: ") == false) {
        result += error + "<br>";
      }
      double meanValue = 0.0;
      for(int i = 0; i < numArr.length; i++) {
        meanValue += numArr[i];
      }
      meanValue = ((double) meanValue)/(numArr.length);

      if (mean != null) {
        String str = String.valueOf(meanValue);
        meanResult = "Mean result: " + str;
        result += meanResult + "<br>";
      }
      if(median != null) {
        int n = numArr.length;
        Arrays.sort(numArr);
        if(numArr.length%2 != 0){
          double medianValue = (double) numArr[n/2];
          String str = String.valueOf(medianValue);
          medianResult = "Median result: " + str;
        }
        else {
          double medianValue = (numArr[(n-1)/2] + numArr[n/2])/2.0;
          String str = String.valueOf(medianValue);
          medianResult = "Median result: " + str;
        }
        result += medianResult + "<br>";
      }
      if(mode != null) {
        int modeValue = 0;
        int max = 0;
        for(int i = 0; i < numArr.length; i++) {
          int count = 0;
          for(int j = 0; j < numArr.length; j++) {
            if(numArr[j] == numArr[i]) {
              count++;
            }
          }
          if(count > max) {
            max = count;
            modeValue = numArr[i];
          }
        }
        if(max == 1) {
          modeResult = "There are no recurrences in your data, so there are is no mode.";
        }
        else {
          String str = String.valueOf(modeValue);
          modeResult = "Mode result: " + str;
        }
        result += modeResult + "<br>";
      }
      if(standardD != null) {
        double sum = 0.0;
        for(int i = 0; i < numArr.length; i++) {
          sum += (((double)numArr[i]) - meanValue) * (((double)numArr[i]) - meanValue);
        }
        sum = sum / numArr.length;
        double sd = Math.sqrt(sum);
        String str = String.valueOf(sd);
        standardDResult = "Standard Deviation result: " + str;
        result += standardDResult + "<br>";
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      PrintHead(out);
      PrintBody(out, numbers, result);
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
    out.println("<title>SWE 432 Final Exam</title>");
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
    out.println("   font-size: 18px;");
    out.println("   margin: 8px;");
    out.println("   text-align: center; }");
    out.println(" form {");
    out.println("   margin: auto;");
    out.println("   padding-top: 10px;");
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

  private void PrintBody (PrintWriter out, String numbers, String result)
  {
    out.println("<body>");
    out.println("<h1>SWE 432 Final Exam</h1>");
    out.println("<h2>By: Du Tran</h2>");
    out.println("<p>Instructions:</p>");
    out.println("<p>The following form will allow you to input a list of integers to calculate the mean, median, mode, and standard deviation.</p>");
    out.println("<p>Enter a list of integers in the box with each one followed by a space. For example (\"1 2 3 4 5\")</p><br>");
    out.println("<div class=\"outer\">");
    out.print("<form id=\"form1\" name=\"nameForm\" method=\"post\"");
    out.println("action=\"/finalexam\" onsubmit=\"return (checkValid())\">");
    out.println(" Integers: <br>");
    out.println(" <input type=\"text\" id=\"numbers\" name=\"Numbers\" value=\"" + numbers + "\"><br><br>");
    out.println(" <div class=\"inner\">");
    out.println(" <label for=\"mean\">");
    out.println(" <input type=\"checkbox\" id=\"mean\" name=\"Mean\" value=\"mean\">");
    out.println(" Mean</label><br>");
    out.println(" <label for=\"median\">");
    out.println(" <input type=\"checkbox\" id=\"median\" name=\"Median\" value=\"median\">");
    out.println(" Median</label><br>");
    out.println(" <label for=\"mode\">");
    out.println(" <input type=\"checkbox\" id=\"mode\" name=\"Mode\" value=\"mode\">");
    out.println(" Mode</label><br>");
    out.println(" <label for=\"standardD\">");
    out.println(" <input type=\"checkbox\" id=\"standardD\" name=\"StandardD\" value=\"standardD\">");
    out.println(" Standard Deviation</label><br>");
    out.println(" </div><br><br>");
    out.println(" <input class=\"center\" type=\"button\" onclick=\"location.href='http://localhost:5000/finalexam';\" value=\"Reset\">");
    out.println(" <br><br>");
    out.println(" <input class=\"center\" type=\"submit\" value=\"Submit\">");
    out.println(" <br><br>");
    out.println("</form>");
    out.println("</div>");
    out.println("<p id=\"display\" class=\"display\" name=\"display\">" + result + "</p>");
    out.println("<script>");
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
