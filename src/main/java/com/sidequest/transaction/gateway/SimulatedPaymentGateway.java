package com.sidequest.transaction.gateway;

import com.sidequest.transaction.entity.PaymentMethod;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

// Fake gateway for development and demos. No real money moves.
// Used when sidequest.payments.gateway=simulated (the default).
@Component
@ConditionalOnProperty(name = "sidequest.payments.gateway", havingValue = "simulated", matchIfMissing = true)
public class SimulatedPaymentGateway implements PaymentGateway {

	@Override
	public GatewayResult charge(BigDecimal amount, PaymentMethod method) {
		if (amount == null || amount.signum() <= 0) {
			return GatewayResult.failed("Amount must be more than zero");
		}
		return GatewayResult.ok("SIM-" + method + "-" + UUID.randomUUID());
	}

	@Override
	public GatewayResult refund(String reference) {
		if (reference == null || !reference.startsWith("SIM-")) {
			return GatewayResult.failed("Unknown payment reference");
		}
		return GatewayResult.ok(reference + "-REFUND");
	}
}
