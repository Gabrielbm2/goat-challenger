package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendApprovalEmail(String recipientEmail, Specialization specialization) {
        String subject = "Especialização Aprovada";
        String text = "Sua solicitação de especialização na área " + specialization.getArea() + " foi aprovada.";
        sendEmail(recipientEmail, subject, text);
    }

    @Override
    public void sendRejectionEmail(String recipientEmail, Specialization specialization, String rejectionReason) {
        String subject = "Especialização Rejeitada";
        String text = "Sua solicitação de especialização na área " + specialization.getArea() + " foi rejeitada. Motivo: " + rejectionReason;
        sendEmail(recipientEmail, subject, text);
    }

    private void sendEmail(String recipientEmail, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    @Override
    @Cacheable(value = "specializations", key = "#recipientEmail")
    public Specialization getSpecializationFromCache(String recipientEmail) {
      return null;
    }
}
