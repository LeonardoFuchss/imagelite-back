package com.leonardofuchs.imageliteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class ImageliteapiApplication {
//	@Bean
//	public ommandLineRunner commandLineRunner(@Autowired ImageRepository imageRepository){  // realizando teste INSERT no banco de dados
    //	return args -> {
    //	Image image = Image
    //	.builder()
    //	.extension(ImageExtension.PNG)
    //	.name("imageTest")
    //	.tags("teste")
    //	.size(1000L)
    //	.build();
    // imageRepository.save(image);
    //	};
    public static void main(String[] args) {
        SpringApplication.run(ImageliteapiApplication.class, args);
    }
}
