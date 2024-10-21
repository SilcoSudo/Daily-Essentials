/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ViewOrderDAO;
import Model.OrderHistory;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author nhatl
 */
public class StaffViewOrderController extends HttpServlet {

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
        ViewOrderDAO orders = new ViewOrderDAO();
        List<OrderHistory> orderlist = orders.getAllOrder();
        request.setAttribute("orderlist", orderlist);
        request.getRequestDispatcher("View/staffViewOrder.jsp").forward(request, response);
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

        ViewOrderDAO orders = new ViewOrderDAO();
        List<OrderHistory> orderlist = orders.getAllOrder();
        if (orderlist != null && !orderlist.isEmpty()) {
            request.setAttribute("orderlist", orderlist);
        } else {
            request.setAttribute("errorMessage", "Không có đơn hàng nào.");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("View/staffViewOrder.jsp");
        dispatcher.forward(request, response);

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
        
        String orderId = request.getParameter("order_id");
        String orderStatus = request.getParameter("order_status");

        ViewOrderDAO orderDAO = new ViewOrderDAO();
        boolean updateSuccess = orderDAO.updateOrderStatus(Integer.parseInt(orderId), Integer.parseInt(orderStatus));

        if (updateSuccess) {
            request.setAttribute("successMessage", "Trạng thái đơn hàng đã được cập nhật.");
        } else {
            request.setAttribute("errorMessage", "Cập nhật trạng thái thất bại.");
        }
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

}
