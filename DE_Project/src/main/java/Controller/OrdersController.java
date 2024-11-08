/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CartDAO;
import Model.LocationModel;
import Model.ProductModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class OrdersController extends HttpServlet {

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
            out.println("<title>Servlet OrdersController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrdersController at " + request.getContextPath() + "</h1>");
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
        String[] part = path.split("/");

        if (part.length >= 3 && part[2].equalsIgnoreCase("Orders")) {
            CartDAO cartDAO = new CartDAO();
            Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
            int userId = (userIdObj != null) ? userIdObj : 0;
            List<ProductModel> cartProducts = cartDAO.getAllProductCart(userId);

            setEstimatedOrders(request, response);

            request.setAttribute("cartProducts", cartProducts);
            request.getRequestDispatcher("/View/Orders.jsp").forward(request, response);
        }
    }

    private void setEstimatedOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession();
        Integer userIDObj = (Integer) session.getAttribute("userID");
        int userID = (userIDObj != null) ? userIDObj : 0;
        List<ProductModel> estimatedOrders = cartDAO.getProductInCart(userID);
        if (estimatedOrders == null || estimatedOrders.isEmpty()) {
            session.setAttribute("totalAmounts", "0"); 
            return;
        }

        BigDecimal price = calculateTotalPrice(estimatedOrders);

        session.setAttribute("totalAmounts", price);
    }

    private BigDecimal calculateTotalPrice(List<ProductModel> estimatedOrders) {
        BigDecimal price = BigDecimal.ZERO;
        for (ProductModel product : estimatedOrders) {
            BigDecimal quantity = BigDecimal.valueOf(product.getQuantityInCart());
            BigDecimal productPrice = product.getProductPrice() != null ? product.getProductPrice() : BigDecimal.ZERO;
            BigDecimal productTotal = productPrice.multiply(quantity);
            price = price.add(productTotal);
        }
        return price;
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
        String path = request.getRequestURI();
        String part[] = path.split("/");
        
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
