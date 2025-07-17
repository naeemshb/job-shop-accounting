<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add to customer</title>
    </head>
    <body>
        <h2>Add to customer</h2>
        <!--
            Form for collecting user input for the new movie_night record.
            Upon form submission, add_movie.jsp file will be invoked.
        -->
        <form action="add_movie.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Enter the customer Data:</th>
                </tr>
                <tr>
                    <td>cname:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=cname>
                    </div></td>
                </tr>
                <tr>
                    <td>caddress:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=caddress>
                    </div></td>
                </tr>
                <tr>
                    <td>category:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=category>
                    </div></td>
                </tr>
               
                <tr>
                    <td><div style="text-align: center;">
                    <input type=reset value=Clear>
                    </div></td>
                    <td><div style="text-align: center;">
                    <input type=submit value=Insert>
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
