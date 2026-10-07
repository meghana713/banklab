package bank;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DBUtil.getConnection()) {
            int no = Integer.parseInt(req.getParameter("accNo"));
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT name, balance FROM account WHERE acc_no = ?")) {
                ps.setInt(1, no);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        Util.respond(resp, rs.getString("name") + " - Balance: " + rs.getDouble("balance"));
                    else
                        Util.respond(resp, "Account not found.");
                }
            }
        } catch (NumberFormatException e) {
            Util.respond(resp, "Invalid number entered.");
        } catch (SQLIntegrityConstraintViolationException e) {
            Util.respond(resp, "Account number already exists.");
        } catch (SQLException e) {
            Util.respond(resp, "Database error: " + e.getMessage());
        }
    }
}
