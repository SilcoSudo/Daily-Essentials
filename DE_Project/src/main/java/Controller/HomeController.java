/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import DAO.AuthenDAO;
import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.ProductDAO;
import Model.CategoryModel;
import Model.ProductModel;
import Model.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class HomeController extends HttpServlet {

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
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
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
        if (part[2].equalsIgnoreCase("Home")) {
            if (part.length > 3 && part[3].equalsIgnoreCase("Info")) {
                AccountDAO accountDAO = new AccountDAO();
                String usernameObj = (String) request.getSession().getAttribute("username");
                String username = (usernameObj != null) ? usernameObj : null;
                List<UserProfile> userProfiles = accountDAO.getInfoUser(username);
                HttpSession session = request.getSession();
                session.setAttribute("userProfiles", userProfiles);

                request.getRequestDispatcher("/View/customerInfo.jsp").forward(request, response);
            } else {
                ProductDAO productDAO = new ProductDAO();
                Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
                int userId = (userIdObj != null) ? userIdObj : 0;

                List<ProductModel> listProduct = productDAO.getListProductMax15Item(userId);
                request.setAttribute("productList", listProduct);

                AuthenDAO authenDAO = new AuthenDAO();
                HttpSession session = request.getSession(false);

                String username = (String) session.getAttribute("username");
                CartDAO cartDAO = new CartDAO();
                int userID = authenDAO.getUserIdByUsername(username);
                int totalCartItems = cartDAO.getTotalCartItems(userID);
                session.setAttribute("totalCartItems", totalCartItems);

                CategoryDAO categoryDAO = new CategoryDAO();
                List<CategoryModel> categoryModel = categoryDAO.getFullLabel();
                session.setAttribute("fullLabel", categoryModel);
                request.getRequestDispatcher("/View/homeCus.jsp").forward(request, response);
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
        processRequest(request, response);
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
