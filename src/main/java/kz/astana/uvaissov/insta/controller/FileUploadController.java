package kz.astana.uvaissov.insta.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import groovy.transform.EqualsAndHashCode;
import kz.astana.uvaissov.insta.cabinet.model.ActiveSession;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.util.StorageFileNotFoundException;
import kz.astana.uvaissov.insta.util.StorageService;


@Controller
@SessionAttributes({"userSession"})
@RequestMapping("/content")
public class FileUploadController {

    private final StorageService storageService;
    
    @Autowired
    ServletContext context;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    InfoService infoService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS)).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
	@PostMapping("/upload")
	@Transactional
	public ResponseEntity<String> handleFileUpload(@ModelAttribute("userSession") ActiveSession session,
			@RequestBody String data, RedirectAttributes redirectAttributes) {
		ByteArrayResource resource = new ByteArrayResource(DatatypeConverter.parseBase64Binary(data.split(",")[1]));
		String fileName = session.profileId + "_" + (System.nanoTime()) + ".png";
		storageService.write(resource, fileName);//запишем новый файл
		infoService.setLogo(session.profileId, fileName); //запишем в БД информацию
		if(session.logoUrl!=null) {
			storageService.delete(session.logoUrl);//Удалим старый файл
		}
		session.logoUrl = fileName;//сохраним в сессию информацию о файле
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);//отправляем как HTMl тэг
		
		
		return new ResponseEntity<String>("<img src='http://"+request.getServerName()+":"+request.getServerPort()+"/content/file/" + fileName + "'>", headers,
				HttpStatus.OK);
	}
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
