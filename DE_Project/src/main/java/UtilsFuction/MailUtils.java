package UtilsFuction;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtils {
//        jcmo blqf oofz morw

    private String host = "smtp.gmail.com";
    private String senderEmail = "yinkennalt@gmail.com";
    private String password = "jcmo blqf oofz morw";

    public void sendEmail(String recipientEmail, String code)
            throws MessagingException, UnsupportedEncodingException {
        // Cấu hình thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        };

        // Tạo phiên gửi email
        Session session = Session.getInstance(props, auth);

        // Tạo đối tượng tin nhắn
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail, "Daily Esstentials", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Password recovery code");
        message.setHeader("Content-Type", "text/html; charset=UTF-8");

        // Nội dung email
        String emailContent = "Mã khôi phục mật khẩu của bạn là: " + code + "\n"
                + "Lưu ý: mã sẽ hết hạn sau 5 phút.";

        message.setContent(emailContent, "text/plain; charset=UTF-8");

        // Gửi email
        Transport.send(message);
    }
}
