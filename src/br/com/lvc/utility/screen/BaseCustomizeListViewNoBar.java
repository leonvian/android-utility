package br.com.lvc.utility.screen;



import br.com.lvc.utility.R;

import com.markupartist.android.widget.ActionBar;

public abstract class BaseCustomizeListViewNoBar<T, Z extends BaseCustomAdapter<T>> extends BaseCustomizeListView<T, BaseCustomAdapter<T>> {
	
	@Override
	protected int layoutID() {
		return R.layout.customize_list_no_bar;
	}

	@Override
	protected void configureActionBar(ActionBar actionBar) {
		// Not necessary
	}

}
