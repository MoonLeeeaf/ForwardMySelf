package io.github.moonleeeaf.share2openwith;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class OpenWithActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int flags = getIntent().getFlags();
        boolean hasReadPermission = (flags & Intent.FLAG_GRANT_READ_URI_PERMISSION) == 1;
        boolean hasWritePermission = (flags & Intent.FLAG_GRANT_WRITE_URI_PERMISSION) == 1;
        
        if (!(hasReadPermission || hasWritePermission)) {
            Toast.makeText(this, "无法访问该 Content URI!", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        Uri uri = getIntent().getExtras().getParcelable(Intent.EXTRA_STREAM);
        
        Intent intent = new Intent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setAction(Intent.ACTION_VIEW)
        .setDataAndType(uri, getContentResolver().getType(uri));
        
        if (hasReadPermission) intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (hasWritePermission) intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        
        startActivityForResult(
            intent,
            0
        );
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        setResult(0, arg2);
        finish();
    }
    
}
