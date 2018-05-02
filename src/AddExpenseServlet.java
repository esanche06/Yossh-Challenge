import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

//
@WebServlet(name = "AddExpenseServlet", urlPatterns = "/api/add-expense")
public class AddExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/expenses")
    private DataSource dataSource;
    
    private void sendError(HttpServletResponse response) {
        try {
			JsonObject responseJsonObject = new JsonObject();
			responseJsonObject.addProperty("status", "fail");
			responseJsonObject.addProperty("message", "Could not add expense.");
			response.getWriter().write(responseJsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        
        Connection dbCon = null;
        Statement statement = null;
        
        try {
    		System.out.println("Establishing connection to DB...");
	        dbCon = dataSource.getConnection();
    		System.out.println("Connection established.");
            statement = dbCon.createStatement();
            
            String value = request.getParameter("value");
            String expenseDate = request.getParameter("expenseDate");
            String reason = request.getParameter("reason");
            
    		System.out.println(value);
    		System.out.println(expenseDate);
    		System.out.println(reason);
    		
            
            String query = "INSERT INTO expenses(userId, dateOfExpense, value, reason) " + 
            		"VALUES('1','"+expenseDate +"','"+value+"','"+reason+"');";
    		statement.executeUpdate(query);
    		    		
	        JsonObject responseJsonObject = new JsonObject();
	        responseJsonObject.addProperty("status", "success");
	        responseJsonObject.addProperty("message", "Added expense to the DB.");
	
	        response.getWriter().write(responseJsonObject.toString());
        }catch(SQLException se){
        	sendError(response);
            se.printStackTrace();
        }catch(Exception e){
        	sendError(response);
    		e.printStackTrace();
    		
    		out.println("<body>");
    		out.println("<p>");
    		out.println("Exception in doGet: " + e.getMessage());
    		out.println("</p>");
    		out.print("</body>");
        }finally {
        	//finally block used to close resources
            try{
            	if(statement!=null)
            		dbCon.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(dbCon!=null)
                  dbCon.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
        }
    }
}
