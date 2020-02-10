package com.kk.app.lib.widget.wrapview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:自动换行view
 */
public class WordWrapView extends ViewGroup {
	  private int PADDING_HOR = 24;//水平方向padding
	  private int PADDING_VERTICAL = 17;//垂直方向padding
	  private int SIDE_MARGIN = 20;//上下间距
	  private int TEXT_MARGIN = 20;//左右间距
	  private ItemViewClickListener listener;
	  private int MaxRow=4;
	  private boolean isMax=false;
	  private int Count; 
	  /**
	   * @param context
	   */
	  public WordWrapView(Context context) {
	    super(context);
	  }
	  
	  /**
	   * @param context
	   * @param attrs
	   * @param defStyle
	   */
	  public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	  }



	  /**
	   * @param context
	   * @param attrs
	   */
	  public WordWrapView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }



	  @Override
	  protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    int childCount = getChildCount();
	    int autualWidth = r - l;
	    int x = 0;// 横坐标开始
	    int y = 0;//纵坐标开始
	    int rows = 1;
	    for(int i=0;i<childCount;i++){
	      final int item=i;
	      View view = getChildAt(i);
	      int width = view.getMeasuredWidth();
	      int height = view.getMeasuredHeight();
	      x += width+TEXT_MARGIN;
	      if(x>autualWidth){
	        x = width;
//	    	x=0;
	        rows++;
	      }
	      y = rows*(height+TEXT_MARGIN);
	      if(i==0){
	        view.layout(x-width-TEXT_MARGIN, y-height, x, y);
	      }else{
	        view.layout(x-width, y-height, x, y);
	      }
	      view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listener!=null) {
					listener.click(v,item);
				}
			}
		});
	      if (rows>MaxRow) {
	    	  removeView(view);
	    	  Count=i-1;
	    	  isMax=true;
			return;
		}
	    }
	  };

	  @Override
	  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int x = 0;//横坐标
	    int y = 0;//纵坐标
	    int rows = 1;//总行数
	    int specWidth = MeasureSpec.getSize(widthMeasureSpec);
	    int actualWidth = specWidth - SIDE_MARGIN * 2;//实际宽度
	    int childCount;
	    if (isMax) {
			childCount=Count;
		}else{
			childCount=getChildCount();
		}
	    for(int index = 0;index<childCount;index++){
	      View child = getChildAt(index);
	      child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR, PADDING_VERTICAL);
	      child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	      int width = child.getMeasuredWidth();
	      int height = child.getMeasuredHeight();
	      x += width+TEXT_MARGIN;
	      if(x>actualWidth){//换行
	        x = width;
	        rows++;
	      }
	      y = rows*(height+TEXT_MARGIN);
	    }
	    setMeasuredDimension(actualWidth, y);
	  } 
	  
	public ItemViewClickListener getListener() {
		return listener;
	}

	public void setListener(ItemViewClickListener listener) {
		this.listener = listener;
	}

	public interface ItemViewClickListener{
		public void click(View view, int item);
	}
	
	@Override
	public void removeAllViews() {
		isMax=false;
		super.removeAllViews();
	}
	
	@Override
	public void addView(View child) {
		if (isMax) {
			return;
		}
		super.addView(child);
	}

	public int getMaxRow() {
		return MaxRow;
	}

	public void setMaxRow(int maxRow) {
		MaxRow = maxRow;
	}

	public int getPADDING_VERTICAL() {
		return PADDING_VERTICAL;
	}

	public void setPADDING_VERTICAL(int pADDING_VERTICAL) {
		PADDING_VERTICAL = pADDING_VERTICAL;
	}

	public int getSIDE_MARGIN() {
		return SIDE_MARGIN;
	}

	public void setSIDE_MARGIN(int sIDE_MARGIN) {
		SIDE_MARGIN = sIDE_MARGIN;
	}

	public int getPADDING_HOR() {
		return PADDING_HOR;
	}

	public void setPADDING_HOR(int pADDING_HOR) {
		PADDING_HOR = pADDING_HOR;
	}

	public int getTEXT_MARGIN() {
		return TEXT_MARGIN;
	}

	public void setTEXT_MARGIN(int tEXT_MARGIN) {
		TEXT_MARGIN = tEXT_MARGIN;
	}

}

