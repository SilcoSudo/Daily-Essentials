/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filter;

import DAO.AuthenDAO;
import DAO.CartDAO;
import DAO.CategoryDAO;
import Model.CategoryModel;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class Fillter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public Fillter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("Fillter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("Fillter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("Fillter:doFilter()");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        System.out.println("path url: " + path);
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);
        boolean hasValidCookie = false;
        if (httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                if ("username".equals(cookie.getName())) {
                    hasValidCookie = true;
                    break;
                }
            }
        }
        if (path != null) {
            if (path.contains("/Component/CSS/") || path.contains("/Component/JS/") || path.contains("/Component/IMG/")
                    || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")
                    || path.endsWith(".jpeg") || path.endsWith(".svg") || path.endsWith(".gif")) {
                chain.doFilter(request, response);
                return;
            }
            if (path.endsWith("/Authen/Login") || path.endsWith("/Authen/forgot_changepass") || path.endsWith("/Authen/forgot_otp") || path.endsWith("/Authen/forgot") || path.endsWith("/Authen/forgot_otp-2 ")) {
                chain.doFilter(request, response);
                return;
            }

            if (path.endsWith("/Authen/Register")) {
                chain.doFilter(request, response);
                return;
            }

            if (path.endsWith("/Authen/ForgotPassword")) {
                chain.doFilter(request, response);
                return;
            }

            if (path.endsWith("/Login") || path.endsWith("/login")) {
                if (!path.contains("/Authen/")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/Authen/Login");
                    return;
                }
            }

            if (path.endsWith("/")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/Home");
                return;
            }

            if (path.endsWith("/Register") || path.endsWith("/register")) {
                if (!path.contains("/Authen/")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/Authen/Register");
                    return;
                }
            }

            if (path.endsWith("/ForgotPassword") || path.endsWith("/forgotpassword")) {
                if (!path.contains("/Authen/")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/Authen/ForgotPassword");
                    return;
                }
            }

            if (path.endsWith("/Home")) {
                chain.doFilter(request, response);
                return;
            }
            if (path.endsWith("/Product/ViewAll") || path.endsWith("/Product/Detail") || path.endsWith("/Product/AddToCart")) {
                chain.doFilter(request, response);
                return;
            }
            if (path.endsWith("/Category/View") || path.endsWith("/Category/Search")) {
                chain.doFilter(request, response);
                return;
            }

            if (path.startsWith("/Home/") && !path.equals("/Home")) {
                if (!isLoggedIn && !hasValidCookie) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/Authen/Login");
                    return;
                }
            }
        }
        if (isLoggedIn) {
            AuthenDAO authenDAO = new AuthenDAO();
            String username = (String) session.getAttribute("username");
            CartDAO cartDAO = new CartDAO();
            int userID = authenDAO.getUserIdByUsername(username);
            int totalCartItems = cartDAO.getTotalCartItems(userID);
            session.setAttribute("totalCartItems", totalCartItems);
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryModel> categoryModel = categoryDAO.getFullLabel();
            session.setAttribute("fullLabel", categoryModel);

        } else {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryModel> categoryModel = categoryDAO.getFullLabel();
            session.setAttribute("fullLabel", categoryModel);
        }

        if (!isLoggedIn && !hasValidCookie) {
            if (path.contains("/Cart/Add")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/Authen/Login");
                return;
            }
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("Fillter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Fillter()");
        }
        StringBuffer sb = new StringBuffer("Fillter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
