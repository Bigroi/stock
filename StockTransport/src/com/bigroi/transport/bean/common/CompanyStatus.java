package com.bigroi.transport.bean.common;

import com.google.gson.annotations.SerializedName;

public enum CompanyStatus {
	
	@SerializedName("INACTIVE")
	NOT_VERIFIED,
	
	@SerializedName("ACTIVE")
	VERIFIED,
	
	@SerializedName("INACTIVE") 
	REVOKED

}
