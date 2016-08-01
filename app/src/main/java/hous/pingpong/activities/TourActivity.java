package hous.pingpong.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import hous.pingpong.R;
import hous.pingpong.adapter.SamplePagerAdapter;
import jp.wasabeef.blurry.Blurry;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by E.Kayali on 7/31/2016.
 */
public class TourActivity extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        img = (ImageView) findViewById(R.id.background_img);
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap blurred = blurRenderScript(bitmap, 20);//second parametre is radius
        img.setImageBitmap(blurred);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager_default);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        viewpager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        indicator.setViewPager(viewpager);
    }

    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(TourActivity.this);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

}
