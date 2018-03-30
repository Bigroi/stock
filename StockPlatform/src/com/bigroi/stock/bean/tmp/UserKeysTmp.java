package com.bigroi.stock.bean.tmp;

public class UserKeysTmp {

	private String username;
	private String generated_key;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGenerated_key() {
		return generated_key;
	}

	public void setGenerated_key(String generated_key) {
		this.generated_key = generated_key;
	}

	@Override
	public String toString() {
		return "UserKeysTmp [username=" + username + ", generated_key=" + generated_key + "]";
	}
}
