/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CartDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CartController extends HttpServlet {

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
            out.println("<title>Servlet CartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartController at " + request.getContextPath() + "</h1>");
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
        String[] part = path.split("/");
        if (part[3].equalsIgnoreCase("Add")) {
            addToCard(request, response);
        }
        if (part[3].equalsIgnoreCase("RemoveCart")) {
            removeCart(request, response);
        }
        if (part[3].equalsIgnoreCase("UpdateQuantity")) {
            updateQuantity(request, response);
        }
        if (part[3].equalsIgnoreCase("RemoveItemInCart")) {
            removeItemInCart(request, response);
        }
    }

    private void removeItemInCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int productId = Integer.parseInt(request.getParameter("productId"));
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        boolean isRemove = cartDAO.removeItemInCart(productId, userId);
        if (isRemove) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"success\": true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"success\": false, \"message\": \"\"Error deleting cart. Please try again later!\"}");
        }
        out.flush();

    }

    private void removeCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        CartDAO cartDAO = new CartDAO();
        boolean isRemove = cartDAO.removeCart(userId);
        if (isRemove) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error deleting cart. Please try again later!");

        }
    }

    private void updateQuantity(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession();

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int userId = (int) session.getAttribute("userID");
        boolean isUpdate = cartDAO.updateProductQuantityInCart(productId, userId, quantity);

        if (isUpdate) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"success\": true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"success\": false, \"message\": \"Update failed :(\"}");
        }

        out.flush();
    }

    private void addToCard(HttpServletRequest request, HttpServletResponse respons)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");
        int userID = (int) session.getAttribute("userID");
        if (productIdStr != null && quantityStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);

                CartDAO cartDAO = new CartDAO();
                if (cartDAO.insertProductToCart(userID, productId, quantity)) {
                    respons.setContentType("application/json");
                    respons.setCharacterEncoding("UTF-8");
                    respons.getWriter().write("{\"status\":\"success\"}");
                } else {
                    respons.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    respons.getWriter().write("{\"status\":\"error\", \"message\": \"Invalid for add to cart\"}");
                }

            } catch (NumberFormatException e) {
                respons.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                respons.getWriter().write("{\"status\":\"error\", \"message\": \"Invalid product ID or quantity\"}");
            }
        } else {
            respons.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respons.getWriter().write("{\"status\":\"error\", \"message\": \"Missing product ID or quantity\"}");
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
