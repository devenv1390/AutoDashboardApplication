package com.qky.autodashboardapplication.ui;

/**
 * Demo class
 *
 * @author kaiyuan.qin
 * @date 2024/3/12
 */

import static android.graphics.Bitmap.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.qky.autodashboardapplication.R;

@SuppressLint("AppCompatCustomView")
public class ReflectTextView extends TextView {
    private Matrix mMatrix;
    private Paint mPaint;

    private static int REFLECT_ALPHA;
    // 倒影透明度
    private static float REFLECT_HEIGHT_MULTIPLE;
    // 倒影的高度倍数
    private static int SPACING_VALUE;
    // 实体文字与倒影之间的空隙
    private static float OFF_Y;
    // Y轴偏移，由于倒影的高度倍数设置小于1时就会出现偏移，显示部分倒影

    public ReflectTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        REFLECT_ALPHA = 100;
        REFLECT_HEIGHT_MULTIPLE = 0.5f;
        SPACING_VALUE = 0;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.reflect, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.reflect_reflectAlpha) {
                // 倒影透明度[1-255]
                REFLECT_ALPHA = a.getInteger(attr, 100);
                // 默认100
                if (REFLECT_ALPHA < 1) {
                    REFLECT_ALPHA = 1;
                } else if (REFLECT_ALPHA > 255) {
                    REFLECT_ALPHA = 255;
                }
            } else if (attr == R.styleable.reflect_reflectHeightMultiple) {
                // 倒影的高度倍数[0-1]
                REFLECT_HEIGHT_MULTIPLE = a.getFloat(attr, 1f);
                // 默认1
                if (REFLECT_HEIGHT_MULTIPLE < 0) {
                    REFLECT_HEIGHT_MULTIPLE = 0;
                } else if (REFLECT_HEIGHT_MULTIPLE > 1) {
                    REFLECT_HEIGHT_MULTIPLE = 1;
                }
            } else if (attr == R.styleable.reflect_spacingValue) {
                // 实体文字与倒影之间的空隙 默认10dp
                SPACING_VALUE = a.getDimensionPixelSize(attr, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                                getResources().getDisplayMetrics()));
            }
        }
        a.recycle();

        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mMatrix.preScale(1, -1);
        // 这句是关闭硬件加速，启用软件加速，如果报相关错误可以尝试注释这句代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int temp = (int) (getMeasuredHeight() - (getLineHeight() - getTextSize()) / 2);
        OFF_Y = temp - temp * REFLECT_HEIGHT_MULTIPLE;
        setMeasuredDimension(getMeasuredWidth(), Math.round(temp * 2 - OFF_Y)
                + SPACING_VALUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        setDrawingCacheEnabled(true);
        Bitmap originalImage = getDrawingCache();
        Bitmap reflectionImage = createBitmap(originalImage, 0, 0,
                Math.min(width, originalImage.getWidth()), height, mMatrix,
                false);
        Paint paint = new Paint();
        paint.setAlpha(REFLECT_ALPHA);
        canvas.drawBitmap(reflectionImage, 0, OFF_Y, paint);
        if (mPaint == null) {
            mPaint = new Paint();
            // 阴影的效果可以自己根据需要设定
            LinearGradient shader = new LinearGradient(0, (height + OFF_Y) / 2,
                    0, height, 0xffffffff, 0x00ffffff, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        canvas.drawRect(0, (height + OFF_Y) / 2, width, height, mPaint);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        buildDrawingCache();
        postInvalidate();
        // 每次更新TextView后遗留上次的残影，所以在这里每次刷新TextView后清楚DrawingCache
        destroyDrawingCache();
    }
}
