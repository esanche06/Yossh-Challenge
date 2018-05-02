import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ShowExpensesServlet", urlPatterns = "/api/show-expenses")
public class ShowExpensesServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;

	@Resource(name = "jdbc/expenses")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json"); 

		PrintWriter out = response.getWriter();

		try {
			Connection dbcon = dataSource.getConnection();

			String query = "SELECT dateOfExpense, value, reason FROM expenses;";

			PreparedStatement statement = dbcon.prepareStatement(query);

			ResultSet rs = statement.executeQuery();

			JsonArray jsonArray = new JsonArray();

			while (rs.next()) {

				String dateOfExpense = rs.getString("dateOfExpense");
				String value = rs.getString("value");
				String reason = rs.getString("reason");


				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("dateOfExpense", dateOfExpense);
				jsonObject.addProperty("value", value);
				jsonObject.addProperty("reason", reason);

				jsonArray.add(jsonObject);
			}
			
            out.write(jsonArray.toString());
            response.setStatus(200);

			rs.close();
			statement.close();
			dbcon.close();
		} catch (Exception e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorMessage", e.getMessage());
			out.write(jsonObject.toString());

			response.setStatus(500);
		}
		out.close();

	}

}
