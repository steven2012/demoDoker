package co.com.getta.platform.infrastructure.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import co.com.getta.platform.crosscutting.util.MessageUtil;

@SpringBootApplication(scanBasePackages = { "co.com.getta.platform.infrastructure", "co.com.getta.platform.modules",
		"co.getta.carvajal.platform.crosscutting" })
public class GetaManagerGovermentListApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetaManagerGovermentListApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames("properties/manager-messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public MessageUtil messagesUtil() {
		return new MessageUtil();
	}
}
