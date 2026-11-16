package com.sidequest.transaction.gateway;

// What the payment provider answered. reference is the provider's id for the payment.
public record GatewayResult(boolean success, String reference, String message) {

	public static GatewayResult ok(String reference) {
		return new GatewayResult(true, reference, "OK");
	}

	public static GatewayResult failed(String message) {
		return new GatewayResult(false, null, message);
	}
}
