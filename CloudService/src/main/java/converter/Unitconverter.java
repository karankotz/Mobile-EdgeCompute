package converter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Unitconverter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Double input = Double.parseDouble(req.getParameter("input"));
        String type =  req.getParameter("typeofconversion");
        String result = convert(input, type);
       // resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
    /*    out.println("<html>");
        out.println("<head>");
        out.println("<title>Hola</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<h1>Result :: "+result+"<h1>");
        out.println("<a href=\"convert.jsp\">Back<a>");
        out.println("</body>");
        out.println("</html>");*/
        /**out.println("<script type=\"text/javascript\">");
        out.println("alert('Result: "+result+"');");
        out.println("location='convert.jsp';");
        out.println("</script>");**/
        out.println(result);
    }


    public String convert(Double t, String type){
        double convertedOutput=0.0;
        String c="0";

            if (type.equals("f2c")){
                convertedOutput=(t-32)*0.55556;
            }

            else if(type.equals("c2f")){
                convertedOutput=(t*1.8)+32;
            }

            else if(type.equals("l2g")){
                convertedOutput=(t*0.264);
            }

            else if(type.equals("g2l")){
                convertedOutput=(t*3.7854);
            }
            else if(type.equals("k2m")){
                convertedOutput=(t*0.62137);
            }
            else if(type.equals("m2k")) {
                convertedOutput = (t * 1.60934);
            }

            else if(type.equals("w2h")){
                convertedOutput=( t * 168);

            }
            double roundOff = Math.round(convertedOutput*100)/100D;

            c = Double.toString(roundOff);

        return c;
    }
}
