package br.com.lvc.utility.screen;

import java.io.Serializable;
import java.util.List;

import br.com.lvc.utility.exceptions.AndroidAppException;

public class BaseListFragment<T extends Serializable> extends ListGenericFragments<T> {

	@Override
	protected List<T> getListElements() throws AndroidAppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onClickItem(T element) {
		// TODO Auto-generated method stub
		
	}

	 

}
