package com.xiangyong.manager.biz.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileBizService {
    String Save(MultipartFile file);

    Boolean Remove(String fileName);
}
