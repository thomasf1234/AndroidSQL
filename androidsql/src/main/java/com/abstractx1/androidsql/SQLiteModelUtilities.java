package com.abstractx1.androidsql;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tfisher on 17/01/2017.
 */

public class SQLiteModelUtilities {
    public static String readAssetsFile(AssetManager assetManager, String fileName) {
        String platformIndependentNewLine = String.format("%n");
        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(assetManager.open(fileName)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append(platformIndependentNewLine);
            }
        } catch (IOException e) {
            Log.e("DEBUG", Log.getStackTraceString(e));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("DEBUG", Log.getStackTraceString(e));
                }
            }
        }
        return text.toString();
    }
}
