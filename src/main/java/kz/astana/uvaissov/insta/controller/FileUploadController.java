package kz.astana.uvaissov.insta.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kz.astana.uvaissov.insta.cabinet.model.ActiveSession;
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
    
	@PostMapping("/upload/{fileType}")
	@Transactional
	public ResponseEntity<String> handleFileUpload(@PathVariable String fileType,@ModelAttribute("userSession") ActiveSession session,
			@RequestBody String data, RedirectAttributes redirectAttributes) {
		ByteArrayResource resource = new ByteArrayResource(DatatypeConverter.parseBase64Binary(data.split(",")[1]));
		String bodyName = session.profileId + "_" + (System.nanoTime());
		String fileName = bodyName +".png";
		storageService.write(resource, fileName);//запишем новый файл
		
		if("logo".equals(fileType)) {		
			infoService.setLogo(session.profileId, fileName); //запишем в БД информацию
			if(session.logoUrl!=null) {
				storageService.delete(session.logoUrl);//Удалим старый файл
			}
			session.logoUrl = fileName;//сохраним в сессию информацию о файле
		} else if("background".equalsIgnoreCase(fileType)) {
			infoService.setBackGround(session.profileId, fileName);
			if(session.backgroundUrl!=null) {
				storageService.delete(session.backgroundUrl);//Удалим старый файл
				storageService.delete(session.backgroundUrl.split("\\.")[0]+"_thumb."+session.backgroundUrl.split("\\.")[1]);
			}
			session.backgroundUrl= fileName;
			
			//create thumbnail
			BufferedImage img = new BufferedImage(115, 140, BufferedImage.TYPE_INT_RGB);
			try {
				img.createGraphics().drawImage(ImageIO.read(resource.getInputStream()).getScaledInstance(115, 140, Image.SCALE_SMOOTH),0,0,null);
				storageService.write(new ByteArrayResource(toByteArrayAutoClosable(img,"png")), bodyName+"_thumb.png");
				fileName =bodyName+"_thumb.png";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);//отправляем как HTMl тэг
		
		
		return new ResponseEntity<String>("<img src='http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/content/file/" + fileName + "'>", headers,
				HttpStatus.OK);
	}
	private static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(image, type, out);
            return out.toByteArray();
        }
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
