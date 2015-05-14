/*
 * Copyright (C) 2010 Johan Nilsson <http://markupartist.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.markupartist.android.widget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.markupartist.android.widget.actionbar.R;

public class ActionBar extends RelativeLayout implements OnClickListener {

	private LayoutInflater mInflater;
	private RelativeLayout mBarView;
	private ImageView mLogoView;
	private View mBackIndicator;
	private TextView mTitleView;
	private LinearLayout mActionsView;
	private ImageButton mHomeBtn;
	private RelativeLayout mHomeLayout;
	private ProgressBar mProgress;
	private List<AbstractAction> actions = new ArrayList<AbstractAction>();

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mBarView = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);
		addView(mBarView);

		mLogoView = (ImageView) mBarView.findViewById(R.id.actionbar_home_logo);
		mHomeLayout = (RelativeLayout) mBarView.findViewById(R.id.actionbar_home_bg);
		mHomeBtn = (ImageButton) mBarView.findViewById(R.id.actionbar_home_btn);
		mBackIndicator = mBarView.findViewById(R.id.actionbar_home_is_back);

		mTitleView = (TextView) mBarView.findViewById(R.id.actionbar_title);
		mActionsView = (LinearLayout) mBarView.findViewById(R.id.actionbar_actions);

		mProgress = (ProgressBar) mBarView.findViewById(R.id.actionbar_progress); 
	}

	public void setHomeAction(AbstractAction action) {
		mHomeBtn.setOnClickListener(this);
		mHomeBtn.setTag(action);
		if(action.getDrawableRES() == -1) {
			mHomeBtn.setImageDrawable(action.getDrawable());	
		} else {
			mHomeBtn.setImageResource(action.getDrawableRES());
		}
		mHomeLayout.setVisibility(View.VISIBLE);
	}

	public void clearHomeAction() {
		mHomeLayout.setVisibility(View.GONE);
	}

	/**
	 * Shows the provided logo to the left in the action bar.
	 * 
	 * This is ment to be used instead of the setHomeAction and does not draw
	 * a divider to the left of the provided logo.
	 * 
	 * @param resId The drawable resource id
	 */
	public void setHomeLogo(int resId) {
		// TODO: Add possibility to add an IntentAction as well.
		mLogoView.setImageResource(resId);
		mLogoView.setVisibility(View.VISIBLE);
		mHomeLayout.setVisibility(View.GONE);
	}

	/* Emulating Honeycomb, setdisplayHomeAsUpEnabled takes a boolean
	 * and toggles whether the "home" view should have a little triangle
	 * indicating "up" */
	public void setDisplayHomeAsUpEnabled(boolean show) {
		mBackIndicator.setVisibility(show? View.VISIBLE : View.GONE);
	}


	public void setTitle(CharSequence title) {
		mTitleView.setText(title);
	}

	public void setTitle(int resid) {
		mTitleView.setText(resid);
	}

	/**
	 * Set the enabled state of the progress bar.
	 * 
	 * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
	 *   or {@link View#GONE}.
	 */
	public void setProgressBarVisibility(int visibility) {
		mProgress.setVisibility(visibility);
	}

	/**
	 * Returns the visibility status for the progress bar.
	 * 
	 * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
	 *   or {@link View#GONE}.
	 */
	public int getProgressBarVisibility() {
		return mProgress.getVisibility();
	}

	/**
	 * Function to set a click listener for Title TextView
	 * 
	 * @param listener the onClickListener
	 */
	public void setOnTitleClickListener(OnClickListener listener) {
		mTitleView.setOnClickListener(listener);
	}

	@Override
	public void onClick(View view) {
		final Object tag = view.getTag();
		if (tag instanceof AbstractAction) {
			final AbstractAction action = (AbstractAction) tag;
			action.performAction(view);
		}
	}

	/**
	 * Adds a list of {@link Action}s.
	 * @param actionList the actions to add
	 */
	public void addActions(ActionList actionList) {
		int actions = actionList.size();
		for (int i = 0; i < actions; i++) {
			addAction(actionList.get(i));
		}
	}

	/**
	 * Adds a new {@link Action}.
	 * @param action the action to add
	 */
	public void addAction(AbstractAction action) {
		final int index = mActionsView.getChildCount();
		addAction(action, index);
	}

	/**
	 * Adds a new {@link Action} at the specified index.
	 * @param action the action to add
	 * @param index the position at which to add the action
	 */
	public void addAction(AbstractAction action, int index) {
		if(!actions.contains(action)) {
			mActionsView.addView(inflateAction(action), index);
			actions.add(action);	
		}
	}

	/**
	 * Removes all action views from this action bar
	 */
	public void removeAllActions() {
		actions.clear();
		mActionsView.removeAllViews();
	}

	/**
	 * Remove a action from the action bar.
	 * @param index position of action to remove
	 */
	public void removeActionAt(int index) {
		mActionsView.removeViewAt(index);
	}

	/**
	 * Remove a action from the action bar.
	 * @param action The action to remove
	 */
	public void removeAction(AbstractAction action) {
		int childCount = mActionsView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = mActionsView.getChildAt(i);
			if (view != null) {
				final Object tag = view.getTag();
				if (tag instanceof AbstractAction && tag.equals(action)) {
					mActionsView.removeView(view);
					actions.remove(action);
				}
			}
		}
	}

	/**
	 * Returns the number of actions currently registered with the action bar.
	 * @return action count
	 */
	public int getActionCount() {
		return mActionsView.getChildCount();
	}

	/**
	 * Inflates a {@link View} with the given {@link Action}.
	 * @param action the action to inflate
	 * @return a view
	 */
	private View inflateAction(AbstractAction action) {
		View view = mInflater.inflate(R.layout.actionbar_item, mActionsView, false);

		ImageButton labelView = (ImageButton) view.findViewById(R.id.actionbar_item);
		
		if(action.getDrawableRES() == -1) {
			labelView.setImageDrawable(action.getDrawable());	
		} else {
			labelView.setImageResource(action.getDrawableRES());
		}
		

		view.setTag(action);
		view.setOnClickListener(this);
		return view;
	}

	/**
	 * A {@link LinkedList} that holds a list of {@link Action}s.
	 */
	public static class ActionList extends LinkedList<AbstractAction> {
	}
 
	public static abstract class AbstractAction /*implements Action */ {

		private String tag;
		private Drawable drawable;
		private int drawableRES = -1;
		
		
		public AbstractAction(int drawable) {
			this.drawableRES = drawable;
		}

		public AbstractAction(Drawable drawable) {
			this.drawable = drawable;
		} 
		
		public int getDrawableRES() {
			return drawableRES;
		}

		
		public Drawable getDrawable() {	
			return drawable;
		}
		
		public abstract void performAction(View view);
		public abstract Object getTag();

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((tag == null) ? 0 : tag.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AbstractAction other = (AbstractAction) obj;
			if (tag == null) {
				if (other.tag != null)
					return false;
			} else if (!tag.equals(other.tag))
				return false;
			return true;
		}
	}
	 
}
