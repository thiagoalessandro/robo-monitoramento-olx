package br.com.intelector.gerenciarcontapagar.service.mail;

import br.com.intelector.gerenciarcontapagar.configuration.ApplicationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;

@Log4j2
public abstract class MailSenderAbstract {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ApplicationProperties applicationProperties;

    @Value("classpath:template/email.html")
    Resource fileLayoutEmail;

    private String subject;

    public MailSenderAbstract(String subject) {
        this.subject = subject;
    }

    protected abstract String htmlContent();

    protected boolean sendEmail(String to, Map<String, String> templateValues) throws IOException {
        MimeMessage msg;
        MimeMessageHelper helper;
        boolean sended = false;
        try {
            msg = javaMailSender.createMimeMessage();
            helper = new MimeMessageHelper(msg, true);
            helper.setFrom(applicationProperties.getAppEmailFrom());
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject(this.subject);
            helper.setText(textHtml(templateValues), true);
            javaMailSender.send(msg);
            sended = true;
            log.info("E-mail enviado para {}", to);
        } catch (MessagingException e) {
            log.error(e);
        }
        return sended;
    }

    private String layoutEmail() throws IOException {
        String layoutEmail = new String(toByteArray(fileLayoutEmail.getInputStream()));
        return inicializeVariablesDefault(layoutEmail);

    }

    private String inicializeVariablesDefault(String layoutEmail) {
        layoutEmail = layoutEmail.replaceAll("#EMAIL_TITLE", subject);
        return layoutEmail;
    }

    private String textHtml(Map<String, String> templateValues) throws IOException {
        String layoutEmail = layoutEmail();
        String htmlContent = htmlContent();
        String preHtmlContent;
        for (Map.Entry<String, String> entry : templateValues.entrySet()) {
            String preValue =  Matcher.quoteReplacement(entry.getValue());
            htmlContent = htmlContent.replaceAll(entry.getKey(), preValue);
        }
        preHtmlContent =  Matcher.quoteReplacement(htmlContent);
        return layoutEmail.replaceAll("#EMAIL_CONTENT", preHtmlContent);
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

}
