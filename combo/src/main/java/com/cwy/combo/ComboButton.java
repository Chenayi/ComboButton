package com.cwy.combo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Chenwy on 2018/1/12 14:34
 */

public class ComboButton extends View {
    private final int DEFAULT_SIZE = 100;
    private Paint paint;

    public ComboButton(Context context) {
        this(context, null);
    }

    public ComboButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComboButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getSize(widthMeasureSpec);
        int height = getSize(heightMeasureSpec);

        if (width < height) {
            height = width;
        } else if (width > height) {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //从下往上数
        //第一个圆的半径
        int firstR = getMeasuredWidth() / 2;

        //共同圆心x，y坐标
        int centerX = firstR;
        int centerY = firstR;

        //绘制第一个圆
        paint.setColor(Color.parseColor("#E291C5"));
        canvas.drawCircle(firstR, centerX, centerY, paint);

        //中间圆半径
        int centerR = firstR - dp2px(8f);
        //绘制中间圆
        paint.setColor(Color.parseColor("#E863CF"));
        canvas.drawCircle(centerX, centerY, centerR, paint);

        //最外层圆
        int outR = centerR - dp2px(8f);
        //绘制最外层圆
        paint.setColor(Color.parseColor("#EC48DA"));
        canvas.drawCircle(centerX, centerY, outR, paint);

        String text = "连击";
        paint.setColor(Color.WHITE);
        paint.setTextSize(dp2px(14));

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        canvas.drawText(text, centerX - textWidth / 2, centerY + textHeight / 2, paint);
    }

    private int getSize(int measureSpec) {
        int sizeResult = DEFAULT_SIZE;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            //没有指定大小，此情况不多
            case MeasureSpec.UNSPECIFIED:
                sizeResult = DEFAULT_SIZE;
                break;
            //最大取值
            case MeasureSpec.AT_MOST:
                sizeResult = size;
                break;
            //固定大小
            case MeasureSpec.EXACTLY:
                sizeResult = size;
                break;
        }

        return sizeResult;
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
