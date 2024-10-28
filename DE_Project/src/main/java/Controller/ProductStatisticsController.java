/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductStatisticsDAO;
import Model.ProductStatistics;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhatl
 */
public class ProductStatisticsController extends HttpServlet {

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

        request.getRequestDispatcher("View/staffViewStatistics.jsp").forward(request, response);

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
        ProductStatisticsDAO products = new ProductStatisticsDAO();

        //total
        ProductStatistics total_revenue = products.getTotalRevenue();
        if (total_revenue != null) {
            request.setAttribute("totalRevenue", total_revenue.getTotal_revenue());
        } else {
            System.out.println("Không có dữ liệu hoặc xảy ra lỗi.");
        }

        //top product
        List<ProductStatistics> topSellingProducts = products.getTopSellingProducts(10);
        request.setAttribute("topSellingProducts", topSellingProducts);

        //get order status
        List<ProductStatistics> orderstatus = products.getOrderStatusStatistics();

        if (orderstatus != null && !orderstatus.isEmpty()) {
            request.setAttribute("orderStatistics", orderstatus);
        } else {
            request.setAttribute("errorMessage", "Không có dữ liệu thống kê nào.");
        }

        //sort cate
        List<ProductStatistics> categoryList = products.getAllCategories();
        request.setAttribute("categoryList", categoryList);

        String categoryIdParam = request.getParameter("category_id");
        List<ProductStatistics> productlist = new ArrayList<>();

        if (categoryIdParam != null && !categoryIdParam.isEmpty() && !categoryIdParam.equals("0")) {
            int categoryId = Integer.parseInt(categoryIdParam);
            productlist = products.getProductStatisticsByCategory(categoryId);
        } else {
            productlist = products.getAllProductStatistics();
        }

        if (productlist != null && !productlist.isEmpty()) {
            request.setAttribute("productlist", productlist);
        } else {
            request.setAttribute("errorMessage", "Không có sản phẩm nào.");
        }

        request.getRequestDispatcher("View/staffViewStatistics.jsp").forward(request, response);
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
        doGet(request, response);
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
