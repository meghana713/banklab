package bank;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DBUtil.getConnection()) {
            int no = Integer.parseInt(req.getParameter("accNo"));
            double bal = Double.parseDouble(req.getParameter("balance"));
            if (bal < 0) { Util.respond(resp, "Opening balance cannot be negative."); return; }

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO account (acc_no, name, balance) VALUES (?, ?, ?)")) {
                ps.setInt(1, no);
                ps.setString(2, req.getParameter("name"));
                ps.setDouble(3, bal);
                ps.executeUpdate();
                Util.respond(resp, "Account " + no + " created.");
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
