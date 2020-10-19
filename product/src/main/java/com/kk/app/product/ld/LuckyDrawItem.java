package com.kk.app.product.ld;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kk.app.product.R;


public class LuckyDrawItem extends RelativeLayout {
	

	private ImageView mIvIcon;

	public LuckyDrawItem(Context context) {
		this(context, null);
	}

	public LuckyDrawItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LuckyDrawItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		init();
	}
	
	private void init() {
		RelativeLayout.inflate(getContext(), R.layout.layout_lucky_draw_item, this);
		mIvIcon = findViewById(R.id.iv_lucky_draw_item);
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		mIvIcon.setSelected(selected);
	}
	
	/**
	 * Sets a drawable as the content of this ImageView. 
	 * @param resId the resource identifier of the drawable
	 */
	public void setImageResource(int resId) {
		if (mIvIcon != null) {
			mIvIcon.setImageResource(resId);
		}
	}
	
	/**
	 * Sets a Bitmap as the content of this ImageView.
	 * @param bm The bitmap to set
	 */
	public void setImageBitmap(Bitmap bm) {
		if (mIvIcon != null) {
			mIvIcon.setImageBitmap(bm);
		}
	}

}
