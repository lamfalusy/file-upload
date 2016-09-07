package hu.lamsoft.file.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class FileUploadController {

	  @Value("${upload_location}")
	  private String uploadLocation;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String createBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		final String foldername = request.getParameter("foldername");
	    final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		
		OutputStream out = null;
	    InputStream filecontent = null;

		try {
	        if(!(new File(uploadLocation + File.separator + foldername)).exists()) {
	        	FileUtils.forceMkdir(new File(uploadLocation + File.separator + foldername));
	        }
	        
	        System.out.println(uploadLocation + File.separator + foldername + File.separator
	                + fileName);
	        
	        out = new FileOutputStream(new File(uploadLocation + File.separator + foldername + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	    } catch (FileNotFoundException fne) {
	    	fne.printStackTrace();
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
		
		return "fileUploadForm";
	}

	private String getFileName(final Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String bookForm(Model model) {
		
		return "fileUploadForm";
	}
	
}
