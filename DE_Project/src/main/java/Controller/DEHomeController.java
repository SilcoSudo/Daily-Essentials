/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ViewOrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class DEHomeController extends HttpServlet {

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
            out.println("<title>Servlet DEHomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DEHomeController at " + request.getContextPath() + "</h1>");
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
        String path = request.getRequestURI();
        String part[] = path.split("/");
        if (part.length > 2 && part[2].equalsIgnoreCase("DEHome")) {
            if (part.length > 3 && part[3].equalsIgnoreCase("Manage-Orders")) {
                request.getRequestDispatcher("/View/staffViewOrder.jsp").forward(request, response);
            }
            if (part.length > 3 && part[3].equalsIgnoreCase("Manage-Products")) {
                request.getRequestDispatcher("/View/listAccount.jsp").forward(request, response);
            }
            if (part.length > 3 && part[3].equalsIgnoreCase("Manage-Account")) {
                request.getRequestDispatcher("/View/listAccount.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/View/staffViewStatistics.jsp").forward(request, response);
            }
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

        updateOrderStatus(request, response);
        doGet(request, response);
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

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("order_id");
        String orderStatus = request.getParameter("order_status");

        ViewOrderDAO orderDAO = new ViewOrderDAO();
        boolean updateSuccess = orderDAO.updateOrderStatus(Integer.parseInt(orderId), Integer.parseInt(orderStatus));

        if (updateSuccess) {
            request.setAttribute("successMessage", "Trạng thái đơn hàng đã được cập nhật.");
        } else {
            request.setAttribute("errorMessage", "Cập nhật trạng thái thất bại.");
        }
    }

}
