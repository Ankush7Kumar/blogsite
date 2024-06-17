package com.example.blog_site;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// This servlet will display a list of all the articles

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class ListServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Articles to Explore";
    }

    public Connection getDBCon() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/blog_site", "root", "bisleriBottle");
            System.out.println("connection established successfully");
        } catch (Exception e) {
            System.out.println("jdbc connection ke exception ke bhitar");
            System.out.println(e);
            e.printStackTrace();
        }
        return con;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Start of the HTML output
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Article List</title>");
        out.println("<style>");
        out.println(".navbar { overflow: hidden; background-color: #333; position: fixed; top: 0; width: 100%; z-index: 1000; }");
        out.println(".navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; font-size: 17px; }");
        out.println(".navbar a.right { float: right; }");
        out.println(".navbar a:hover { background-color: #ddd; color: black; }");
        out.println(".card { width: 50%; margin: 70px auto; background-color: #f9f9f9; padding: 20px; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); }");
        out.println(".card h2 { margin-top: 0; }");
        out.println(".card a { text-decoration: none; color: #4CAF50; }");
        out.println(".card a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Navbar
        out.println("<div class=\"navbar\">");
        out.println("<a href=\"index.jsp\">Home</a>");
        out.println("<a href=\"hello-servlet\" class=\"right\">Explore more articles</a>");
        out.println("</div>");

        // Card
        out.println("<div class=\"card\">");

        out.println("<h1>" + message + "</h1>");

        Connection c = getDBCon();
        try {
            PreparedStatement ps = c.prepareStatement("select * from blog_articles");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getString("publish_date");
                Integer id = rs.getInt("id");
                out.println("<h2>" + title + "</h2>");
                out.println("<a href='list-servlet?id=" + id + "'>Read More</a><br><br>");
            }
        } catch (SQLException e) {
            System.out.println("inside the query's catch");
            throw new RuntimeException(e);
        }

        // End of the card and HTML
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    public void destroy() {
    }
}
