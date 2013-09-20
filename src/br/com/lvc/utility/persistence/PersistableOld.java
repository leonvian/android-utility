package br.com.lvc.utility.persistence;

import java.io.Serializable;

public interface PersistableOld<T> extends Serializable {

	public void setId(T t);
	public T getId();
}
