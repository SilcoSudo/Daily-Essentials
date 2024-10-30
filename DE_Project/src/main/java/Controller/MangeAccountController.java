/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import Models.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Qi
 */
public class MangeAccountController extends HttpServlet {

        private AccountDAO accountDAO = new AccountDAO();

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MangeAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MangeAccountController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        
    if ("view".equals(action)) {
        request.setAttribute("action", "view");
        request.getRequestDispatcher("/DEHome/Manage-Account/listAccount.jsp").forward(request, response);
    } else if ("edit".equals(action)) {
        request.setAttribute("action", "edit");
        // Fetch the account details here to pre-fill the form if needed
        request.getRequestDispatcher("/DEHome/Manage-Account/listAccount.jsp").forward(request, response);
    } else {
        // Default to listing all accounts
        listAccounts(request, response);
    }
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            handleSearch(request, response);
        } else if ("updatestaff".equals(action)) {
            updateAccount(request, response);
        } else {
            listAccounts(request, response);
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountId = request.getParameter("search-accountId");
        String status = request.getParameter("status");
        String username = request.getParameter("search-username");

        try {
            // Get the ResultSet from the DAO
            ResultSet rs = accountDAO.getFilteredAccountsResultSet(accountId, status, username);
            request.setAttribute("accountResultSet", rs); // Set ResultSet in the request
            request.getRequestDispatcher("listAccount.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while searching for accounts.");
            request.getRequestDispatcher("listAccount.jsp").forward(request, response);
        }
    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        if (idStr == null || username == null || password == null || fullName == null || phone == null || email == null || role == null || status == null) {
            request.setAttribute("error", "Required fields are missing.");
            listAccounts(request, response);
            return;
        }

        boolean isLocked = "Đã khóa".equals(status);

        Account account = new Account(
                Integer.parseInt(idStr),
                username,
                password,
                fullName,
                phone,
                email,
                role,
                isLocked,
                new java.sql.Date(System.currentTimeMillis())
        );

        boolean success = accountDAO.updateAccount(account);

        if (success) {
            request.getSession().setAttribute("message", "Account updated successfully.");
        } else {
            request.getSession().setAttribute("error", "Error updating account.");
        }

        response.sendRedirect("listAccount.jsp");
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                boolean success = accountDAO.deleteAccount(id);
                if (success) {
                    response.sendRedirect("listAccount.jsp");
                } else {
                    response.getWriter().write("Error deleting account.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid account ID.");
            }
        } else {
            response.getWriter().write("Account ID is required.");
        }
    }

    private void listAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try ( ResultSet rs = accountDAO.getAllAccountsResultSet()) { // Automatically close ResultSet
            request.setAttribute("accountResultSet", rs); // Set ResultSet in request
            request.getRequestDispatcher("listAccount.jsp").forward(request, response); // Forward to JSP
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework for production
            request.setAttribute("error", "An error occurred while retrieving accounts. Please try again later.");
            request.getRequestDispatcher("listAccount.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}