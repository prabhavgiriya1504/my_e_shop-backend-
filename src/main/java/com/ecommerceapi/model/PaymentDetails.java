package com.ecommerceapi.model;

import lombok.Data;

@Data
public class PaymentDetails {

	private String paymentMethod;
	private String status;
	private String paymentId;
	private String razorpayPaymentLinkId;
	private String razorpayPaymentLinkReferenceId ;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentId;
	
	public PaymentDetails(String paymentMethod, String status, String paymentId, String razorpayPaymentLinkId,
			String razorpayPaymentLinkReferenceId, String razorpayPaymentLinkStatus, String razorpayPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.paymentId = paymentId;
		this.razorpayPaymentLinkId = razorpayPaymentLinkId;
		this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
		this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public PaymentDetails() {
		super();
	}
	
	
}
