package com.cwy.combo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Chenwy on 2018/1/12 14:34
 */

public class ComboButton extends View implements View.OnClickListener {
    /**
     * 默认宽高尺寸
     */
    private final int DEFAULT_SIZE = 100;

    /**
     * 默认动画时间
     */
    private final int DEFAULT_ANIM_DURATION = 300;

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 动画时间
     */
    private int animDuration;

    /**
     * 最底下圆的颜色
     */
    private int underCircleColor;

    /**
     * 中间圆的颜色
     */
    private int centerCircleColor;

    /**
     * 最顶部圆的颜色
     */
    private int topCircleColor;

    /**
     * 圆与圆之间的间距
     */
    private float circleSpacing;

    /**
     * 缩放倍数
     */
    private float zoomF;

    public ComboButton(Context context) {
        this(context, null);
    }

    public ComboButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComboButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ComboButton);
        animDuration = t.getInteger(R.styleable.ComboButton_animDuration, DEFAULT_ANIM_DURATION);
        underCircleColor = t.getColor(R.styleable.ComboButton_underCircleColor, Color.parseColor("#E291C5"));
        centerCircleColor = t.getColor(R.styleable.ComboButton_centerCircleColor, Color.parseColor("#E863CF"));
        topCircleColor = t.getColor(R.styleable.ComboButton_topCircleColor, Color.parseColor("#EC48DA"));
        circleSpacing = t.getDimension(R.styleable.ComboButton_circleSpacing, dp2px(8));
        zoomF = t.getFloat(R.styleable.ComboButton_zoomF, 1.5f);
        t.recycle();
        setOnClickListener(this);
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
        paint.setColor(underCircleColor);
        canvas.drawCircle(firstR, centerX, centerY, paint);

        //中间圆半径
        float centerR = firstR - circleSpacing;
        //绘制中间圆
        paint.setColor(centerCircleColor);
        canvas.drawCircle(centerX, centerY, centerR, paint);

        //最外层圆
        float outR = centerR - circleSpacing;
        //绘制最外层圆
        paint.setColor(topCircleColor);
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

    @Override
    public void onClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, zoomF, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, zoomF, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(animDuration);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }
}
