package io.github.moonleeeaf.forwardmyself;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    
    /**
     * 将多个输入流写入到 ZIP 文件中
     * 来自 DeepSeek (部分修改)
     *
     * @param inputStreams   输入流列表(自动close)
     * @param fileNames     对应的文件名列表
     * @returns byte[] 数据流
     * @throws IOException 如果发生 I/O 错误
     */
    public static byte[] from(List<InputStream> inputStreams, List<String> fileNames) throws IOException {
        if (inputStreams == null || fileNames == null || inputStreams.size() != fileNames.size()) {
            throw new IllegalArgumentException("输入流列表和文件名列表不能为null且大小必须相同");
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);

            byte[] buffer = new byte[1024];
            int bytesRead;

            for (int i = 0; i < inputStreams.size(); i++) {
                InputStream is = inputStreams.get(i);
                String fileName = fileNames.get(i);

                // 确保文件名不为空
                String entryName = (fileName != null) ? fileName : "file_" + (i + 1);

                // 添加 ZIP 条目
                ZipEntry zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);

                // 写入数据
                while ((bytesRead = is.read(buffer)) != -1) {
                    zos.write(buffer, 0, bytesRead);
                }

                // 关闭当前条目
                zos.closeEntry();
                is.close();
            }
            zos.close();
            
            return bos.toByteArray();
        } catch (IOException e) {
            throw e;
        }
    }
    
}
