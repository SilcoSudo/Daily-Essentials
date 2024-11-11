/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.WarehouseDAO;
import Model.InventoryModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Qi
 */
public class WarehouseController extends HttpServlet {

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
            out.println("<title>Servlet WarehouseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WarehouseController at " + request.getContextPath() + "</h1>");
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
        if (part[3].equalsIgnoreCase("Export")) {
            exportWare(response);
        }
        if (part[3].equalsIgnoreCase("Inventory")) {
            WarehouseDAO wareHouse = new WarehouseDAO();
            List<InventoryModel> inventoryModel = wareHouse.getInventoryList();
            HttpSession session = request.getSession();
            session.setAttribute("inventory", inventoryModel);
            request.getRequestDispatcher("/View/inventoryView.jsp").forward(request, response);
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

    }

    private void exportWare(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"inventory_data.xlsx\"");
        WarehouseDAO wareHouse = new WarehouseDAO();
        List<InventoryModel> inventoryModel = wareHouse.getInventoryList();
        XSSFWorkbook workbook = createExcelWorkbook(inventoryModel);
        try ( ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            System.out.println("exportWare: " + e);
        }

    }

    private XSSFWorkbook createExcelWorkbook(List<InventoryModel> inventoryList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Map<Integer, Sheet> sheetsMap = new HashMap<>();

        for (InventoryModel inventory : inventoryList) {
            LocalDateTime periodDate = inventory.getPeriodDate();
            int year = periodDate.getYear();  // Lấy năm từ periodDate

            Sheet sheet = sheetsMap.get(year);
            if (sheet == null) {
                sheet = workbook.createSheet("Year " + year);
                createHeaderRow(sheet);
                sheetsMap.put(year, sheet);
            }
            addDataToSheet(sheet, inventory);
        }

        for (Sheet currentSheet : sheetsMap.values()) {
            for (int i = 0; i < 10; i++) {
                currentSheet.autoSizeColumn(i);
            }
        }

        return workbook;
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Inventory ID", "Inventory Code", "Product ID", "Period Date", "Beginning Quantity", "Incoming Quantity", "Outgoing Quantity", "Ending Quantity", "Create At"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    private void addDataToSheet(Sheet sheet, InventoryModel inventory) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue(inventory.getInventoryId());
        cell = row.createCell(1);
        cell.setCellValue(inventory.getInventoryCode());
        cell = row.createCell(2);
        cell.setCellValue(inventory.getProductId());
        cell = row.createCell(3);
        cell.setCellValue(inventory.getPeriodDate().toString());
        cell = row.createCell(4);
        cell.setCellValue(inventory.getBeginningQuantity());
        cell = row.createCell(5);
        cell.setCellValue(inventory.getIncomingQuantity());
        cell = row.createCell(6);
        cell.setCellValue(inventory.getOutgoingQuantity());
        cell = row.createCell(7);
        cell.setCellValue(inventory.getEndingQuantity());
        cell = row.createCell(8);
        cell.setCellValue(inventory.getCreateAt().toString());
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
