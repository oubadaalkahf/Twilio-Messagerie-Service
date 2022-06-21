package sms.twilio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sms.twilio.Request.SmsRequest;
import sms.twilio.service.SmsSender;
import sms.twilio.service.impl.SmsSenderImpl;
import sms.twilio.service.verification.PhoneverificationService;
import sms.twilio.service.verification.VerificationResult;

@RestController
@RequestMapping("/")
public class SmsController {
	@Autowired
	private SmsSenderImpl smsSenderImpl ;
	@Autowired
	private PhoneverificationService phonesmsservice;
	
	
	@PostMapping("/sendSms")
	public ResponseEntity<Object> sendSms(@RequestBody SmsRequest request ) {
	return smsSenderImpl.sendSms(request);
	}
	
	
	
	
	
	
	@PostMapping("/sendotp")
	public ResponseEntity<String> sendotp(@RequestBody SmsRequest phone)
	{
	    System.out.println(phone.getPhoneNumber());
	    VerificationResult result=phonesmsservice.startVerification(phone.getPhoneNumber());
	    System.out.println(phonesmsservice.startVerification(phone.getPhoneNumber()).toString());
	    if(result.isValid())
	    {
	    	return new ResponseEntity<>("Otp Sent..",HttpStatus.OK);
	    }
	    else 
		return new ResponseEntity<>("Otp failed to sent..",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<String> sendotp(@RequestBody SmsRequest phone, @RequestParam("otp") String otp)
	{
	    VerificationResult result=phonesmsservice.checkverification(phone.getPhoneNumber(),otp);
	    if(result.isValid())
	    {
	    	return new ResponseEntity<>("Your number is Verified",HttpStatus.OK);
	    }
		return new ResponseEntity<>("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST);
	}
	
	
		
}
