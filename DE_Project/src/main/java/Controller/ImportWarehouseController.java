/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductWMDAO;
import DAO.WarehouseDAO;
import Model.Product;
import Model.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qi
 */
public class ImportWarehouseController extends HttpServlet {

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
            out.println("<title>Servlet ImportWarehouseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportWarehouseController at " + request.getContextPath() + "</h1>");
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
        // Thiết lập mã hóa cho request để đọc được các ký tự đặc biệt
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin kho hàng từ form
        String warehouseCode = request.getParameter("warehouseCode");
        String warehouseName = request.getParameter("warehouseName");
        String warehouseAddress = request.getParameter("warehouseAddress");

        if (warehouseCode == null || warehouseCode.isEmpty()) {
            request.setAttribute("errorMessage", "Mã kho không được để trống");
            request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
            return;
        }

        // Lấy danh sách sản phẩm từ form
        List<Product> productList = new ArrayList<>();
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] productSKUs = request.getParameterValues("sku");
        String[] productQuantities = request.getParameterValues("productQuantity");
        String[] productCategories = request.getParameterValues("category");
        String[] productLabels = request.getParameterValues("label");

        if (productNames == null || productNames.length == 0) {
            request.setAttribute("errorMessage", "Cần phải nhập ít nhất một sản phẩm");
            request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
            return;
        }

        // Tạo danh sách sản phẩm từ dữ liệu đã nhập
        try {
            for (int i = 0; i < productNames.length; i++) {
                if (productNames[i] != null && !productNames[i].trim().isEmpty()) {
                    String name = productNames[i];
                    BigDecimal price = null;
                    try {
                        price = new BigDecimal(productPrices[i]);
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Giá sản phẩm phải là số hợp lệ");
                        request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
                        return;
                    }

                    String sku = productSKUs[i];
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(productQuantities[i]);
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Số lượng sản phẩm phải là số nguyên hợp lệ");
                        request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
                        return;
                    }

                    String category = productCategories[i];
                    String label = productLabels[i];

                    Product product = new Product();
                    product.setProductName(name);
                    product.setProductPrice(price);
                    product.setProductSku(sku);
                    product.setProductQuantity(quantity);
                    product.setLabelName(label);
                    product.setWarehouseCode(warehouseCode);

                    productList.add(product);
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi xử lý thông tin sản phẩm");
            request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
            return;
        }

        // Lưu thông tin vào cơ sở dữ liệu
        try {
            WarehouseDAO warehouseDAO = new WarehouseDAO();
            Warehouse warehouse = warehouseDAO.getWarehouseByCode(warehouseCode);

            if (warehouse == null) {
                warehouse = new Warehouse(warehouseCode, warehouseName, warehouseAddress, 0, "Loại kho mặc định", "Hoạt động");
                warehouseDAO.addWarehouse(warehouse);
            }

            ProductWMDAO productWMDAO = new ProductWMDAO();
            for (Product product : productList) {
                productWMDAO.addProduct(product);
            }

            response.sendRedirect("warehouseList.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi lưu thông tin vào cơ sở dữ liệu");
            request.getRequestDispatcher("importWarehouse.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
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
