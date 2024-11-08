/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CartDAO;
import DAO.ProductDAO;
import Model.ProductModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class ProductController extends HttpServlet {

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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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
        if (part[2].equalsIgnoreCase("Product")) {
            if (part.length > 3 && part[3].equalsIgnoreCase("ViewAll")) {
                int offset = 15;

                ProductDAO productDAO = new ProductDAO();
                List<ProductModel> productList = productDAO.getRemainingProducts(offset);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(productList);
                response.getWriter().write(jsonResponse);
            }
            if (part.length > 3 && part[3].equalsIgnoreCase("Detail")) {
                int productId = Integer.parseInt(request.getParameter("id"));
                ProductDAO productDAO = new ProductDAO();
                HttpSession session = request.getSession();
                int quantityReal;

                Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
                int userId = (userIdObj != null) ? userIdObj : 0;
                if (userId == 0) {
                    quantityReal = 1;
                } else {
                    quantityReal = Math.max(productDAO.getQuantityRemain(userId, productId), 1);
                }
                List<ProductModel> productDetail = productDAO.getProductDetails(productId);
                int quantiyInWare = productDetail.get(0).getProductQuantity() - quantityReal;
                session.setAttribute("productName", productDetail.get(0).getProductName());
                session.setAttribute("productDetail", productDetail);
                session.setAttribute("productInCart", quantityReal);
                request.getRequestDispatcher("/View/productDetail.jsp").forward(request, response);

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
        String path = request.getRequestURI();
        String part[] = path.split("/");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (part.length > 3 && part[3].equalsIgnoreCase("AddToCart")) {
            CartDAO cartDAO = new CartDAO();
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int productID = Integer.parseInt(request.getParameter("productID"));
            Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
            if (userIdObj == null) {
                response.getWriter().write("Please login to perform this function.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            ProductDAO productDAO = new ProductDAO();
            int quantityProductInWare = productDAO.getQuantityProduct(productID);
            if (quantityProductInWare < quantity) {
                response.getWriter().write("There was an error adding the product. \nPlease come back later.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int userId = userIdObj;
            int quantityProductInCart = cartDAO.getProductQuantityInCart(userId, productID);
            if (quantityProductInCart > 0) {
                if (cartDAO.updateQuantityCartInDetail(userId, productID, quantity)) {
                    out.write("{\"status\":\"success\",\"type\":\"old\"}");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.getWriter().write("There was an error adding the product. \nPlease come back later.");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                cartDAO.insertProductToCart(userId, productID, quantity);
                out.write("{\"status\":\"success\",\"type\":\"old\"}");
                response.setStatus(HttpServletResponse.SC_OK);
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
