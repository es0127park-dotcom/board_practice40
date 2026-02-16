package com.example.board_practice40.sevice;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 파일을 저장하고, 저장된 파일명(UUID)를 반환
     * 파일이 없으면 null 반환
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 1. 파일이 비어 있으면 null 반환
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 2. 원본 파일명에서 확장자 추출
        String originalFileName = file.getOriginalFilename(); // "photo.jpg"
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".")); // ".jpg"

        // 3. UUID로 저장용 파일명 생성 (중복 방지)
        String savedFileName = UUID.randomUUID() + ext; // "a1b2c3d4~~~.jpg"

        // 4. 저장 폴더가 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 5. 파일 저장
        File savedFile = new File(dir, savedFileName);
        file.transferTo(savedFile);

        // 6. 저장된 파일명 반환
        return savedFileName;
    }

    /**
     * 파일의 전체 경로 반환
     */
    public String getFullPath(String fileName) {
        return uploadDir + fileName;
    }
}
