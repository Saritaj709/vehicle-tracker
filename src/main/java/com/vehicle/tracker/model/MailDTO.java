package com.vehicle.tracker.model;

import lombok.Data;

@Data
public class MailDTO {
	private String to;
	private String subject;
	private String text;
}
