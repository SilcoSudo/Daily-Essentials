package Controller;

import DAO.ProductDAO;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class ImportWarehouseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // Lấy phần đường dẫn phụ

        if (pathInfo != null && pathInfo.equals("/import")) {
            // Điều hướng đến trang nhập kho
            request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
        } else {
            // Điều hướng đến trang quản lý sản phẩm mặc định
            request.getRequestDispatcher("/View/warehouseList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Nhận dữ liệu sản phẩm từ request
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String sku = request.getParameter("sku");
        String productQuantity = request.getParameter("productQuantity");
        String productDescription = request.getParameter("productDescription");
        String productImage = request.getParameter("productImage");
        String categoryId = request.getParameter("categoryId");

        // Thêm thông báo lỗi
        StringBuilder errorMessage = new StringBuilder();

        // Log dữ liệu nhận được để kiểm tra
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("SKU: " + sku);

        // Kiểm tra dữ liệu đầu vào của từng trường
        if (isInvalidInput(productName)) {
            errorMessage.append("Please add at least one product name. ");
        }
        if (isInvalidInput(productPrice) || isInvalidNumber(productPrice)) {
            errorMessage.append("Product price is required and must be a valid number. ");
        }
        if (isInvalidInput(sku)) {
            errorMessage.append("SKU is required. ");
        }
        if (isInvalidInput(productQuantity) || isInvalidNumber(productQuantity)) {
            errorMessage.append("Product quantity is required and must be a valid number. ");
        }
        if (isInvalidInput(categoryId) || isInvalidNumber(categoryId)) {
            errorMessage.append("Category ID is required and must be a valid number. ");
        }

        // Nếu có lỗi, quay lại trang nhập kho với thông báo lỗi
        if (errorMessage.length() > 0) {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
            return;
        }

        // Nếu hợp lệ, lưu dữ liệu vào cơ sở dữ liệu
        ProductDAO productDAO = new ProductDAO();
        try {
            // Tạo đối tượng Product và lưu vào cơ sở dữ liệu
            Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(new BigDecimal(productPrice));
            product.setProductSku(sku);
            product.setProductQuantity(Integer.parseInt(productQuantity));
            product.setProductDescription(productDescription);
            product.setImgUrl(productImage);
            product.setCategoryId(Integer.parseInt(categoryId));
            product.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            // Thêm sản phẩm vào cơ sở dữ liệu
            productDAO.addProduct(product);

            // Điều hướng về danh sách sản phẩm sau khi nhập thành công
            response.sendRedirect(request.getContextPath() + "/DEHome/Manage-Products");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while importing products.");
            request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
        }
    }

    private boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    private boolean isInvalidNumber(String input) {
        try {
            new BigDecimal(input); // Kiểm tra nếu giá trị có thể chuyển thành BigDecimal
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
