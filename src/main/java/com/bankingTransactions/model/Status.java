package com.bankingTransactions.model;

public class Status {
	
	private String reference;
	private String channel;
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@Override
	public String toString() {
		return "Status [reference=" + reference + ", channel=" + channel + "]";
	}
	
	

}
