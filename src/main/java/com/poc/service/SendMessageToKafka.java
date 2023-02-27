package com.poc.service;

public interface SendMessageToKafka<T> {
	public String sendMessageToKafka(T message);
}
