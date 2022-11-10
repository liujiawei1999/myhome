package com.jia.home.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class UploadFileUtils {
    public RespBean getWebPath(MultipartFile file, String path, Boolean addFolder, Boolean keepName) {
        if (file==null){
            return RespBean.error("数据为空！");
        }
        String filename= null;
        if (keepName == null){
            keepName = true;
        }
        if (addFolder == null){
            addFolder = false;
        }
//        for (MultipartFile file : files) {
            filename = "E:\\pdfToWord";
            if (addFolder) {
                filename = filename + "\\" + UUIDUtils.uuid();
            }
            File savePath = new File(filename);
            if (!savePath.exists()) {
                boolean mkdirs = savePath.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            if (!keepName) {
                assert originalFilename != null;
                String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
                originalFilename = UUIDUtils.uuid() + suffixName;
            }
            filename = filename + "\\" + originalFilename;
            try {
                file.transferTo(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
                return RespBean.error("上传失败！");
            }
//        }
        return RespBean.ok(filename);
    }
    public static void  downLoadFile(Map<String, Object> parameter, HttpServletResponse response) throws FileNotFoundException {
        String path = (String) parameter.get("path");
        try {
            File file = new File(path);
            System.out.println(path);
            String fileName = file.getName();
            System.out.println(fileName);
            /**
             * 中文乱码解决
             */
            fileName = URLEncoder.encode(fileName,"utf-8");
            // 设置响应的头部信息
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            System.out.println(1);
            // 设置响应内容的类型
            response.setContentType(getFileContentType(fileName)+"; charset=" + "utf-8");
            // 设置响应内容的长度
            response.setContentLength((int) file.length());
            System.out.println(2);
            // 输出
            outStream(new FileInputStream(file), response.getOutputStream());
            System.out.println(3);
        } catch (Exception e) {
            System.out.println("执行downloadFile发生了异常：" + e.getMessage());
        }
    }

    private static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            System.out.println("执行 outStream 发生了异常：" + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
    private static String getFileContentType(String name){
        String result = "";
        String fileType = name.toLowerCase();
        if (fileType.endsWith(".png")) {
            result = "image/png";
        } else if (fileType.endsWith(".gif")) {
            result = "image/gif";
        } else if (fileType.endsWith(".jpg") || fileType.endsWith(".jpeg")) {
            result = "image/jpeg";
        } else if(fileType.endsWith(".svg")){
            result = "image/svg+xml";
        }else if (fileType.endsWith(".doc")) {
            result = "application/msword";
        } else if (fileType.endsWith(".xls")) {
            result = "application/x-excel";
        } else if (fileType.endsWith(".zip")) {
            result = "application/zip";
        } else if (fileType.endsWith(".pdf")) {
            result = "application/pdf";
        } else {
            result = "application/octet-stream";
        }
        return result;
    }
}
