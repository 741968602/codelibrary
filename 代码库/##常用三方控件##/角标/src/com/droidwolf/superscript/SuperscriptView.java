package com.droidwolf.superscript;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class SuperscriptView extends TextView {
	private float mDegress,mX,mY;
	private int mHeight,mWidth;
	
	public SuperscriptView(Context context) {
		super(context);
		init(context, null);
	}

	public SuperscriptView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SuperscriptView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	@Override
	public void setVisibility(int visibility) {
		setAnimation(visibility == View.VISIBLE? mAnimation: null);
		super.setVisibility(visibility);
	}

	private void init(Context context, AttributeSet attrs) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int topEdge = Math.round(TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 41.333f, dm));
		int  leftEdge= Math.round(TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 41.333f, dm));
		calc(leftEdge, topEdge);

		mAnimation.setFillBefore(true);
		mAnimation.setFillAfter(true);
		mAnimation.setFillEnabled(true);
		startAnimation(mAnimation);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mHeight < 1 || mWidth < 1) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			setMeasuredDimension(mWidth, mHeight);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int vi= getVisibility();
		setAnimation(null);
		setVisibility(vi);
	}
	
	private void calc(int leftEdge, int topEdge) {
		final double ab = Math.sqrt(Math.pow(topEdge, 2d)+ Math.pow(leftEdge, 2d));
		final double sinB = leftEdge / ab;
		mDegress = -(float) Math.toDegrees(Math.asin(sinB));

		// ef=da=sin(］ebf)*eb
		mHeight = Math.round((float) (sinB * topEdge));

		// de=sin(］ead)*ea=sin(］ebf)*ea
		final double de = sinB * leftEdge;

		// dg=cos(］ead)*de=cos(］ebf)*de
		mX = -(float) ((topEdge / ab) * de);

		// eg==sin(］edg)*de=sin(］ebf)*de
		mY = (float) (sinB * de);
		mWidth = Math.round((float) ab);
	}

	private Animation mAnimation = new Animation() {
		protected void applyTransformation(float interpolatedTime,Transformation t) {
			if (mHeight < 1 || mWidth < 1) {
				return;
			}
			Matrix tran = t.getMatrix();
			tran.setTranslate(mX, mY);
			tran.postRotate(mDegress, mX, mY);
		}
	};
}
