/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenDAO;
import DAO.CartDAO;
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
import UtilsFuction.*;
import jakarta.mail.MessagingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
            request.getRequestDispatcher("/View/login.jsp").forward(request, response);
        } else if (part[3].equalsIgnoreCase("Register")) {
            request.getRequestDispatcher("/View/register.jsp").forward(request, response);
        } else if (part[3].equalsIgnoreCase("ForgotPassword")) {
            request.getRequestDispatcher("/View/ForgotPassword.jsp").forward(request, response);
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
            login(request, response);
        }
        if (part[3].equalsIgnoreCase("Register")) {
            register(request, response);
        }

        if (part[3].equalsIgnoreCase("forgot")) {
            String email = request.getParameter("email");
            AuthenDAO authenDAO = new AuthenDAO();
            if (email.isEmpty()) {
                request.setAttribute("errorMessage", "Không được để trống.");
                request.getRequestDispatcher("/View/ForgotPassword.jsp").forward(request, response);
                return;
            }
            int accoutnID = authenDAO.getAccountID(email);
            if (accoutnID != -1) {
                try {
                    String resetCode = String.valueOf((int) (Math.random() * 900000) + 100000);
                    authenDAO.insertCode(accoutnID, resetCode);
                    forgotpassword(request, response, email, resetCode);
                } catch (MessagingException ex) {
                    System.out.println("Error: " + ex);
                }
                request.setAttribute("email", email);
                request.getRequestDispatcher("/View/ForgotPasswordOTP.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMessage", "Vui lòng nhập email chính xác!");
                request.getRequestDispatcher("/View/ForgotPassword.jsp").forward(request, response);
                return;
            }
        }

        if (part[3].equalsIgnoreCase("forgot_otp")) {
            String email = request.getParameter("email");
            String codeOTP = request.getParameter("otp");
            AuthenDAO authenDAO = new AuthenDAO();
            int accountID = authenDAO.getAccountID(email);
            String codeValue = authenDAO.getLatestCodeValue(accountID);
            if (codeOTP.isEmpty()) {
                request.setAttribute("accountID", accountID);
                request.setAttribute("errorMessage", "Nhập lại mã");
                request.getRequestDispatcher("/View/ForgotPasswordOTP.jsp").forward(request, response);
                return;
            }
            if (codeOTP.equals(codeValue)) {
                request.setAttribute("accountID", accountID);
                request.getRequestDispatcher("/View/ForgotPassword_ChangePass.jsp").forward(request, response);
            }
        }
        if (part[3].equalsIgnoreCase("forgot_changepass")) {
            int accountID = Integer.parseInt(request.getParameter("accountID"));
            String pass = request.getParameter("pass");
            AuthenDAO authenDAO = new AuthenDAO();
            if (authenDAO.updatePassword(accountID, md5Hash(pass))) {
                response.sendRedirect(request.getContextPath() + "/Login");
                return;
            } else {
                request.setAttribute("idacc", accountID);
                request.getRequestDispatcher("/View/ForgotPassword_ChangePass.jsp").forward(request, response);
                return;
            }
        }
        if (part[3].equalsIgnoreCase("forgot_otp-2")) {
            AuthenDAO authenDAO = new AuthenDAO();
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            String resetCode = String.valueOf((int) (Math.random() * 900000) + 100000);
            String email = (String) request.getAttribute("email");
            int accoutnID = authenDAO.getAccountID(email);
            authenDAO.insertCode(accoutnID, resetCode);
            try {
                forgotpassword(request, response, email, resetCode);
            } catch (MessagingException ex) {
                System.out.println("Error: " + ex);
            }
            if (true) {
                out.print("{\"status\": \"success\"}");
            } else {
                out.print("{\"status\": \"error\"}");
            }
            out.flush();
        }
    }

    private void forgotpassword(HttpServletRequest request, HttpServletResponse response, String recipientEmail, String resetCode)
            throws ServletException, IOException, MessagingException {
        MailUtils email = new MailUtils();
        try {
            email.sendEmail(recipientEmail, resetCode);
        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        response.sendRedirect(request.getContextPath() + "/Login");
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String gender = request.getParameter("gender");
        boolean genders = gender.equals("male");
        if (password.equals(confirmPassword)) {
            AuthenDAO authenDAO = new AuthenDAO();
            boolean isRegistered = authenDAO.registerUser(username, md5Hash(password), fullname, phone, genders);
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

    private void login(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthenDAO authen = new AuthenDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác");
            request.getRequestDispatcher("/View/login.jsp").forward(request, response);
            return;
        }
        boolean isLogin = authen.isPassLogin(username, md5Hash(password));
        String fullNameUser = authen.getFullNameUser(username);
        int userID = authen.getUserIdByUsername(username);

        if (isLogin) {
            HttpSession session = request.getSession();
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
            Cookie usernameCookie = new Cookie("username", encodedUsername);
            usernameCookie.setMaxAge(24 * 3 * 60 * 60); // 3 days
            usernameCookie.setPath("/");
            usernameCookie.setHttpOnly(true);

            session.setAttribute("username", username);
            session.setAttribute("userID", userID);

            session.setAttribute("userFullName", fullNameUser);
            response.addCookie(usernameCookie);
            
            CartDAO cartDAO = new CartDAO();
            int totalCartItems = cartDAO.getTotalCartItems(userID);
            session.setAttribute("totalCartItems", totalCartItems);
            
//            System.out.println("Session ID: " + session.getId());
//            System.out.println("Session username: " + session.getAttribute("username"));
//            System.out.println("Session userID: " + session.getAttribute("userID"));
//            System.out.println("Cookie name: " + usernameCookie.getName());
//            System.out.println("Cookie value: " + usernameCookie.getValue());
//            System.out.println("Cookie max age: " + usernameCookie.getMaxAge());

            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác");
            request.getRequestDispatcher("/View/login.jsp").forward(request, response);
            return;
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
