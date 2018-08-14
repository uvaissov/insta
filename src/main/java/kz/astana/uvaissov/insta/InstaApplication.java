package kz.astana.uvaissov.insta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import kz.astana.uvaissov.insta.util.StorageProperties;
import kz.astana.uvaissov.insta.util.StorageService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class InstaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
	        return (args) -> {
	            //storageService.deleteAll();
	            storageService.init();
	        };
	}
	
//	@Bean public CommonsMultipartResolver multipartResolver() { 
//		CommonsMultipartResolver multipart = new CommonsMultipartResolver(); 
//		multipart.setMaxUploadSize(3 * 1024 * 1024); 
//		return multipart;
//	}
//	
//	@Bean @Order(0) 
//	public MultipartFilter multipartFilter() { 
//		MultipartFilter multipartFilter = new MultipartFilter(); 
//		multipartFilter.setMultipartResolverBeanName("multipartReso‌​lver"); 
//		return multipartFilter; 
//	}
}
