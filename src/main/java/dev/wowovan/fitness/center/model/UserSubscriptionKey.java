package dev.wowovan.fitness.center.model;

import java.io.Serializable;

public class UserSubscriptionKey implements Serializable {
    private static final long serialVersionUID = -5196626637184599382L;

    private String userId;
	private String productId;
	
	public UserSubscriptionKey() {}

	public UserSubscriptionKey(String userId, String productId) {
		this.userId = userId;
		this.productId = productId;
	}
}