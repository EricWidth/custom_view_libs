package com.example.custom_view_lib.labels;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.custom_view_lib.R;

import java.lang.ref.WeakReference;

public class LabelView extends ConstraintLayout {
    private WeakReference<Context> contextRef;
    private ImageView iconView;
    private TextView textView;
    private GradientDrawable backgroundDrawable;

    // 默认值 - 与Android标准控件保持一致
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_SIZE = 12; // sp
    private static final int DEFAULT_ICON_SIZE = 16; // dp
    private static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_WIDTH = 0; // dp
    private static final int DEFAULT_CORNER_RADIUS = 0; // dp
    private static final float DEFAULT_ALPHA = 1.0f;
    private static final int DEFAULT_PADDING = 6; // dp
    private static final int DEFAULT_TEXT_Margin = 4;//dp


    private String mText;
    private int mTextColor;
    private float mTextSizeSP = -1f;
    private float mTextSizeDP = -1f;
    private float mTextSizePx;
    private float mIconSize;
    private Boolean isIconVisible;
    private int mBackgroundColor;
    private float mCornerRadius;
    private  float  mAlphaValue ;
    private int paddingLabelStart;
    private int paddingLabelEnd;
    private int paddingLabelTop;
    private int paddingLabelBottom;
    private Drawable mIconValue ;

    private int mTextMarginStart;
    private int mTextMarginEnd;
    private int mTextMarginTop;
    private int mTextMarginBottom;


    // 修改默认背景透明度为255（完全不透明），与Android标准控件保持一致
    private static final int DEFAULT_BACKGROUND_ALPHA = 255;

    private int mBackgroundAlpha = DEFAULT_BACKGROUND_ALPHA;
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private float mBorderWidth = dpToPx(DEFAULT_BORDER_WIDTH);

    // 存储原始颜色（不考虑透明度）
    private int mSolidBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int mGradientStartColor = 0;
    private int mGradientEndColor = 0;
    private boolean mIsGradientBackground = false;

    public LabelView(Context context) {
        super(context);
        init(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        contextRef = new WeakReference<>(context);
        // 初始化背景Drawable
        backgroundDrawable = new GradientDrawable();

        // 加载布局
        LayoutInflater.from(contextRef.get()).inflate(R.layout.view_label, this, true);

        // 获取子视图
        iconView = findViewById(R.id.iv_label);
        textView = findViewById(R.id.tv_label);

        // 初始化边框颜色和宽度
        mBorderColor = DEFAULT_BORDER_COLOR;
        mBorderWidth = dpToPx(DEFAULT_BORDER_WIDTH);

        // 处理自定义属性
        if (attrs != null) {
            TypedArray a = contextRef.get().obtainStyledAttributes(attrs, R.styleable.LabelView);

            // 文本相关
            mText = a.getString(R.styleable.LabelView_labelText);


            mTextColor = a.getColor(R.styleable.LabelView_labelTextColor, DEFAULT_TEXT_COLOR);


            mTextSizePx = a.getDimension(R.styleable.LabelView_labelTextSize, spToPx(DEFAULT_TEXT_SIZE));

            // 图标相关
            mIconValue = a.getDrawable(R.styleable.LabelView_labelIconSrc);


            isIconVisible = a.getBoolean(R.styleable.LabelView_labelIconVisible, true);

            mIconSize = a.getDimension(R.styleable.LabelView_labelIconSize, dpToPx(DEFAULT_ICON_SIZE));


            // 背景相关
            mBackgroundColor = a.getColor(R.styleable.LabelView_labelBackgroundColor, DEFAULT_BACKGROUND_COLOR);

            // 背景透明度
            mBackgroundAlpha = a.getInt(R.styleable.LabelView_labelBackgroundAlpha, DEFAULT_BACKGROUND_ALPHA);

            mGradientStartColor = a.getColor(R.styleable.LabelView_labelBackgroundStartColor, 0);
            mGradientEndColor = a.getColor(R.styleable.LabelView_labelBackgroundEndColor, 0);


            mCornerRadius = a.getDimension(R.styleable.LabelView_labelCornerRadius, dpToPx(DEFAULT_CORNER_RADIUS));

            // 边框相关
            mBorderColor = a.getColor(R.styleable.LabelView_labelBorderColor, DEFAULT_BORDER_COLOR);

            float borderWidth = a.getDimension(R.styleable.LabelView_labelBorderWidth, dpToPx(DEFAULT_BORDER_WIDTH));
            mBorderWidth = (int) Math.ceil(borderWidth);
            // 透明度
            mAlphaValue = a.getFloat(R.styleable.LabelView_labelAlpha, DEFAULT_ALPHA);

            // 内边距
            int padding = (int) Math.ceil(a.getDimension(R.styleable.LabelView_labelPadding, dpToPx(DEFAULT_PADDING)));
            paddingLabelStart = (int) a.getDimension(R.styleable.LabelView_labelPaddingStart, padding);
            paddingLabelEnd = (int) a.getDimension(R.styleable.LabelView_labelPaddingEnd, padding);
            paddingLabelTop = (int) a.getDimension(R.styleable.LabelView_labelPaddingTop, padding);
            paddingLabelBottom = (int) a.getDimension(R.styleable.LabelView_labelPaddingBottom, padding);

            //文字外边距
            int margin = (int) Math.ceil(a.getDimension(R.styleable.LabelView_labelTextMargin, dpToPx(DEFAULT_TEXT_Margin)));
            mTextMarginStart = (int) a.getDimension(R.styleable.LabelView_labelTextMarginStart, margin);
            mTextMarginEnd = (int) a.getDimension(R.styleable.LabelView_labelTextMarginEnd, 0);
            mTextMarginTop = (int) a.getDimension(R.styleable.LabelView_labelTextMarginTop, 0);
            mTextMarginBottom = (int) a.getDimension(R.styleable.LabelView_labelTextMarginBottom, 0);

            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        applyAllAttributes();
    }

    private void applyAllAttributes() {
        if (Float.compare(mTextSizeSP, 0f) > 0) {
            setTextSizeSp(mTextSizeSP);
        } else if (Float.compare(mTextSizeDP, 0f) > 0) {
            setTextSizeDp(mTextSizeDP);
        } else {
            setTextSizePx(mTextSizePx);
        }
        if (mText != null) {
            setText(mText);
        }
        setTextColor(mTextColor);
        if (mIconValue != null) {
            setIcon(mIconValue);
        }
        setIconVisible(isIconVisible);
        setIconSize(mIconSize);
        setBackgroundColor(mBackgroundColor);
        setBackgroundAlpha(mBackgroundAlpha);
        setCornerRadiusPx(mCornerRadius);
        if (mGradientStartColor != 0 && mGradientEndColor != 0) {
            setBackgroundGradient(mGradientStartColor, mGradientEndColor);
        }
        setAlphaValue(mAlphaValue);
        setPadding(paddingLabelStart, paddingLabelTop, paddingLabelEnd, paddingLabelBottom);
        setBorderWidthPx(mBorderWidth);
        setBorderColor(mBorderColor);
        setTextMarginPx(mTextMarginStart, mTextMarginTop, mTextMarginEnd, mTextMarginBottom);
    }



    // ========== 背景透明度相关方法 ==========

    /**
     * 设置背景透明度（0-255），与Android标准控件保持一致
     */
    public void setBackgroundAlpha(int alpha) {
        mBackgroundAlpha = alpha;
        updateBackgroundWithAlpha();
    }

    /**
     * 设置背景透明度（0f-1f），与View.setAlpha()方法保持一致
     */
    public void setBackgroundAlpha(float alpha) {
        setBackgroundAlpha((int) (alpha * 255));
    }

    /**
     * 获取当前背景透明度（0-255）
     */
    public int getBackgroundAlpha() {
        return mBackgroundAlpha;
    }

    /**
     * 获取当前背景透明度（0f-1f）
     */
    public float getBackgroundAlphaFloat() {
        return mBackgroundAlpha / 255.0f;
    }

    /**
     * 更新背景颜色，考虑透明度
     */
    private void updateBackgroundWithAlpha() {
        if (mIsGradientBackground) {
            // 处理渐变背景透明度
            int startColorWithAlpha = applyAlphaToColor(mGradientStartColor, mBackgroundAlpha);
            int endColorWithAlpha = applyAlphaToColor(mGradientEndColor, mBackgroundAlpha);

            backgroundDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            backgroundDrawable.setColors(new int[]{startColorWithAlpha, endColorWithAlpha});
        } else {
            // 处理纯色背景透明度
            int colorWithAlpha = applyAlphaToColor(mSolidBackgroundColor, mBackgroundAlpha);
            backgroundDrawable.setColor(colorWithAlpha);
        }
    }

    /**
     * 将透明度应用到颜色
     */
    private int applyAlphaToColor(@ColorInt int color, int alpha) {
        // 如果颜色已经是透明色，直接返回
        if (color == Color.TRANSPARENT) {
            return color;
        }

        // 确保alpha值在0-255范围内
        alpha = Math.max(0, Math.min(255, alpha));

        return Color.argb(
                alpha,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    /**
     * 设置背景颜色（颜色值），保留当前透明度
     */
    @Override
    public void setBackgroundColor(@ColorInt int color) {
        mSolidBackgroundColor = color;
        mIsGradientBackground = false;
        mBackgroundColor = color;
        // 应用当前透明度到新颜色
        updateBackgroundWithAlpha();
    }

    /**
     * 设置渐变背景（开始和结束颜色值），保留当前透明度
     */
    public void setBackgroundGradient(@ColorInt int startColor, @ColorInt int endColor) {
        mGradientStartColor = startColor;
        mGradientEndColor = endColor;
        mIsGradientBackground = true;

        // 应用当前透明度到新颜色
        updateBackgroundWithAlpha();
    }

    // ========== 文本相关方法 ==========

    /**
     * 设置标签文字
     */
    public void setText(String text) {
        textView.setText(text);
        mText = text;
    }

    /**
     * 获取标签文字
     */
    public String getText() {
        return textView.getText().toString();
    }

    /**
     * 设置文字颜色（颜色资源ID）
     */
    public void setTextColorRes(@ColorRes int colorRes) {
        setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置文字颜色（颜色值）
     */
    public void setTextColor(@ColorInt int color) {
        textView.setTextColor(color);
        mTextColor = color;
    }

    /**
     * 设置文字颜色（十六进制字符串）
     */
    public void setTextColor(String colorHex) {
        try {
            setTextColor(Color.parseColor(colorHex));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置文字大小（像素）优先级SP>DP>PX
     */
    public void setTextSizePx(float sizePx) {
        mTextSizePx = sizePx;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx);
    }

    /**
     * 设置文字大小（DP单位）优先级SP>DP>PX
     */
    public void setTextSizeDp(float sizePx) {
        mTextSizeDP = sizePx;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizePx);
    }

    /**
     * 设置文字大小（SP单位） 优先级SP>DP>PX
     */
    public void setTextSizeSp(float sizeSp) {
        mTextSizeSP = sizeSp;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
    }

    // ========== 图标相关方法 ==========

    /**
     * 设置图标（资源ID）
     */
    public void setIcon(@DrawableRes int resId) {
        iconView.setImageResource(resId);
        setIconVisible(true);
    }

    /**
     * 设置图标（Drawable）
     */
    public void setIcon(Drawable drawable) {
        iconView.setImageDrawable(drawable);
        setIconVisible(true);
        mIconValue = drawable;
    }

    /**
     * 设置图标（Bitmap）
     */
    public void setIcon(Bitmap bitmap) {
        iconView.setImageBitmap(bitmap);
        setIconVisible(true);
    }

    /**
     * 设置图标（网络路径 - 需要外部图片加载库）
     */
    public void setIcon(String imageUrl) {
//        ImageLoaderUtil.with().loadImage(
//                iconView.getContext(),
//                iconView,
//                imageUrl,
//                R.drawable.ai_replace_progressbar_common_loading,
//                R.drawable.ai_replace_loading_background
//        );
        Glide.with(contextRef.get())
                .load(imageUrl)
                .into(iconView);
        setIconVisible(true);
    }

    /**
     * 设置图标大小（像素）
     */
    public void setIconSize(float sizePx) {
        ViewGroup.LayoutParams params = iconView.getLayoutParams();
        params.width = (int) sizePx;
        params.height = (int) sizePx;
        iconView.setLayoutParams(params);
        mIconSize = sizePx;
    }

    /**
     * 设置图标大小（DP单位）
     */
    public void setIconSizeDp(float sizeDp) {
        setIconSize(dpToPx(sizeDp));
    }

    /**
     * 设置图标显示/隐藏
     */
    public void setIconVisible(boolean visible) {
        iconView.setVisibility(visible ? View.VISIBLE : View.GONE);
        isIconVisible = visible;
    }

    /**
     * 获取图标是否显示
     */
    public boolean isIconVisible() {
        return iconView.getVisibility() == View.VISIBLE;
    }

    // ========== 背景颜色相关方法 ==========

    /**
     * 设置背景颜色（颜色资源ID）
     */
    public void setBackgroundColorRes(@ColorRes int colorRes) {
        setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置背景颜色（十六进制字符串）
     */
    public void setBackgroundColor(String colorHex) {
        try {
            setBackgroundColor(Color.parseColor(colorHex));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置渐变背景（开始和结束颜色的资源ID）
     */
    public void setBackgroundGradientRes(@ColorRes int startColorRes, @ColorRes int endColorRes) {
        setBackgroundGradient(
                ContextCompat.getColor(getContext(), startColorRes),
                ContextCompat.getColor(getContext(), endColorRes)
        );
    }

    /**
     * 设置渐变背景（开始和结束颜色的十六进制字符串）
     */
    public void setBackgroundGradient(String startColorHex, String endColorHex) {
        try {
            setBackgroundGradient(
                    Color.parseColor(startColorHex),
                    Color.parseColor(endColorHex)
            );
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    // ========== 边框相关方法 ==========

    /**
     * 设置边框颜色（颜色资源ID）
     */
    public void setBorderColorRes(@ColorRes int colorRes) {
        setBorderColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置边框颜色（颜色值）
     */
    public void setBorderColor(@ColorInt int color) {
        // 保存当前边框宽度，然后设置新的颜色
        backgroundDrawable.setStroke((int) Math.ceil(mBorderWidth), color);
        mBorderColor = color; // 更新存储的边框颜色
    }

    /**
     * 设置边框颜色（十六进制字符串）
     */
    public void setBorderColor(String colorHex) {
        try {
            setBorderColor(Color.parseColor(colorHex));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置边框宽度（像素）
     */
    public void setBorderWidthPx(float widthPx) {
        // 使用当前边框颜色，而不是重置为透明
        backgroundDrawable.setStroke((int) Math.ceil(widthPx), mBorderColor);
        mBorderWidth = widthPx; // 更新存储的边框宽度
    }

    /**
     * 设置边框宽度（DP单位）
     */
    public void setBorderWidthDp(float widthDp) {
        setBorderWidthPx(dpToPx(widthDp));
    }

    // ========== 其他方法 ==========

    /**
     * 设置圆角半径（像素）
     */
    public void setCornerRadiusPx(float radiusPx) {
        backgroundDrawable.setCornerRadius(radiusPx);
        mCornerRadius = radiusPx;
    }

    /**
     * 设置圆角半径（DP单位）
     */
    public void setCornerRadiusDp(float radiusDp) {
        setCornerRadiusPx(dpToPx(radiusDp));
    }

    /**
     * 设置整个标签的透明度
     */
    public void setAlphaValue(float alpha) {
        mAlphaValue = alpha;
        setAlpha(alpha);
    }

    /**
     * 设置内边距（像素）
     */
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        textView.setPadding(0, 0, 0, 0);// 确保文字内容紧贴容器边界
        paddingLabelStart = left;
        paddingLabelEnd = right;
        paddingLabelTop = top;
        paddingLabelBottom = bottom;
    }

    /**
     * 设置内边距（DP单位）
     */
    public void setPaddingDp(float left, float top, float right, float bottom) {
        setPadding(
                (int) Math.ceil(dpToPx(left)),
                (int) Math.ceil(dpToPx(top)),
                (int) Math.ceil(dpToPx(right)),
                (int) Math.ceil(dpToPx(bottom))
        );
    }

    /**
     * 配置文字外边距（像素）
     */
    public void setTextMarginPx(int left, int top, int right, int bottom){
        if (textView == null) return;
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams params = (MarginLayoutParams) layoutParams;
            //约束采用下面这种方式，不要采用setMargin()方法，当在约束布局中设置了约束边距，则setMargin()方法无效
            params.setMarginStart(left);
            params.topMargin = top;
            params.setMarginEnd(right);
            params.bottomMargin = bottom;
            textView.setLayoutParams(params);
            mTextMarginStart = left;
            mTextMarginTop = top;
            mTextMarginEnd = right;
            mTextMarginBottom = bottom;
        }
    }

    /**
     * 配置文字外边距（DP单位）
     */
    public void setTextMarginDp(float left, float top, float right, float bottom){
        setTextMarginPx(
                (int) Math.ceil(dpToPx(left)),
                (int) Math.ceil(dpToPx(top)),
                (int) Math.ceil(dpToPx(right)),
                (int) Math.ceil(dpToPx(bottom))
        );
    }




//暂时不需要

//    public void setIconPaddingPx(int left, int top, int right, int bottom){
//        iconView.setPadding(left, top, right, bottom);
//        mIconPaddingStart = left;
//        mIconPaddingTop = top;
//        mIconPaddingEnd = right;
//        mIconPaddingBottom = bottom;
//    }
//
//    public void setIconPaddingDp(float left, float top, float right, float bottom){
//        setIconPaddingPx(
//                (int) Math.ceil(dpToPx(left)),
//                (int) Math.ceil(dpToPx(top)),
//                (int) Math.ceil(dpToPx(right)),
//                (int) Math.ceil(dpToPx(bottom))
//        );
//    }    /**
//     * 设置图标外边距（像素）
//     */
//    public void setIconMarginPx(int left, int top, int right, int bottom) {
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) iconView.getLayoutParams();
//        params.setMargins(left, top, right, bottom);
//        iconView.setLayoutParams(params);
//    }
//
//    /**
//     * 设置图标外边距（DP单位）
//     */
//    public void setIconMarginDp(float left, float top, float right, float bottom) {
//        setIconMarginPx(
//                (int) Math.ceil(dpToPx(left)),
//                (int) Math.ceil(dpToPx(top)),
//                (int) Math.ceil(dpToPx(right)),
//                (int) Math.ceil(dpToPx(bottom))
//        );
//    }

    // ========== 工具方法 ==========

    /**
     * 将最后一次设置的属性应用到标签上
     */
    public void applyLastAttributes() {
        applyAllAttributes();
        setBackground(backgroundDrawable);
    }

    /**
     * DP转PX
     */
    private float dpToPx(float dp) {
        return (dp * getResources().getDisplayMetrics().density);
    }

    /**
     * SP转PX
     */
    private float spToPx(float sp) {
        return (sp * getResources().getDisplayMetrics().scaledDensity);
    }
}