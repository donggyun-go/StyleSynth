package FinalPrjTest.FinalPrj.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import FinalPrjTest.FinalPrj.service.FileTransferService;

@RestController
@RequestMapping("/rest")
public class RestServiceController {
	@Autowired
	FileTransferService fileTransferService;

	Map<String, String> map = new HashMap<>();

	@PostMapping("/uploadOriginal")
	public Map<String, String> uploadOriginal(@RequestPart(value = "uploadOriginal") MultipartFile uploadOriginal)
			throws IllegalStateException, IOException {
		System.out.println("======================start rest/uploadOriginal");
		String imgPath = fileTransferService.saveOriginal(uploadOriginal);
		System.out.println("imgPath: " + imgPath);
		map.put("imgPath", imgPath);
		System.out.println(map);

		return map;
	}
	
	@PostMapping("/uploadRef")
	public Map<String, String> uploadRef(@RequestPart(value = "uploadRef") MultipartFile uploadfile)
			throws IllegalStateException, IOException {
		String imgPath = "/result/" + fileTransferService.webToLocal(uploadfile);
		System.out.println("imgPath: " + imgPath);
		map.put("imgPath", imgPath);
		System.out.println(map);

		return map;
	}
}
