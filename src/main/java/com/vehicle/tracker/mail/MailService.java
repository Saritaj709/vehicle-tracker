package com.vehicle.tracker.mail;

import com.vehicle.tracker.model.MailDTO;

public interface MailService {
	public boolean sendMail(MailDTO mail);	
}
