package sms.twilio.service.verification;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;


import sms.twilio.configuration.TwilioConfig;

@Service
public class PhoneverificationService {

//	private final Twilioproperties twilioproperties;
	private final TwilioConfig twilioConfig ;

	@Autowired
	public PhoneverificationService(TwilioConfig twilioConfig) {
		this.twilioConfig=twilioConfig;
	}
	
	
	//method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator(twilioConfig.getServiceId(), phone, "sms").create();
            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
			return new VerificationResult(verification.getSid());
			}
        } catch (ApiException exception) {
            return new VerificationResult(new String[] {exception.getMessage()});
        }
        return null;
    }

    //mehtod to verifiy the otp
    public VerificationResult checkverification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(twilioConfig.getServiceId(), code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
	
}