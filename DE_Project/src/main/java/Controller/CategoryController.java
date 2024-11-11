/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenDAO;
import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.LabelDAO;
import Model.CategoryModel;
import Model.ProductModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryController extends HttpServlet {

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
            out.println("<title>Servlet CategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

            Set<String> uniLabel = new HashSet<>();
            for (ProductModel e : searchProducts) {
                uniLabel.add(e.getLabelName());
            }

            Map<String, List<CategoryModel>> labelCategoryMap = new HashMap<>();

            for (String label : uniLabel) {
                List<CategoryModel> categoryList = categoryDAO.getCategoriesByLabelName(label);
                labelCategoryMap.put(label, categoryList);
            }
            session.setAttribute("labelCategoryMap", labelCategoryMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\"}");

        }
        if (part[3].equalsIgnoreCase("View")) {
            AuthenDAO authenDAO = new AuthenDAO();
            HttpSession session = request.getSession(false);
            CartDAO cartDAO = new CartDAO();
            String username = (String) session.getAttribute("username");
            int userID = authenDAO.getUserIdByUsername(username);
            int totalCartItems = cartDAO.getTotalCartItems(userID);
            session.setAttribute("totalCartItems", totalCartItems);
            request.getRequestDispatcher("/View/category.jsp").forward(request, response);
        } else if ("filterLabels".equalsIgnoreCase(action)) {
            handleLabelFilter(request, response);
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
        String action = request.getParameter("action");

        if ("addLabel".equalsIgnoreCase(action)) {
            handleAddLabel(request, response);
        } else if ("updateLabel".equalsIgnoreCase(action)) {
            handleUpdateLabel(request, response);
        } else if ("searchByCategory".equalsIgnoreCase(action)) {
            handleSearchByCategory(request, response);
        }
    }

        if (part[3].equalsIgnoreCase("Search")) {
            int labelID = Integer.parseInt(request.getParameter("categoryId"));
            Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
            int userId = (userIdObj != null) ? userIdObj : 0;
            HttpSession session = request.getSession();
            CategoryDAO categoryDAO = new CategoryDAO();
            List<ProductModel> productList;
            List<ProductModel> productHaveInCart;

            productList = categoryDAO.getProductByCategoryID(labelID);

            productHaveInCart = categoryDAO.getProductInCartWhenSearch(productList, userId);
            for (ProductModel e : productList) {
                for (ProductModel j : productHaveInCart) {
                    if (j.getProductId() == e.getProductId()) {
                        e.setQuantityInCart(j.getQuantityInCart());
                    }
                }
            }
        }
        session.setAttribute("productList", searchProducts);

        Set<String> uniLabel = new HashSet<>();
        for (ProductModel e : searchProducts) {
            uniLabel.add(e.getLabelName());
        }

        Map<String, List<CategoryModel>> labelCategoryMap = new HashMap<>();
        for (String label : uniLabel) {
            List<CategoryModel> categoryList = categoryDAO.getCategoriesByLabelName(label);
            labelCategoryMap.put(label, categoryList);
        }
        session.setAttribute("labelCategoryMap", labelCategoryMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"success\"}");
    }

    private void handleLabelFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String labelName = request.getParameter("labelName");

        LabelDAO labelDAO = new LabelDAO();
        ResultSet filteredLabels = labelDAO.getFilteredLabels(categoryName, labelName);

        request.setAttribute("filteredLabels", filteredLabels);
        request.getRequestDispatcher("/DEHome/Manage-Products/warehouseList.jsp").forward(request, response);
    }

    private void handleAddLabel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LabelDAO labelDAO = new LabelDAO();
        String labelName = request.getParameter("labelName");
        String categoryType = request.getParameter("categoryName");

        if (labelName != null && !labelName.trim().isEmpty() && categoryType != null && !categoryType.trim().isEmpty()) {
            labelDAO.addLabel(labelName, categoryType);
            response.sendRedirect("warehouseList.jsp?successMessage=Label successfully added.");
        } else {
            request.setAttribute("errorMessage", "Please fill in all fields.");
            request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
        }
    }

    private void handleUpdateLabel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            LabelDAO labelDAO = new LabelDAO();
            int labelId = Integer.parseInt(request.getParameter("labelId"));
            String updatedCategory = request.getParameter("categoryName");
            String updatedLabelName = request.getParameter("labelName");

            if (updatedCategory != null && !updatedCategory.trim().isEmpty() && updatedLabelName != null && !updatedLabelName.trim().isEmpty()) {
                boolean isUpdated = labelDAO.updateLabel(labelId, updatedCategory, updatedLabelName);
                if (isUpdated) {
                    response.sendRedirect("warehouseList.jsp?successMessage=Label successfully updated.");
                } else {
                    request.setAttribute("errorMessage", "Failed to update label. Please try again.");
                    request.getRequestDispatcher("/DEHome/Manage-Products/warehouseList.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Please fill in all fields.");
                request.getRequestDispatcher("/DEHome/Manage-Products/warehouseList.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid label ID.");
            request.getRequestDispatcher("/DEHome/Manage-Products/warehouseList.jsp").forward(request, response);
        }
    }

    private void handleSearchByCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryID = Integer.parseInt(request.getParameter("categoryId"));
        Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
        int userId = (userIdObj != null) ? userIdObj : 0;
        HttpSession session = request.getSession();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<ProductModel> productList = categoryDAO.getProductByCategoryID(categoryID);
        List<ProductModel> productHaveInCart = categoryDAO.getProductInCartWhenSearch(productList, userId);

        for (ProductModel e : productList) {
            for (ProductModel j : productHaveInCart) {
                if (j.getProductId() == e.getProductId()) {
                    e.setQuantityInCart(j.getQuantityInCart());
                }
            }
        }
        session.setAttribute("productList", productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"success\"}");
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
