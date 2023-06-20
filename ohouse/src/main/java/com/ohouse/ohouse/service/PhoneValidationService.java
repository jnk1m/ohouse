package com.ohouse.ohouse.service;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneValidationService {
  private final String ACCOUNT_SID;
  private final String AUTH_TOKEN;
  private final String SERVICE_SID;

  public PhoneValidationService(@Value("${twilio.account_sid}") String account_sid,
                                @Value("${twilio.auth_token}") String auth_token,
                                @Value("${twilio.service_sid}") String service_sid) {
    this.ACCOUNT_SID = account_sid;
    this.AUTH_TOKEN = auth_token;
    this.SERVICE_SID = service_sid;

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    com.twilio.rest.verify.v2.Service service = com.twilio.rest.verify.v2.Service.creator("My First Verify Service").create();

  }

  public void sendVerification(String phoneNumber) {
    Verification verification = Verification.creator(
                    SERVICE_SID,
                    phoneNumber,
                    "sms")
            .create();

    System.out.println(verification.getStatus());

  }

}
