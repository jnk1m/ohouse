package com.ohouse.ohouse.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneValidationService {
  private final String ACCOUNT_SID;
  private final String AUTH_TOKEN;
  private final String VERIFY_SERVICE_ID;

  public PhoneValidationService(@Value("${twilio.account_sid}") String account_sid,
                                @Value("${twilio.auth_token}") String auth_token,
                                @Value("${twilio.verify_service_id}") String verify_service_id) {
    this.ACCOUNT_SID = account_sid;
    this.AUTH_TOKEN = auth_token;
    this.VERIFY_SERVICE_ID = verify_service_id;

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  public void sendVerificationCode(String phoneNumber) {
//    Verification verification = Verification.creator(
//            VERIFY_SERVICE_ID,
//            phoneNumber,
//            "sms"
//    ).create();
//    System.out.println("Sent verification: "+ verification.getSid());

    Message message = Message
            .creator(
                    new PhoneNumber("+15558675309"),
                    new PhoneNumber(phoneNumber),
                    "Ohouse Message Test"
            )
            .create();

    System.out.println(message.getSid());
  }

  public void verifyPhoneNumber(String phoneNumber, String code) {
  }
}
