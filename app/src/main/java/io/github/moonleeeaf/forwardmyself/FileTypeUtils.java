package io.github.moonleeeaf.forwardmyself;

import java.util.HashMap;
import java.util.Map;

// 来自 DeepSeek

public class FileTypeUtils {
    
    private static final Map<String, String> MIME_TO_EXTENSION = new HashMap<String, String>() {{
        // 常见图片类型
        put("image/jpeg", "jpg");
        put("image/png", "png");
        put("image/gif", "gif");
        put("image/bmp", "bmp");
        put("image/webp", "webp");
        
        // 常见文档类型
        put("application/pdf", "pdf");
        put("application/msword", "doc");
        put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        put("application/vnd.ms-excel", "xls");
        put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        put("application/vnd.ms-powerpoint", "ppt");
        put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        put("text/plain", "txt");
        put("text/html", "html");
        put("text/css", "css");
        put("text/javascript", "js");
        put("application/json", "json");
        put("application/xml", "xml");
        
        // 压缩文件
        put("application/zip", "zip");
        put("application/x-rar-compressed", "rar");
        put("application/x-7z-compressed", "7z");
        put("application/x-tar", "tar");
        put("application/gzip", "gz");
        
        // 音视频
        put("audio/mpeg", "mp3");
        put("audio/wav", "wav");
        put("audio/ogg", "ogg");
        put("video/mp4", "mp4");
        put("video/quicktime", "mov");
        put("video/x-msvideo", "avi");
        put("video/x-matroska", "mkv");
        put("video/webm", "webm");
    }};

    public static String getExtensionFromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return null;
        }
        
        // 处理包含分号的情况，如 "image/jpeg;charset=UTF-8"
        String cleanMimeType = mimeType.split(";")[0].trim().toLowerCase();
        
        return MIME_TO_EXTENSION.get(cleanMimeType);
    }
}