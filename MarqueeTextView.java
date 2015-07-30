package com.youku.tv.launcher.view;


import java.lang.reflect.Method;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.TextView;



public class MarqueeTextView extends TextView {
//add    
    private static final String TAG = "MarqueeTextView";
    private boolean isMarquee = false;

	public boolean isMarquee() {
		return isMarquee;
	}

	public void setMarquee(boolean isMarquee) {
		this.isMarquee = isMarquee;
		setText(getText());	
	}
	
	

	public MarqueeTextView(Context context) {
		super(context);
		
		init();
	}

	public MarqueeTextView(Context context, boolean b) {
		super(context);
		isMarquee = b;
		
		init();
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init();
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
	}

	/**
	 * set default value for easily usage.
	 */
	private void init() {
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(Integer.MAX_VALUE);
	}
	
	

	@Override
	public boolean isFocused() {
		return isMarquee;
	}

	/**
	 * 为了解决列表里面第一次不滚动的情况，需要修改
	 * */
	@Override
	public void setEllipsize(TruncateAt where) {
		super.setEllipsize(where);
		
		boolean isFadingMarqueeEnabled = true;
		
		try {
			ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
			Method method = viewConfiguration.getClass().getMethod("isFadingMarqueeEnabled");
			if (method != null) {
				isFadingMarqueeEnabled = (Boolean) method.invoke(viewConfiguration);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
        setHorizontalFadingEdgeEnabled(isFadingMarqueeEnabled);
        
	}

	
	/**
	 * !!!!为了避免在listview中被强行设置，setSelected方法不做任何事情，请调用setSelectedIndeed！！！我要崩溃了。。。。
	 * */
	@Override
	public void setSelected(boolean selected) {
		
	}
	
	public void setSelectedIndeed(boolean selected) {
		super.setSelected(selected);
	}
	
	
	public Method getColumnGetMethod(Class<?> entityType,String methodName,final Class[] classes) {
        Method getMethod = null;
        
        if (getMethod == null) {
            try {
                getMethod = entityType.getDeclaredMethod(methodName,classes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if (getMethod == null && !Object.class.equals(entityType.getSuperclass())) {
            return getColumnGetMethod(entityType.getSuperclass(), methodName,classes);
        }
        return getMethod;
    }

	
	
	
}
