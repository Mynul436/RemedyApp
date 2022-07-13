package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUpload {
    public String fileUpload(MultipartFile file)
    {
        System.out.println("Hello");
        String fileName=(int)new Date().getTime()+file.getOriginalFilename();
        File myFile = new File("D:\\storage\\"+fileName);
		try {
            myFile.createNewFile();
       
		FileOutputStream fos =new FileOutputStream(myFile);
		
            fos.write(file.getBytes());
            fos.close();
     } catch (IOException e) {
            // TODO Auto-generated catch block
         return "Error";
        }
		
        return fileName;
    }
}
