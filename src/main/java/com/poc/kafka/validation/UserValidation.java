package com.poc.kafka.validation;

import org.springframework.stereotype.Component;

@Component
public class UserValidation {

	public boolean validateVowelUserName(String userName) {
		char firstchar=userName.charAt(0);
		return (firstchar=='a' ||firstchar=='e' ||firstchar=='i' ||firstchar=='o' ||firstchar=='u'||
				firstchar=='A' ||firstchar=='E' ||firstchar=='I' ||firstchar=='O' ||firstchar=='U');
	}
}
