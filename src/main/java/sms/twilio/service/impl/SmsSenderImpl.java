package sms.twilio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import sms.twilio.Request.SmsRequest;
import sms.twilio.configuration.TwilioConfig;
import sms.twilio.configuration.TwilioInitializer;
import sms.twilio.service.SmsSender;

@Service
public class SmsSenderImpl implements SmsSender {
	private TwilioConfig twilioConfig;
	private final static Logger  LOGGER =  LoggerFactory.getLogger(TwilioInitializer.class);
	@Autowired
	public SmsSenderImpl(TwilioConfig twilioConfig) {
	this.twilioConfig = twilioConfig;
	}

	@Override
	public ResponseEntity<Object> sendSms(SmsRequest smsRequest) {
		if(isPhonenumberValid(smsRequest.getPhoneNumber())) {
			PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
			String message = smsRequest.getMessage();
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
			MessageCreator creator = Message.creator(to, from, message);
			creator.create();
			LOGGER.info("SMS -------------: {}" +smsRequest);
			return new ResponseEntity<Object>("OTP Sent, Please Check your Messages",HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("OTP Not Send, please enter a different phone Number ",HttpStatus.BAD_REQUEST);
		}
	
		

	}

	private boolean isPhonenumberValid(String phoneNumber) {
		
		return true;
	}

}
