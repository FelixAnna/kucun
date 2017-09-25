package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.FileBizService;
import com.xiangyong.manager.core.util.WsConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileBizServiceImp implements FileBizService {
    @Override
    public String Save(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = file.getOriginalFilename();//获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件的后缀名
        String newFileName = String.format("/avatar/%s%s", UUID.randomUUID().toString().replace("-",""), suffixName).toLowerCase();
        File dest = new File(WsConstants.TEMP_DIR + newFileName);
        if (!dest.getParentFile().exists()) {//检测是否存在目录
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest);
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean Remove(String fileName) {
        File dest = new File(WsConstants.TEMP_DIR + fileName);
        if (!dest.exists()) {//检测是否存在目录
            return dest.delete();
        }

        return  false;
    }
}
