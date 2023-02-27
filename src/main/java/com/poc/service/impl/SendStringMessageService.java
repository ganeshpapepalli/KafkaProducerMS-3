package com.poc.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.poc.service.SendMessageToKafka;

@Service
public class SendStringMessageService implements SendMessageToKafka<String>{
	public KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.stringtopicName}")
	public String stringTopic;

	public SendStringMessageService(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String sendMessageToKafka(String message) {
		kafkaTemplate.send(stringTopic, message.toString());
		return  "String message Sent To kafka";
	}
}
