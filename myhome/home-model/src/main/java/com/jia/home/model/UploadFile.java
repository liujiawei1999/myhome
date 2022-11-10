package com.jia.home.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {
    private String path;
    private Boolean keepName;
    private Boolean addFolder;
    private MultipartFile files;
}
