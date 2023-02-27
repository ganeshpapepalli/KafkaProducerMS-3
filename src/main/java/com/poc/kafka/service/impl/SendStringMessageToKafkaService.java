package com.poc.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.poc.kafka.service.SendMessageService;

import lombok.extern.slf4j.Slf4j;

@Service(value = "string_message_service")
@Slf4j
public class SendStringMessageToKafkaService implements SendMessageService<String> {


	@Autowired
	public KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.stringtopicName}")
	public String stringTopic;

	public SendStringMessageToKafkaService(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String sendMessageToKafka(String message) {
		
		kafkaTemplate.send(stringTopic, message.toString());
		
		
		
		log.info("String message sent to kafka");
		return "String message Sent To kafka";
	}

}
