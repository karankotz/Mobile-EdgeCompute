package home;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import java.util.logging.Logger;
import home.logic.AccessControl;
import home.logic.HTMLDocument;
import com.edge.http.configuration.ServerConfig;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;

import android.util.Log;
import android.widget.TextView;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.utilities.Utilities;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class N_Queens extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    public static final String RELOCATE_PARAM_NAME = "relocate";
//    private int nQueensLocalNr;
//    private double nQueensLocalTotDur;
//    private int nQueensRemoteNr;
//    private double nQueensRemoteTotDur;
//    private TextView nQueensLocalNrText;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServerConfig serverConfig = (ServerConfig) getServletContext().getAttribute(ServerConfig.class.getName());

        HTMLDocument doc = new HTMLDocument("N_Queens", false);
        doc.setOwnerClass(getClass().getSimpleName());

        doc.writeln("<div class=\"form-unit\">");

        doc.writeln("<h2>N_Queen's Problem-High Computational Analysis</h2>");
        int count = 0;
        String dropdown1="Karan Kamatgi";
        //int N=0;

        String c = "0";
        //String Ns;


        if (request.getMethod().equals(HttpRequestWrapper.METHOD_POST)) {
            LOGGER.fine("Kamatgi reached this page");

            if (request.getPostParameter("value") != null) {
                String Ns = request.getPostParameter("value");
                int N = Integer.parseInt(Ns);

                dropdown1 = request.getPostParameter("computeType");


                if (N > 8 && Objects.equals(dropdown1, new String("SmartCompute")) ) {
                    OkHttpClient client = new OkHttpClient();

                    Request request1 = new Request.Builder()
                            .url("http://app-env-smaple.us-west-2.elasticbeanstalk.com/nqueen?input="+N)
                            .get()
                            .addHeader("Cache-Control", "no-cache")
//                            .addHeader("Postman-Token", "922390c9-49e5-43d6-b718-c04336511a3c")
                            .build();

                    try {
                        Response response1 = client.newCall(request1).execute();
                        c = "From Cloud ";
                        c += response1.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
//--------------------------------------------------------------------------------------------------------

                    N_Queen_helper helper = new N_Queen_helper();
                    int n = helper.main(N);
                    //doc.writeln("<h5>called Queens and number of queens is"+n+"</h5>");
                    c = String.valueOf(n);
                    n = 0;


                }
            }
        }
//---------------------------------------------------------------------------------------------------------
        String location = "/home/N_Queens";
        if (request.getParameter(RELOCATE_PARAM_NAME) != null) {
            location += "?" + RELOCATE_PARAM_NAME + "=" + Utilities.urlEncode(request.getParameter(RELOCATE_PARAM_NAME));
        }
        //count = count + 1;
        String form = "<form action=\""
                + location
                + "\" method=\"post\">\n"
                + "      <input name=\"N_Queens\" type=\"hidden\" value=\"true\" />\n" +
                " <h3> Compute Type</h3>"+


                " <select name=\"computeType\" id=\"computeType\">\n" +

                "        <option value=\"-1\">None</option>\n" +

                "        <option value=\"SmartCompute\">Smart Compute (Offload if necessary)</option>\n" +
                "        <option value=\"Local\">Force Local</option (No Offloading)>\n " +
                " </select>\n"+ "<br />"+


                " <br /><label for=\"N_Queens\" class=\"sr-only\">N_Queens</label>\n" +

                "          <input name=\"value\" type=\"text\" id=\"value\" class=\"form-control\" placeholder=\"Input Value for N_Queens problem\" required autofocus>\n <br />" +



                "<br /><button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Calculate</button>\n" +


                "</form>\n";

        doc.write(form);
        doc.writeln("</div>");
        if (c != "0") {
            doc.write("<br />");
            doc.write("<h4> The total number of Queen's posibilities is " + c + "</h4>");


            //doc.write(dropdown);
            //doc.write(tempInFarhenheit);
        }

        response.getWriter().print(doc.toString());
    }

}

