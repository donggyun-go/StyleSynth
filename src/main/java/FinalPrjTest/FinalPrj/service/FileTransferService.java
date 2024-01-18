package FinalPrjTest.FinalPrj.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileTransferService {
	public String webToLocal(MultipartFile uploadfile) throws IOException {
		String check;
		String baseUrl = "/home/ubuntu/djangoSource/img_test/data/dyeing/";
		if (!uploadfile.isEmpty()) {
			File newFileName = new File(baseUrl + uploadfile.getOriginalFilename());

			// 전달된 내용을 실제 물리적인 파일로 저장
			uploadfile.transferTo(newFileName);

			// 장고로 저장된 그림의 path 보내주고 처리된 이미지 파일 명을 check에 받음
			check = RequestUtil.restRequest("http://localhost:8000/testImg/dyeing/",
					uploadfile.getOriginalFilename());
			System.out.println("-------------------result from Django:" + check);
			System.out.println("-------------------uploadFile name: " + uploadfile.getOriginalFilename());
			return check;
		}
		return "error";
	}
	
	public String saveOriginal(MultipartFile uploadfile) throws IOException {
		String baseUrl = "/home/ubuntu/djangoSource/img_test/data/src/src/";
		String check;
		if (!uploadfile.isEmpty()) {
			File newFileName = new File(baseUrl + uploadfile.getOriginalFilename());

			// 전달된 내용을 실제 물리적인 파일로 저장
			uploadfile.transferTo(newFileName);
			check = baseUrl + uploadfile.getOriginalFilename();
			System.out.println("~~~~~~~~~~~~~~~~~in SAVEORIGINAL CHECK: " + check);
			return check;
			
		}
		return "error";
	}
}
