package com.example.mypaint.view;

import com.example.mypaint.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class XCArcMenuView extends ViewGroup implements OnClickListener {
	/*
	 * Œ¿–«≤Àµ•
	 */
	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	private Position mPosition = Position.RIGHT_BOTTOM;
	private int mRadius;
	private Status mStatus = Status.CLOSE;
	private View mCButton;
	private OnMenuItemClickListener mOnMenuItemClickListener;

	public enum Status {
		OPEN, CLOSE
	}

	public enum Position {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	public interface OnMenuItemClickListener {
		void onClick(View view, int pos);
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
		this.mOnMenuItemClickListener = onMenuItemClickListener;
	}

	public XCArcMenuView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public XCArcMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public XCArcMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.XCArcMenuView, defStyle, 0);
		int pos = a
				.getInt(R.styleable.XCArcMenuView_position, POS_RIGHT_BOTTOM);
		switch (pos) {
		case POS_LEFT_TOP:
			mPosition = Position.LEFT_TOP;
			break;
		case POS_LEFT_BOTTOM:
			mPosition = Position.LEFT_BOTTOM;
			break;
		case POS_RIGHT_TOP:
			mPosition = Position.RIGHT_TOP;
			break;
		case POS_RIGHT_BOTTOM:
			mPosition = Position.RIGHT_BOTTOM;
			break;
		}
		mRadius = (int) a.getDimension(R.styleable.XCArcMenuView_radius,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						150, getResources().getDisplayMetrics()));
		Log.v("czm", "mPosition = " + mPosition + ",mRadius = " + mRadius);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (changed) {
			layoutCButton();
			layoutMenuItems();
		}
	}

	private void layoutCButton() {
		// TODO Auto-generated method stub
		mCButton = getChildAt(0);
		mCButton.setOnClickListener(this);
		int l = 0;
		int t = 0;
		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();
		switch (mPosition) {
		case LEFT_TOP:
			l = 0;
			t = 0;
			break;
		case LEFT_BOTTOM:
			l = 0;
			t = getMeasuredHeight() - height;
			break;
		case RIGHT_TOP:
			l = getMeasuredWidth() - width;
			t = 0;
			break;
		case RIGHT_BOTTOM:
			l = getMeasuredWidth() - width;
			t = getMeasuredHeight() - height;
			break;
		default:
			break;
		}
		mCButton.layout(l, t, l + width, t + height);
	}

	private void layoutMenuItems() {
		// TODO Auto-generated method stub
		int count = getChildCount();
		for (int i = 0; i < count - 1; i++) {
			View child = getChildAt(i + 1);
			int l = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int t = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();

			if (mPosition == Position.LEFT_BOTTOM
					|| mPosition == Position.RIGHT_BOTTOM) {
				t = getMeasuredHeight() - height - t;
			}
			if (mPosition == Position.RIGHT_TOP
					|| mPosition == Position.RIGHT_BOTTOM) {
				l = getMeasuredWidth() - width - l;
			}
			child.layout(l, t, l + width, t + height);
			child.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// mCButton = findViewById(R.id.id_button);
		rotateCButton(v, 0, 360, 300);
		toggleMenu(300);
	}

	public void toggleMenu(int duration) {
		// TODO Auto-generated method stub
		int count = getChildCount();

		for (int i = 0; i < count - 1; i++) {
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);

			// end 0 , 0
			// start
			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

			int xflag = 1;
			int yflag = 1;

			if (mPosition == Position.LEFT_TOP
					|| mPosition == Position.LEFT_BOTTOM) {
				xflag = -1;
			}

			if (mPosition == Position.LEFT_TOP
					|| mPosition == Position.RIGHT_TOP) {
				yflag = -1;
			}

			AnimationSet animset = new AnimationSet(true);
			Animation tranAnim = null;

			// to open
			if (mStatus == Status.CLOSE) {
				tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
				childView.setClickable(true);
				childView.setFocusable(true);

			} else
			// to close
			{
				tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			tranAnim.setFillAfter(true);
			tranAnim.setDuration(duration);
			tranAnim.setStartOffset((i * 100) / count);

			tranAnim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (mStatus == Status.CLOSE) {
						childView.setVisibility(View.GONE);
					}
				}
			});

			RotateAnimation rotateAnim = new RotateAnimation(0, 720,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);

			animset.addAnimation(rotateAnim);
			animset.addAnimation(tranAnim);
			childView.startAnimation(animset);

			final int pos = i + 1;
			childView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnMenuItemClickListener != null)
						mOnMenuItemClickListener.onClick(childView, pos);

					menuItemAnim(pos - 1);
					changeStatus();

				}
			});
		}

		changeStatus();

	}

	private void rotateCButton(View v, float start, float end, int duration) {
		// TODO Auto-generated method stub
		RotateAnimation anim = new RotateAnimation(start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}

	private void menuItemAnim(int pos) {
		for (int i = 0; i < getChildCount() - 1; i++) {

			View childView = getChildAt(i + 1);
			if (i == pos) {
				childView.startAnimation(scaleBigAnim(300));
			} else {

				childView.startAnimation(scaleSmallAnim(300));
			}

			childView.setClickable(false);
			childView.setFocusable(false);

		}

	}

	private Animation scaleSmallAnim(int duration) {

		AnimationSet animationSet = new AnimationSet(true);

		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	private Animation scaleBigAnim(int duration) {
		AnimationSet animationSet = new AnimationSet(true);

		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);

		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	private void changeStatus() {
		mStatus = (mStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
	}

	public boolean isOpen() {
		return mStatus == Status.OPEN;
	}

}
