package com.jojo.independentwebsite.utils;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Configuration
public class FileUtils {

    public static String FILE_PATH;

    @Value("${filePath}")
    public void setFilePath(String savePath){
        FILE_PATH = savePath;
    }

    public static String fileUpload(InputStream fileInputStream, FormDataContentDisposition fileMetaData) throws IOException {

        File file = new File(FILE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }

        String fileName = fileMetaData.getFileName();
        String newFilename = null;
        String newPath = null;
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(fileExtName.equalsIgnoreCase("jpg")|| fileExtName.equalsIgnoreCase("pgn")|| fileExtName.equalsIgnoreCase("jpeg")){

            newFilename = UUID.randomUUID().toString() + "." + fileExtName;
            newPath = FILE_PATH + "/" + newFilename;

            int read = 0;
            byte[] bytes = new byte[1024];
            FileOutputStream out = new FileOutputStream(newPath);
            while((read = fileInputStream.read(bytes))!=-1){
                out.write(bytes,0,read);
            }
            out.flush();
            out.close();
        }

        return FILE_PATH + "\\" + newFilename;
    }

}
