package api.com.transmission.specializations.services;

import api.com.transmission.specializations.models.Specialization;

public interface EmailService {
    void sendApprovalEmail(String recipientEmail, Specialization specialization);
    void sendRejectionEmail(String recipientEmail, Specialization specialization, String rejectionReason);
    Specialization getSpecializationFromCache(String recipientEmail);
}
