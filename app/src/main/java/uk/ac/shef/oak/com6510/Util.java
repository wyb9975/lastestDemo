package uk.ac.shef.oak.com6510;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * The Util is a class to decode sampled bitmap from resource bitmap.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class Util {
    /**
     * Decodes sampled bitmap from resource bitmap.
     *
     * @param filepath  the filepath
     * @param reqWidth  the req width
     * @param reqHeight the req height
     * @return the bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(String filepath, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath,options);
    }

    /**
     * Calculates in sample size int.
     *
     * @param options   the options
     * @param reqWidth  the req width
     * @param reqHeight the req height
     * @return the int
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight / 2;
        final int width = options.outWidth / 2;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) >= reqHeight
            && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
