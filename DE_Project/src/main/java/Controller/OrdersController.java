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

            getLocationArea(request, response);
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
            session.setAttribute("provisionals", "0");
            session.setAttribute("feeShips", "0");
            session.setAttribute("totalAmounts", "0");
            return;
        }

        BigDecimal price = calculateTotalPrice(estimatedOrders);

        LocationModel userAddress = cartDAO.getUserAddress(userID);
        LocationModel locationAddress = cartDAO.getLocationAddress(userID);

        int feeShip = 0;
        if (userAddress != null && locationAddress != null
                && userAddress.getDistrict() != null && !userAddress.getDistrict().isEmpty()
                && locationAddress.getDistrict() != null && !locationAddress.getDistrict().isEmpty()) {

            feeShip = calculateShippingFee(userAddress, locationAddress, price);
        }

        BigDecimal feeShipBigDecimal = BigDecimal.valueOf(feeShip);
        BigDecimal totalAmount = price.add(feeShipBigDecimal);
        session.setAttribute("provisionals", price);
        session.setAttribute("feeShips", feeShip);
        session.setAttribute("totalAmounts", totalAmount);
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

    private int calculateShippingFee(LocationModel userAddress, LocationModel locationAddress, BigDecimal price) {
        int feeShip;

        if (!userAddress.getProvince().equalsIgnoreCase(locationAddress.getProvince())) {
            feeShip = 200000; // Phí giao hàng ngoài tỉnh
        } else if (!userAddress.getDistrict().equalsIgnoreCase(locationAddress.getDistrict())) {
            feeShip = 50000;  // Phí giao hàng khác quận
        } else {
            feeShip = 0;       // Giao hàng miễn phí cùng quận
        }

        // Nếu giá trị đơn hàng lớn hơn 300000 thì giảm 40% phí giao hàng
        if (price.compareTo(BigDecimal.valueOf(300000)) > 0) {
            feeShip = (int) (feeShip * 0.6); // Giảm 40% phí giao hàng
        }

        return feeShip;
    }

    private void getLocationArea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession();
        Integer userIDObj = (Integer) request.getSession().getAttribute("userID");
        int userID = (userIDObj != null) ? userIDObj : 0;
        List<LocationModel> locationModels = cartDAO.getLocation(userID);
        String location;
        if (locationModels.isEmpty()) {
            session.setAttribute("location", "Chọn khu vực giao hàng.");
        } else {
            location = "Địa chỉ đã chọn: P. " + locationModels.get(0).getWard()
                    + ", Q. " + locationModels.get(0).getDistrict()
                    + ", TP. " + locationModels.get(0).getProvince();
            session.setAttribute("location", location);
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
        String path = request.getRequestURI();
        String part[] = path.split("/");
        if (part.length >= 4 && part[3].equalsIgnoreCase("AddressInfo")) {
            int thanhPho2 = Integer.parseInt(request.getParameter("thanhPho2"));
            String thanhPho = request.getParameter("thanhPho");
            int quanHuyen2 = Integer.parseInt(request.getParameter("quanHuyen2"));
            String quanHuyen = request.getParameter("quanHuyen");
            int phuongXa2 = Integer.parseInt(request.getParameter("phuongXa2"));
            String phuongXa = request.getParameter("phuongXa");
            HttpSession session = request.getSession();
            int userID = (int) session.getAttribute("userID");
            saveLocation(thanhPho2, thanhPho, quanHuyen2, quanHuyen, phuongXa2, phuongXa, userID);
        }
    }

    private void saveLocation(int thanhPho, String thanhPho2, int quanHuyen, String quanHuyen2, int phuongXa, String phuongXa2, int userID) {
        CartDAO cartDAO = new CartDAO();
        if (!cartDAO.isHaveLocation(userID)) {
            cartDAO.insertLocation(thanhPho, thanhPho2, quanHuyen, quanHuyen2, phuongXa, phuongXa2, userID);
        } else {
            cartDAO.updateLocation(thanhPho, thanhPho2, quanHuyen, quanHuyen2, phuongXa, phuongXa2, userID);
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
