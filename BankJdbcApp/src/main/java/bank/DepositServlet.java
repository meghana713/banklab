package bank;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DBUtil.getConnection()) {
            int no = Integer.parseInt(req.getParameter("accNo"));
            double amt = Double.parseDouble(req.getParameter("amount"));
            if (amt <= 0) { Util.respond(resp, "Amount must be positive."); return; }

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE account SET balance = balance + ? WHERE acc_no = ?")) {
                ps.setDouble(1, amt);
                ps.setInt(2, no);
                Util.respond(resp, ps.executeUpdate() > 0 ? "Deposit successful." : "Account not found.");
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
