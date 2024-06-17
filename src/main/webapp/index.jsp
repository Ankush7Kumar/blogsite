<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>



<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <style>
    .navbar {
      overflow: hidden;
      background-color: #333;
      position: fixed; /* Fixed at the top */
      top: 0;
      width: 100%;
      z-index: 1000;
    }

    .navbar a {
      float: left;
      display: block;
      color: white;
      text-align: center;
      padding: 14px 20px;
      text-decoration: none;
      font-size: 17px;
    }

    .navbar a.right {
      float: right;
    }

    .navbar a:hover {
      background-color: #ddd;
      color: black;
    }

    .card {
      width: 50%;
      margin: 70px auto; /* Center the card and add margin for the navbar */
      background-color: #f9f9f9;
      padding: 20px;
      box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    }

    .card button {
      background-color: #4CAF50; /* Green */
      border: none;
      color: white;
      padding: 10px 20px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      font-size: 16px;
      margin: 4px 2px;
      transition-duration: 0.4s;
      cursor: pointer;
    }

    .card button:hover {
      background-color: #45a049; /* Darker Green */
    }

    .card img {
      max-width: 100%;
      height: auto;
      display: block;
      margin: 0 auto;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>

<div class="navbar">
  <a href="#">Home</a>
  <a href="hello-servlet" class="right">Explore more articles</a>
</div>

<div class="card">
  <%
    String date = "null";
    String title = "null";
    String content = "null";
    String imageUrl = "null";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/blog_site", "root", "bisleriBottle");
      System.out.println("connection established successfully");

      PreparedStatement ps = con.prepareStatement("select * from blog_articles where id = (select max(id) from blog_articles)");
      System.out.println("ps done");

      ResultSet rs = ps.executeQuery();
      System.out.println("rs done");
      rs.next();
      date = rs.getString("publish_date");
      title = rs.getString("title");
      content = rs.getString("content");
      imageUrl = rs.getString("image_url");
      System.out.println("values done");

    } catch (Exception e) {
      System.out.println("jdbc connection ke exception ke bhitar");
      System.out.println(e);
      e.printStackTrace();
    }
  %>

  <h1><%= title %></h1>
  <img src="<%= imageUrl %>">
  <p><strong>Published on: </strong><%= date %></p>
  <p><%= content %></p>
</div>

</body>
</html>
