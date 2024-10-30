/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LabelDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Qi
 */
public class LabelController extends HttpServlet {

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
            out.println("<title>Servlet LabelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LabelController at " + request.getContextPath() + "</h1>");
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
        String categoryName = request.getParameter("categoryName");
        String labelName = request.getParameter("labelName");

        LabelDAO labelDAO = new LabelDAO();
        ResultSet filteredLabels = labelDAO.getFilteredLabels(categoryName, labelName);

        request.setAttribute("filteredLabels", filteredLabels);

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
        String action = request.getParameter("action");  // Lấy action để phân biệt "add" hoặc "update"
        LabelDAO labelDAO = new LabelDAO();

// Phân biệt theo action
        if ("add".equalsIgnoreCase(action)) {
            String labelName = request.getParameter("labelName");
            String categoryType = request.getParameter("labelCategory");

            if (labelName != null && !labelName.trim().isEmpty() && categoryType != null && !categoryType.trim().isEmpty()) {
                labelDAO.addLabel(labelName, categoryType);  // Thực hiện thêm nhãn vào DB qua DAO

                // Sau khi thêm thành công, chuyển hướng với thông báo thành công
                response.sendRedirect("warehouseList.jsp?successMessage=Nhãn đã được thêm thành công.");
            } else {
                // Nếu trường trống, hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "Vui lòng điền tất cả các trường.");
                request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
            }
        } else if ("update".equalsIgnoreCase(action)) {  // So sánh không phân biệt hoa thường
            // Cập nhật nhãn hiện tại
            try {
                int labelId = Integer.parseInt(request.getParameter("labelId")); // Lấy ID của nhãn cần cập nhật
                String updatedCategory = request.getParameter("labelCategory");
                String updatedLabelName = request.getParameter("labelName");

                if (updatedCategory != null && !updatedCategory.trim().isEmpty() && updatedLabelName != null && !updatedLabelName.trim().isEmpty()) {
                    // Gọi DAO để cập nhật nhãn
                    boolean isUpdated = labelDAO.updateLabel(labelId, updatedCategory, updatedLabelName);  // Gọi phương thức updateLabel trong LabelDAO

                    if (isUpdated) {
                        response.sendRedirect("warehouseList.jsp?successMessage=Nhãn đã được cập nhật thành công.");
                    } else {
                        request.setAttribute("errorMessage", "Cập nhật nhãn thất bại. Vui lòng thử lại.");
                        request.getRequestDispatcher("labelList.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Vui lòng điền tất cả các trường.");
                    request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID nhãn không hợp lệ.");
                request.getRequestDispatcher("labelList.jsp").forward(request, response);
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
