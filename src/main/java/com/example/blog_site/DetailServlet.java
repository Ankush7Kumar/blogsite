package com.example.blog_site;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "listServlet", value = "/list-servlet")
public class DetailServlet extends HttpServlet {

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
        Integer id = Integer.valueOf(request.getParameter("id"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start of the HTML output
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Article Detail</title>");
        out.println("<style>");
        out.println(".navbar { overflow: hidden; background-color: #333; position: fixed; top: 0; width: 100%; z-index: 1000; }");
        out.println(".navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; font-size: 17px; }");
        out.println(".navbar a.right { float: right; }");
        out.println(".navbar a:hover { background-color: #ddd; color: black; }");
        out.println(".card { width: 50%; margin: 70px auto; background-color: #f9f9f9; padding: 20px; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); }");
        out.println(".card button { background-color: #4CAF50; border: none; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; transition-duration: 0.4s; cursor: pointer; }");
        out.println(".card button:hover { background-color: #45a049; }");
        out.println(".card img { max-width: 100%; height: auto; display: block; margin: 0 auto; margin-bottom: 20px; }");
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

        Connection c = getDBCon();
        try {
            PreparedStatement ps = c.prepareStatement("select * from blog_articles where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getString("publish_date");
                String content = rs.getString("content");
                String imageUrl = rs.getString("image_url");

                out.println("<h1>" + title + "</h1>");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    out.println("<img src=\"" + imageUrl + "\"></br></br>");
                }
                out.println("<p><strong>Posted on: </strong>" + date + "</p></br>");
                out.println("<p>" + content + "</p>");
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
}
