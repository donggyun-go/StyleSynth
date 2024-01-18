package FinalPrjTest.FinalPrj;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

		// 염색 참고 이미지 폴더
		registry.addResourceHandler("/ref/**")
				.addResourceLocations("file:////home/ubuntu/djangoSource/img_test/data/dyeing/")
				// 접근 파일 캐싱 시간
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
		
		// 원본 이미지 폴더
		registry.addResourceHandler("/original/**")
				.addResourceLocations("file:////home/ubuntu/djangoSource/img_test/data/src/src/")
				// 접근 파일 캐싱 시간
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
		
		// 결과물 폴더
		registry.addResourceHandler("/result/**")
				.addResourceLocations("file:////home/ubuntu/djangoSource/img_test/results/results/synthesized_image/")
				// 접근 파일 캐싱 시간
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
	}
}
