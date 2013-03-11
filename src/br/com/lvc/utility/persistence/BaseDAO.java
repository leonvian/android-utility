package br.com.lvc.utility.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressWarnings("rawtypes")
public abstract class BaseDAO<T extends Persistable> {

	private SQLiteOpenHelper sqLiteHelper = null;
	private SQLiteDatabase dataBase = null;
	
	public BaseDAO() {
		sqLiteHelper = getSQLiteOpenHelper();
		dataBase = sqLiteHelper.getWritableDatabase();
	}
	
	 
	protected abstract String getNomeTabela();
	protected abstract String[] getColunas();	
	protected abstract SQLiteOpenHelper getSQLiteOpenHelper();
	protected abstract ContentValues getContentValue(T persistable);
	protected abstract String getWhereArgumentoIdentificacaoPorId();
	protected abstract String[] getWhereArgsArgumentoIdentificacaoPorId();
	protected abstract ArrayList<T> cursorParaList(Cursor cursor);
	
	public void salvarOuAtualizar(T persistable) {		
		if (persistable.getId() != null) {
			atualizar(persistable);
		} else {
			inserir(persistable);
		}		
	}

	public long inserir(T persistable) {		
		ContentValues values = getContentValue(persistable);		
		long id = dataBase.insert(getNomeTabela(), null,values);
		return id;
	}

	public int atualizar(T persistable) {
		ContentValues values = getContentValue(persistable);		
		int count = dataBase.update(getNomeTabela(), values, getWhereArgumentoIdentificacaoPorId(), getWhereArgsArgumentoIdentificacaoPorId());
		return count;

	}

	public void deletar(T persistable) {				
		dataBase.delete(getNomeTabela(), getWhereArgumentoIdentificacaoPorId(), getWhereArgsArgumentoIdentificacaoPorId());		
	}


	public ArrayList<T> buscarTodasTsUsandoRawQuery() {
		ArrayList<T> persistables = new ArrayList<T>();
		String sql = "select * from " + getNomeTabela();
		Cursor cursor = dataBase.rawQuery(sql, null);
		persistables = cursorParaList(cursor);		
		return persistables;
	}


	public ArrayList<T> buscarTudo() {
		ArrayList<T> persistables = null;
		Cursor cursor = dataBase.query(getNomeTabela(), getColunas(), null, null, null,null, null);
		persistables = cursorParaList(cursor);
		return persistables;
	}	
	
	public void deletarTodos() {
		dataBase.execSQL("delete from " + getNomeTabela());		
	}	

	public void fechar() {
		if (dataBase != null) {
			dataBase.close();
		}		
	}

}
