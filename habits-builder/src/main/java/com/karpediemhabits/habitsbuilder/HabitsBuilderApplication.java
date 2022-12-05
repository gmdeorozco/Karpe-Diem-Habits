package com.karpediemhabits.habitsbuilder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.karpediemhabits.habitsbuilder.entities.Challenge;
import com.karpediemhabits.habitsbuilder.entities.Execution;


@SpringBootApplication
public class HabitsBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitsBuilderApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			ResponseEntity< Execution[] > response = restTemplate.getForEntity(
					"https://8081-gmdeorozco-karpediemhab-t9prwr6p6kx.ws-us77.gitpod.io/execution/bydate/2022-12-02"
								, Execution[].class);

			Execution[] executions = response.getBody();
			List<Execution> listExecutions = List.of( executions );

			for( Execution ex : listExecutions){
				
				if( ex.getChallengeId() != null ){
					Challenge challenge = restTemplate.getForObject(
						"https://8080-gmdeorozco-karpediemhab-t9prwr6p6kx.ws-us77.gitpod.io/challenge/getbyid/"+ex.getChallengeId()
									, Challenge.class);
									ex.setChallenge(challenge);
									challenge.getExecutions().add(ex);
				}
				
				System.out.println( ex.toString() );
			}

		};
	}

}
