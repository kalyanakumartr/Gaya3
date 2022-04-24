package org.hbs.gaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GayathriConstructionRentalApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplicationBuilder(GayathriConstructionRentalApplication.class).sources(GayathriConstructionRentalApplication.class).build();
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(GayathriConstructionRentalApplication.class);
	}

}
