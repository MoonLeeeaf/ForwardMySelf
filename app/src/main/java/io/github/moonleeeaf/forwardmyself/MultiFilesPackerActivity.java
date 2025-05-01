package io.github.moonleeeaf.forwardmyself;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 多文件分享没有什么软件支持的
// 有也是 Telegram 什么的
// 甚至质感文件也不支持

public class MultiFilesPackerActivity extends Activity {
    private int total = 0;
    private int processed_total = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int flags = getIntent().getFlags();
        boolean hasReadPermission = (flags & Intent.FLAG_GRANT_READ_URI_PERMISSION) == 1;
        boolean hasWritePermission = (flags & Intent.FLAG_GRANT_WRITE_URI_PERMISSION) == 1;
        
        // 这个永远不会 = null
        List<Uri> uris = getIntent().getExtras().getParcelableArrayList(Intent.EXTRA_STREAM);
        String text = getIntent().getExtras().getString(Intent.EXTRA_TEXT);
    
        if (uris.size() == 0) {
            finishAndRemoveTask();
            startActivity(
                new Intent(this, ShareToOpenWithActivity.class)
                .putExtra(Intent.EXTRA_TEXT, text)
            );
            return;
        }
        
        if (!(hasReadPermission || hasWritePermission)) {
            Toast.makeText(this, "无法访问文件的 Content URI!", Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
            return;
        }
        
        String filePath = getExternalCacheDir() + "/tmp_packed.zip";
        
        /*
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("等待文件打包中...");
        pd.show();
        */
        
        try {
            ArrayList<InputStream> streams = new ArrayList<>();
            ArrayList<String> fileNames = new ArrayList<>();
            for (Uri uri : uris) {
                String _uri = (uri + "");
                streams.add(getContentResolver().openInputStream(uri));
                fileNames.add("分享的附件/" + URLDecoder.decode(_uri.substring(_uri.lastIndexOf("/") + 1), "utf-8"));//i +" + "." + FileTypeUtils.getExtensionFromMimeType(getContentResolver().getType(uri)));
            }
            
            if (text != null) {
                streams.add(new ByteArrayInputStream(text.getBytes()));
                fileNames.add("分享的文本.txt");
            }
            
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(
                ZipUtils.from(
                    streams,
                    fileNames
                )
            );
            fos.flush();
            fos.close();
            
            finishAndRemoveTask();
            startActivityForResult(
                ForwardIntents.createShare(
                    FileProvider.getUriForFile(
                        this,
                        "io.github.moonleeeaf.forwardmyself",
                        new File(filePath)
                    ),
                    "application/zip",
                    true,
                    false
                ),
                0
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        
        if (requestCode != 114514) return;
        
        processed_total ++;
        if (processed_total == total) {
            setResult(0, new Intent());
            finish();
        } else {
            
        }
    }
    */
    
}
