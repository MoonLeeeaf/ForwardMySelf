package io.github.moonleeeaf.forwardmyself;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/*
 * Author - 月有阴晴圆缺
 * 
 * 备注: 本类仅供本软件内部使用, 但如果需要也可以引用到你的项目中, 请记得保留著作信息
 */

public class ForwardIntents {
    
    public static Intent createOpenWith(Uri fileUri, String mimeType, boolean hasReadPermission, boolean hasWritePermission) {
        Uri uri = fileUri; // intentWithParcelable.getExtras().getParcelable(Intent.EXTRA_STREAM);
        
        Intent intent = new Intent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setAction(Intent.ACTION_VIEW)
        .setDataAndType(uri, mimeType);//context.getContentResolver().getType(uri));
        
        if (hasReadPermission) intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (hasWritePermission) intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        
        return Intent.createChooser(intent, "打开...");
    }
    
    public static Intent createShare(Uri fileUri, String mimeType, boolean hasReadPermission, boolean hasWritePermission) {
        Uri uri = fileUri;// intentWithParcelable.getExtras().getParcelable(Intent.EXTRA_STREAM);
        
        Intent intent = new Intent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setAction(Intent.ACTION_SEND)
        .setDataAndType(uri, mimeType); //context.getContentResolver().getType(uri));
        
        if (hasReadPermission) intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (hasWritePermission) intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        
        return Intent.createChooser(intent, "分享到");
    }
    
}
