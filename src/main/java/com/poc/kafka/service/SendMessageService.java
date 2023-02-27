package com.poc.kafka.service;

public interface SendMessageService<T> {
	
	public  String sendMessageToKafka(T message);

}
