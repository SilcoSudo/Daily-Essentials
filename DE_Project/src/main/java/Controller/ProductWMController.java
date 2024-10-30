/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductWMDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Qi
 */
public class ProductWMController extends HttpServlet {

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
            out.println("<title>Servlet ProductWMController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductWMController at " + request.getContextPath() + "</h1>");
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

        String productName = request.getParameter("productName");
        String productID = request.getParameter("productID");
        String price = request.getParameter("price");
        String label = request.getParameter("label");
        String warehouse = request.getParameter("warehouse");

        // Create ProductWMDAO object
        ProductWMDAO productWMDAO = new ProductWMDAO();

        // Get the filtered products
        ResultSet filteredProducts = productWMDAO.getFilteredProducts(productName, productID, price, label, warehouse);

        // Set the result set in the request attribute
        request.setAttribute("filteredProduct", filteredProducts);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("warehouseList.jsp");
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
        String action = request.getParameter("action");

        if ("save".equals(action)) {
            // Extract parameters from the request
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            String productDescription = request.getParameter("productDescription");
            BigDecimal productPrice = new BigDecimal(request.getParameter("productPrice"));
            int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
            String labelName = request.getParameter("labelName"); // Changed to labelName
            String categoryName = request.getParameter("categoryName"); // Changed to categoryName
            String imageUrl = request.getParameter("imageUrl");

            // Debugging: Log the extracted parameters
            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Product Description: " + productDescription);
            System.out.println("Product Price: " + productPrice);
            System.out.println("Product Quantity: " + productQuantity);
            System.out.println("Label Name: " + labelName);
            System.out.println("Category Name: " + categoryName);
            System.out.println("Image URL: " + imageUrl);

            ProductWMDAO productWMDAO = new ProductWMDAO();
            boolean success = productWMDAO.updateProduct(productId, productName, productDescription, productPrice,
                    productQuantity, labelName, categoryName, imageUrl);

            if (success) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
                System.out.println("Update failed for product ID: " + productId);
            }
         } else if ("delete".equals(action)) { // Add this delete action block
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductWMDAO productWMDAO = new ProductWMDAO();

            boolean success = productWMDAO.deleteProduct(productId); // Call the deleteProduct method

            if (success) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
                System.out.println("Delete failed for product ID: " + productId);
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
