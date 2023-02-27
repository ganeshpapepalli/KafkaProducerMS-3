package com.poc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.poc.common.UserRole;
import com.poc.model.ConvertedUser;
import com.poc.service.SendMessageToKafka;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendUserMessageService implements SendMessageToKafka<ConvertedUser>{

	@Autowired
	public KafkaTemplate<String, ConvertedUser> userKafkaTemplate;
	
	@Value("${spring.kafka.admintopicName}")
	public String adminTopic;

	@Value("${spring.kafka.customertopicName}")
	public String customertopicName;

	

	@Override
	public String sendMessageToKafka(ConvertedUser messagedata) {
		log.info("User messgae recived MS-3  "+messagedata);
		Message<ConvertedUser> message = null;
		String response = null;
		if (!ObjectUtils.isEmpty(messagedata)) {

				if(messagedata.getUserRole().equals(UserRole.ADMIN_USER)) {
					message = MessageBuilder.withPayload(messagedata).setHeader(KafkaHeaders.TOPIC, adminTopic).build();
					response = "ConvertedUser Message sent to Kafka Topic with name:" + adminTopic;
				}else {
					message = MessageBuilder.withPayload(messagedata).setHeader(KafkaHeaders.TOPIC, customertopicName).build();
					response = "ConvertedUser Message sent to Kafka Topic with name:" + customertopicName;
				}
				
		} else {
			log.error("User Deatils are Null Please provide valid Details");
		}
		userKafkaTemplate.send(message);
		return  response;
	}
	
}
