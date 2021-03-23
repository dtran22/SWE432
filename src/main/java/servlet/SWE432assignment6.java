package servlet;
import java.io.*;
import java.util.*;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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
  static enum Data {NAMES, RESULT};
  static string RESOURCE_FILE = "names.json";

  static String Domain = "";
  static String Path = "/";
  static String Servlet = "json";

  static String OperationAdd = "Add";
  static String Style = "http://mason.gmu.edu/~dtran22/mystyle.css";

  public class Entry {
    String names;
    String result;
  }

  public class Entries {
    List<Entry> entries;
  }

  public class EntryManager{
    private String filePath = null;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Entries save(String names, String result) {
      Entries entries = getAll();
      Entry newEntry = new Entry();
      newEntry.names = names;
      newEntry.result = result;
      entries.entries.add(newEntry);
      try {
        FileWriter fileWriter = new FileWriter(filePath);
        new Gson().toJson(entries, fileWriter);
        fileWriter.flush();
        fileWriter.close();
      }catch(IOException ioException){
        return null;
      }
      return entries;
    }

    private Entries getAll() {
      Entries entries = entries = new Entries();
      entries.entries = new ArrayList();

      try {
        File file = new File(filePath);
        if(!file.exists()){
          return entries;
        }

        BufferedReader bufferedReader =
          new BufferedReader(new FileReader(file));
        Entries readEntries =
          new Gson().fromJson(bufferedReader, Entries.class);

        if(readEntries != null && readEntries.entries != null){
          entries = readEntries;
        }
        bufferedReader.close();
      }
      catch(IOException ioException) {
      }
      return entries;
    }

    public String getAllAsHTMLTable(Entries entries) {
      StringBuilder htmlOut = new StringBuilder("<table>");
      htmlOut.append("<tr><th>Names</th><th>Result</th></tr>");
      if(entries == null
          || entries.entries == null || entries.entries.size() == 0){
        htmlOut.append("<tr><td>No entries yet.</td></tr>");
      }else{
        for(Entry entry: entries.entries){
           htmlOut.append(
           "<tr><td>"+entry.names+"</td><td>"+entry.result+"</td></tr>");
        }
      }
      htmlOut.append("</table>");
      return htmlOut.toString();
    }
  }

  public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
      String names = request.getParameter(Data.NAME.name());
      String result = new String();

      String error = "";
      if(names == null) {
        error = "<li>The name field is required</li>";
        names = "";
        result = "";
      }
      else{

      //if (request.getParameter("randomSelect") != null) {
        String[] names = nameStr.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
        result = namesArr[i];
      }
      //}
      /**
      if(request.getParameter("replacementRandom") != null) {
        String[] namesArr = names.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
      }
      if(request.getParameter("woReplacementRandom") != null) {
        String[] namesArr = names.split(" ");
        Random r = new Random();
        int i = r.nextInt(namesArr.length);
      }
      if(request.getParameter("sorted") != null) {
        String[] namesArr = names.split(" ");
        Arrays.sort(namesArr);
      }
      if(request.getParameter("reversed") != null) {
        String[] namesArr = names.split(" ");
        Arrays.sort(namesArr, Collections.reverseOrder());
      }**/
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      if(error.length() == 0) {
        EntryManager entryManager = new EntryManager();
        entryManager.setFilePath(RESOURCE_FILE);
        Entries newEntries = entryManager.save(names, result);

        printHead(out);
        if(newEntires == null) {
          error += "<li>Could not save entry.</li>";
          printBody(out, names, result, error);
        }
        else {
          printResponseBody(out, entryManager.getAllAsHTMLTable(newEntries));
        }
        printTail(out);
      }
      else {
        printHead(out);
        printBody(out, names, result, error);
        printTail(out);
      }
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
    out.println("   margin: 35px;");
    out.println("   font-size: 30px; }");
    out.println("</style>");
    out.println("</head>");
    out.println("");
  }

  private void PrintBody (PrintWriter out, String names, String result, String error)
  {
    out.println("<body>");
    out.println("<h1>SWE 432</h1>");
    out.println("<h2>By: Du Tran</h2>");
    out.println("<p>Instructions:</p>");
    out.println("<p>The following form will allow you to input a list of names and perform the following 5 actions of your choosing.</p>");
    out.println("<p>You can select multiple options and click the Try button once finished to see the magic happen!</p>");
    out.println("<p>Enter the list of names in the box with each name followed by a space. For example (\"John Karen Sam Allen Tom\")</p>");
    if(error != null && error.length() > 0){
      out.println(
      "<p style=\"color:red;\">Please correct the following and resubmit.</p>"
      );
      out.println("<ol>");
      out.println(error);
      out.println("</ol>");
    }
    out.println("<div class=\"outer\">");
    out.print("<form id=\"form1\" name=\"nameForm\" method=\"post\"");
    out.println("action=\""+Domain+Path+Servlet+"\" onsubmit=\"return (checkValid())\">");
    out.println(" Names: <br>");
    out.println(" <input type=\"text\" id=\"name\" name=\"" + Data.NAMES.name() + "\" value=\"" + names + "\"><br><br>");
    out.println(" <div class=\"inner\">");
    out.println(" <label for=\"random\">");
    out.println(" <input type=\"checkbox\" id=\"random\" name=\"randomSelect\" value=\"randomSelect\">");
    out.println(" Random select</label><br>");
    out.println(" <label for=\"wreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"wreplacement\" name=\"replacementRandom\" value=\"replacementRandom\">");
    out.println(" Random select with replacement</label><br>");
    out.println(" <label for=\"woreplacement\">");
    out.println(" <input type=\"checkbox\" id=\"woreplacement\" name=\"woReplacementRandom\" value=\"woReplacementRandom\">");
    out.println(" Random select without replacement</label><br>");
    out.println(" <label for=\"sorted\">");
    out.println(" <input type=\"checkbox\" id=\"sorted\" name=\"sorted\" value=\"sorted\">");
    out.println(" Sorted order</label><br>");
    out.println(" <label for=\"reversed\">");
    out.println(" <input type=\"checkbox\" id=\"reversed\" name=\"reversed\" value=\"reversed\">");
    out.println(" Reversed order</label><br><br>");
    out.println(" <input type=\"text\" id=\"result\" name=\"" + Data.RESULT.name() + "\" value=\"" + result + "\"><br><br>");
    out.println(" </div><br><br>");
    out.println(" <input class=\"center\" type=\"button\" onclick=\"reset()\" value=\"Clear\">");
    out.println(" <br><br>");
    out.println(" <input class=\"center\" type=\"submit\" value=\"Submit\">");
    out.println(" <br><br>");
    out.println("</form>");
    out.println("</div>");
    //out.println("<p id=\"display\" class=\"display\" name=\"display\">" + result + "</p>");
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

  private void printResponseBody (PrintWriter out, String tableString){
    out.println("<body>");
    out.println("<p>");
    out.println("A simple example that shows entries from a JSON file");
    out.println("</p>");
    out.println("");
    out.println(tableString);
    out.println("");
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
