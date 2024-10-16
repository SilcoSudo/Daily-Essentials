/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author Yin Kenna
 */
public class AuthenticatorController extends HttpServlet {

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
            out.println("<title>Servlet AuthenticatorController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthenticatorController at " + request.getContextPath() + "</h1>");
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
     * @throws IOException if an I/O error otlo0ccurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String part[] = path.split("/");
        if (part[3].equalsIgnoreCase("Login")) {
            System.out.println("1");
            request.getRequestDispatcher("/View/login.jsp").forward(request, response);
        } else if (part[3].equalsIgnoreCase("Register")) {
            request.getRequestDispatcher("/View/register.jsp").forward(request, response);
        }
        if (part[3].equalsIgnoreCase("Logout")) {
            logout(request, response);
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

        if (part[3].equalsIgnoreCase("Login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác");
                request.getRequestDispatcher("/View/login.jsp").forward(request, response);
            }
            login(username, password, request, response);
        }
        if (part[3].equalsIgnoreCase("Register")) {
            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");
            String gender = request.getParameter("gender");
            if (password.equals(confirmPassword)) {
                AuthenDAO authenDAO = new AuthenDAO();
                boolean isRegistered = authenDAO.registerUser(username, password, fullname, phone, gender);

                if (isRegistered) {
                    request.getRequestDispatcher("/View/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Tên tài khoản đã tồn tại.");
                    request.getRequestDispatcher("/View/register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
                request.getRequestDispatcher("/View/register.jsp").forward(request, response);
            }
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("username");
            session.invalidate();
        }

        Cookie usernameCookie = new Cookie("username", null);
        usernameCookie.setMaxAge(0);
        response.addCookie(usernameCookie);

        response.sendRedirect(request.getContextPath() + "/Login");
    }

    private void register() {

    }

    private void login(String username, String password,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthenDAO authen = new AuthenDAO();
        boolean isLogin = authen.isPassLogin(username, md5Hash(password));
        if (isLogin) {
            HttpSession session = request.getSession();
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(24 * 3 * 60 * 60);
            session.setAttribute("username", username);
            response.addCookie(usernameCookie);

//            System.out.println("Session ID: " + session.getId());
//            System.out.println("Session username: " + session.getAttribute("username"));
//            System.out.println("Cookie name: " + usernameCookie.getName());
//            System.out.println("Cookie value: " + usernameCookie.getValue());
//            System.out.println("Cookie max age: " + usernameCookie.getMaxAge());
            response.sendRedirect(request.getContextPath() + "/Home");
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác");
            request.getRequestDispatcher("/View/login.jsp").forward(request, response);
        }
    }

    public static String md5Hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = data.getBytes();
            byte[] hashBytes = md.digest(dataBytes);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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
