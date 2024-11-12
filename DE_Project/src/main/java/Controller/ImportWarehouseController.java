package Controller;

import DAO.ProductDAO;
import DAO.ProductWMDAO;
import DAO.LabelDAO;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig
public class ImportWarehouseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // Lấy phần đường dẫn phụ

        if (pathInfo != null) {
            switch (pathInfo) {
                case "/import":
                    request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
                    break;
                case "/search":
                    handleSearch(request, response);
                    break;
                case "/manageLabels":
                    handleLabelSearch(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/View/warehouseList.jsp").forward(request, response);
                    break;
            }
        } else {
            request.getRequestDispatcher("/View/warehouseList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            switch (pathInfo) {
                case "/update":
                    handleUpdate(request, response);
                    break;
                case "/delete":
                    handleDelete(request, response);
                    break;
                case "/importExcel":
                    handleImportExcel(request, response);
                    break;
                case "/manageLabels":
                    handleLabelActions(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Phương thức xử lý tìm kiếm sản phẩm
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String productID = request.getParameter("productID");
        String price = request.getParameter("price");
        String label = request.getParameter("label");

        ProductWMDAO productWMDAO = new ProductWMDAO();
        ResultSet filteredProducts = productWMDAO.getFilteredProducts(productName, productID, price, label);

        request.setAttribute("filteredProduct", filteredProducts);
        request.getRequestDispatcher("/View/warehouseList.jsp").forward(request, response);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            String productDescription = request.getParameter("productDescription");
            BigDecimal productPrice = new BigDecimal(request.getParameter("productPrice"));
            int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
            String labelName = request.getParameter("labelName");
            String categoryName = request.getParameter("categoryName");
            String imageUrl = request.getParameter("imageUrl");

            ProductWMDAO productWMDAO = new ProductWMDAO();

            // Nếu imageUrl là null hoặc rỗng, lấy imageUrl hiện tại từ cơ sở dữ liệu
            if (imageUrl == null || imageUrl.isEmpty()) {
                String currentImageUrl = productWMDAO.getImageUrlById(productId); // Thêm phương thức này trong DAO
                imageUrl = currentImageUrl != null ? currentImageUrl : ""; // Giữ nguyên imageUrl hiện tại nếu có
            }

            // In log các giá trị
            System.out.println("Received data for update:");
            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Description: " + productDescription);
            System.out.println("Price: " + productPrice);
            System.out.println("Quantity: " + productQuantity);
            System.out.println("Label: " + labelName);
            System.out.println("Category: " + categoryName);
            System.out.println("Image URL: " + imageUrl);

            // Thực hiện cập nhật
            boolean success = productWMDAO.updateProduct(productId, productName, productDescription, productPrice,
                    productQuantity, labelName, categoryName, imageUrl);

            if (success) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from request: " + e.getMessage());
            response.getWriter().write("failure");
        } catch (Exception e) {
            System.err.println("An error occurred during update: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().write("failure");
        }
    }

    // Phương thức xử lý xóa sản phẩm
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String productIdParam = request.getParameter("productId");

        if (productIdParam == null || productIdParam.isEmpty()) {
            response.getWriter().write("failure");
            System.err.println("Product ID is missing or invalid.");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);
            ProductWMDAO productWMDAO = new ProductWMDAO();
            boolean success = productWMDAO.deleteProduct(productId);

            response.getWriter().write(success ? "success" : "failure");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().write("failure");
        }
    }

    // Phương thức xử lý nhập sản phẩm từ Excel
    private void handleImportExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("productExcel");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("errorMessage", "Please select an Excel file.");
            request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
            return;
        }

        List<Product> products = new ArrayList<>();
        try ( InputStream fileContent = filePart.getInputStream();  Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Product product = new Product();
                product.setProductName(row.getCell(0).getStringCellValue());
                product.setProductPrice(BigDecimal.valueOf(row.getCell(1).getNumericCellValue()));
                product.setProductSku(row.getCell(2).getStringCellValue());
                product.setProductQuantity((int) row.getCell(3).getNumericCellValue());
                product.setProductDescription(row.getCell(4).getStringCellValue());
                product.setImgUrl(row.getCell(5).getStringCellValue());
                product.setCategoryId((int) row.getCell(6).getNumericCellValue());
                product.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                products.add(product);
            }

            ProductDAO productDAO = new ProductDAO();
            for (Product product : products) {
                productDAO.addProduct(product);
            }

            response.sendRedirect(request.getContextPath() + "/DEHome/Manage-Products");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while importing products.");
            request.getRequestDispatcher("/View/importWarehouse.jsp").forward(request, response);
        }
    }

    // Phương thức xử lý tìm kiếm nhãn
    private void handleLabelSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String labelName = request.getParameter("labelName");

        LabelDAO labelDAO = new LabelDAO();
        ResultSet filteredLabels = labelDAO.getFilteredLabels(categoryName, labelName);

        request.setAttribute("filteredLabels", filteredLabels);
        request.getRequestDispatcher("/View/warehouseList.jsp").forward(request, response);
    }

    // Phương thức xử lý thêm và cập nhật nhãn
    private void handleLabelActions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        LabelDAO labelDAO = new LabelDAO();

        if ("add".equalsIgnoreCase(action)) {
            String labelName = request.getParameter("labelName");
            String categoryType = request.getParameter("categoryName");

            if (labelName != null && !labelName.trim().isEmpty() && categoryType != null && !categoryType.trim().isEmpty()) {
                labelDAO.addLabel(labelName, categoryType);
                response.sendRedirect("warehouseList.jsp?successMessage=Label added successfully.");
            } else {
                request.setAttribute("errorMessage", "Please fill all fields.");
                request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
            }

        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int labelId = Integer.parseInt(request.getParameter("labelId"));
                String updatedCategory = request.getParameter("categoryName");
                String updatedLabelName = request.getParameter("labelName");

                if (updatedCategory != null && !updatedCategory.trim().isEmpty() && updatedLabelName != null && !updatedLabelName.trim().isEmpty()) {
                    boolean isUpdated = labelDAO.updateLabel(labelId, updatedCategory, updatedLabelName);

                    if (isUpdated) {
                        response.sendRedirect("warehouseList.jsp?successMessage=Label updated successfully.");
                    } else {
                        request.setAttribute("errorMessage", "Update failed. Please try again.");
                        request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Please fill all fields.");
                    request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid label ID.");
                request.getRequestDispatcher("warehouseList.jsp").forward(request, response);
            }
        }
    }
}
