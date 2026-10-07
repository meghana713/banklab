package bank;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DBUtil.getConnection()) {
            int no = Integer.parseInt(req.getParameter("accNo"));
            double amt = Double.parseDouble(req.getParameter("amount"));
            if (amt <= 0) { Util.respond(resp, "Amount must be positive."); return; }

            // Atomic check: only updates if balance is sufficient
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE account SET balance = balance - ? WHERE acc_no = ? AND balance >= ?")) {
                ps.setDouble(1, amt);
                ps.setInt(2, no);
                ps.setDouble(3, amt);
                if (ps.executeUpdate() > 0) { Util.respond(resp, "Withdrawal successful."); return; }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM account WHERE acc_no = ?")) {
                ps.setInt(1, no);
                try (ResultSet rs = ps.executeQuery()) {
                    Util.respond(resp, rs.next() ? "Insufficient balance." : "Account not found.");
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
