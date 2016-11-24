package com.example.matheus.meugerenciadorfinanceiro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matheus-HP on 22/11/2016.
 */
public class LancamentoSqlHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ClassesLancamento.Lancamento.TABLE_NAME + " (" +
                    ClassesLancamento.Lancamento._ID + " INTEGER PRIMARY KEY," +
                    ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO + DefinicaoDb.INTEGER_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO + DefinicaoDb.TEXT_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_DATA + DefinicaoDb.TEXT_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_VALOR + DefinicaoDb.FLOAT_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS + DefinicaoDb.INTEGER_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_TIPO + DefinicaoDb.TEXT_TYPE + DefinicaoDb.COMMA_SEP +
                    ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA + DefinicaoDb.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ClassesLancamento.Lancamento.TABLE_NAME;

    public LancamentoSqlHelper(Context context) {
        super(context, DefinicaoDb.DATABASE_NAME, null, DefinicaoDb.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENTRIES);}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
