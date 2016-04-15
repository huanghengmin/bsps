package com.hzih.ssl;


import org.apache.log4j.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: wxh
 * Date: 2005-8-15
 * Time: 20:17:53
 * To change this template use File | Settings | File Templates.
 */
public class Service {
    public static Category s_log = null;

    public Service() {
        s_log = Category.getInstance(Service.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>vpn  Service Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");
        writer.println("<table border=\"0\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<h1>Hzih Vpn main Status Page</h1>");
        writer.println("<P>vpn  service is running.<P><BR>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>vpn  Service Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");
        writer.println("<table border=\"0\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<h1>Hzih Vpn main Status Page</h1>");
        writer.println("<P>vpn  service is running.<P><BR>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }


    public void init(String path, String fileName) throws ServletException {

    }
}
