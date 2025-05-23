package net.zhuoweizhang.mcpelauncher.api;

import java.util.*;

import net.zhuoweizhang.mcpelauncher.pro.R;

import net.zhuoweizhang.mcpelauncher.Utils;
import net.zhuoweizhang.mcpelauncher.texture.*;

import android.os.*;
import android.widget.*;

public class ImportTexturepackActivity extends ImportActivity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        installConfirmText.setText(R.string.texturepack_import_confirm);
    }

    @Override
    protected void startImport() {
        try {
            List<TexturePackDescription> list = TexturePackLoader.loadDescriptionsWithIcons(this);
            TexturePackDescription desc = new TexturePackDescription(TexturePackLoader.TYPE_ZIP, mFile.getAbsolutePath());
            boolean already = false;
            for (TexturePackDescription d : list) {
                if (d.path.equals(desc.path)) {
                    already = true;
                    break;
                }
            }
            if (!already) list.add(0, desc);
            TexturePackLoader.saveDescriptions(this, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.Default().edit().putBoolean("zz_texture_pack_enable", true).apply();
        Toast.makeText(this, R.string.texturepack_imported, Toast.LENGTH_LONG).show();
        finish();
    }
}
