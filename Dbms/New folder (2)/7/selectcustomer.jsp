<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
        <title>Movie Nights</title>
    </head>
    <body>
        <%@page import="jsp_azure_test.Hand"%>
        <%@page import="java.sql.ResultSet"%>
        <%
            // We instantiate the data handler here, and get all the movies from the database
            final Hand handler = new Hand();
            final ResultSet movies = handler.selectcustomer();
        %>
        <!-- The table for displaying all the movie records -->
        <table cellspacing="2" cellpadding="2" border="1">
            <tr> <!-- The table headers row -->
              <td align="center">
                <h4>cname</h4>
              </td>
              <td align="center">
                <h4>caddress</h4>
              </td>
              <td align="center">
                <h4>category</h4>
              </td>
              
             
            </tr>
            <%
               while(movies.next()) { // For each movie_night record returned...
                   // Extract the attribute values for every row returned
                   final String cname = movies.getString("cname");
                   final String caddress = movies.getString("caddress");
                   final String category = movies.getString("category");
                  
                   
                   out.println("<tr>"); // Start printing out the new table row
                   out.println( // Print each attribute value
                        "<td align=\"center\">" + cname +
                        "</td><td align=\"center\"> " + caddress +
                        "</td><td align=\"center\"> " + category +
                     "</td>");
                   out.println("</tr>");
               }
               %>
          </table>
    </body>
</html>
