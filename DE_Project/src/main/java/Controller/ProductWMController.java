package Controller;

import DAO.ProductWMDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;

public class ProductWMController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String productID = request.getParameter("productID");
        String price = request.getParameter("price");
        String label = request.getParameter("label");

        // Create ProductWMDAO object
        ProductWMDAO productWMDAO = new ProductWMDAO();

        // Get the filtered products (no warehouse filtering)
        ResultSet filteredProducts = productWMDAO.getFilteredProducts(productName, productID, price, label);

        // Set the result set in the request attribute
        request.setAttribute("filteredProduct", filteredProducts);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("warehouseList.jsp");
        dispatcher.forward(request, response);
    }

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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
