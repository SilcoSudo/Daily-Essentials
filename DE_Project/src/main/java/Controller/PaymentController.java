/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CartDAO;
import DAO.OrdersDAO;
import Model.LocationModel;
import Model.ProductModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class PaymentController extends HttpServlet {

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
            out.println("<title>Servlet PaymentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
        int userId = (userIdObj != null) ? userIdObj : 0;

        if (part.length >= 4 && part[3].equalsIgnoreCase("pay")) {
            OrdersDAO ordersDAO = new OrdersDAO();
            CartDAO cartDAO = new CartDAO();

            // Lấy sản phẩm trong giỏ hàng
            List<ProductModel> productsInCart = cartDAO.getProductInCart(userId);

            // Kiểm tra giỏ hàng trống
            if (productsInCart == null || productsInCart.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Không có sản phẩm trong giỏ hàng.");
                return;
            }

            // Tính tổng số tiền hàng
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (ProductModel product : productsInCart) {
                BigDecimal productTotal = product.getProductPrice().multiply(BigDecimal.valueOf(product.getQuantityInCart()));
                totalAmount = totalAmount.add(productTotal);
            }

            // Lấy phí vận chuyển từ session
            LocationModel userAddress = cartDAO.getUserAddress(userId);
            LocationModel locationAddress = cartDAO.getLocationAddress(userId);

            if (userAddress == null || userAddress.getDistrict() == null || userAddress.getDistrict().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Lỗi trong quá trình thanh toán. \nVui lòng cập nhật địa chỉ cá nhân.");
                return;
            }
            if (locationAddress == null || locationAddress.getDistrict() == null || locationAddress.getDistrict().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Lỗi trong quá trình thanh toán. \nVui lòng cập nhật địa chỉ khu vực giao hàng.");
                return;
            }
            Object feeShipObj = request.getSession().getAttribute("feeShips");
            BigDecimal feeShip = (feeShipObj != null) ? BigDecimal.valueOf(((Number) feeShipObj).doubleValue()) : BigDecimal.ZERO;

            // Gọi hàm xử lý thanh toán với transaction
            boolean success = ordersDAO.processPaymentTransaction(userId, totalAmount, productsInCart, feeShip);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Thanh toán thành công.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Lỗi trong quá trình thanh toán. Vui lòng thử lại.");
            }
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
