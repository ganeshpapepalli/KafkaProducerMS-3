package com.poc.kafka.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.poc.kafka.exceptions.CustomException;
import com.poc.kafka.model.User;
@Service
public class SendMessageServiceImpl{
	
	@Qualifier(value = "string_template")
	public KafkaTemplate<String, String> kafkaTemplate;
	@Qualifier(value = "user_template")
	public KafkaTemplate<String,User> userKafkaTemplate;

	@Value("${spring.kafka.usertopicName}")
	public String userTopic;
	
	@Value("${spring.kafka.stringtopicName}")
	public String stringTopic;
	
	@Value("${spring.kafka.errortopicName}")
	public String errorTopic;
	
	public SendMessageServiceImpl(KafkaTemplate<String, String> kafkaTemplate,KafkaTemplate<String, User> userKafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.userKafkaTemplate=userKafkaTemplate;
	}
	
	public void sendStringMessageToKafka(String message) {
		
		kafkaTemplate.send(stringTopic, message);
		
	}
	
	
	public void sendUserDetailsToKafka(User data) throws CustomException {
		Message<User> message=null;
		if(data.equals(null)) {
			char firstchar = data.getName().charAt(0);
			if(firstchar=='a' ||firstchar=='e' ||firstchar=='i' ||firstchar=='o' ||firstchar=='u'||
					firstchar=='A' ||firstchar=='E' ||firstchar=='I' ||firstchar=='O' ||firstchar=='U'){
				
				 message = MessageBuilder
		                .withPayload(data)
		                .setHeader(KafkaHeaders.TOPIC, userTopic)
		                .build();
			}else {
				 message = MessageBuilder
		                .withPayload(data)
		                .setHeader(KafkaHeaders.TOPIC, errorTopic)
		                .build();
			}
			
		}else {
			throw new CustomException("User details must not be null");
		}
		

        kafkaTemplate.send(message);
	}
	
}
