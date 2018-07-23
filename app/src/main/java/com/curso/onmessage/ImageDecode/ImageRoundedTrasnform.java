package com.curso.onmessage.ImageDecode;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;



/**
 * Created by PC-PRAF on 23/10/2016.
 */

public class ImageRoundedTrasnform extends BitmapTransformation {

    private int width;
    private  int height;

    public ImageRoundedTrasnform(Context context , int W , int H) {
        super(context);
        this.width = W;
        this.height = H;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        // If no matching Bitmap is in the pool, get will return null, so we should allocate.
        if (result == null) {
            // Use ARGB_8888 since we're going to add alpha to the image.
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        // Create a Canvas backed by the result Bitmap.
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAlpha(128);
        RectF rectF = new RectF(10 , 10 ,result.getWidth() , result.getHeight());
        // Draw the original Bitmap onto the result Bitmap with a transformation.
        canvas.drawRoundRect(rectF , 10 ,10 , paint);
        // Since we've replaced our original Bitmap, we return our new Bitmap here. Glide will
        // will take care of returning our original Bitmap to the BitmapPool for us.
        return result;
    }

    @Override
    public String getId() {
        return null;
    }
}
