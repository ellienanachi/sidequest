package com.sidequest.transaction.gateway;

import com.sidequest.transaction.entity.PaymentMethod;

import java.math.BigDecimal;

// Interface to the external payment provider for GCash, Maya and QR Ph (Class Diagram).
// The app only talks to this interface, so the simulated gateway can be swapped
// for a PayMongo (test mode) one without touching the rest of the code.
public interface PaymentGateway {

	GatewayResult charge(BigDecimal amount, PaymentMethod method);

	GatewayResult refund(String reference);

	// TODO: payout to the adventurer when a reward is released (not in the class diagram yet).
}
