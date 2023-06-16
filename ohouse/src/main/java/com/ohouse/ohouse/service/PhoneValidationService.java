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

  public PhoneValidationService(@Value("${twilio.account_sid}") String account_sid,
                                @Value("${twilio.auth_token}") String auth_token) {
    this.ACCOUNT_SID = account_sid;
    this.AUTH_TOKEN = auth_token;

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  public void sendVerificationCode(String phoneNumber) {
    Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber("+"),
                    "Where's Wallace?")
            .create();
    System.out.println(message.getSid());
  }

  public void verifyPhoneNumber(String phoneNumber, String code) {
  }
}
