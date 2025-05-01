package io.github.moonleeeaf.forwardmyself;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class ShareToOpenWithActivity extends Activity {
    
    private void start(Uri uri, String fileType, boolean hasReadPermission, boolean hasWritePermission) {
        finishAndRemoveTask();
        startActivityForResult(
            ForwardIntents.createOpenWith(
                uri,
                fileType,
                hasReadPermission,
                hasWritePermission
            ),
            0
        );
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String shareText = getIntent().getExtras().getString(Intent.EXTRA_TEXT);
        
        int flags = getIntent().getFlags();
        boolean _hasReadPermission = (flags & Intent.FLAG_GRANT_READ_URI_PERMISSION) == 1 || shareText != null;
        boolean hasWritePermission = (flags & Intent.FLAG_GRANT_WRITE_URI_PERMISSION) == 1;
        
        Uri _uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
        
        if (shareText != null) {
            String path = getExternalCacheDir() + "/tmp.txt";
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(shareText.getBytes());
                _uri = FileProvider.getUriForFile(this, "io.github.moonleeeaf.forwardmyself", new File(path));
                _hasReadPermission = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        if (!(_hasReadPermission || hasWritePermission)) {
            Toast.makeText(this, "无法访问该 Content URI!", Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
            return;
        }
        
        Uri uri = _uri;
        boolean hasReadPermission = _hasReadPermission;
        
        String fileType = getContentResolver().getType(uri);
        
        if (fileType == null) {
            new AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage("文件类型未知, 需要手动指定一个类型来打开文件嘛?")
            .setPositiveButton("好的呢", (which, dialog) -> {
                
            })
            .setNegativeButton("直接打开", (which, dialog) -> {
                start(uri, fileType, hasReadPermission, hasWritePermission);
            })
            .setNeutralButton("取消操作", (which, dialog) -> {
                finishAndRemoveTask();
            })
            .show();
        } else
            start(uri, fileType, hasReadPermission, hasWritePermission);
    }
    
    /*
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        setResult(0, arg2);
        finish();
    }
    */
    
}
