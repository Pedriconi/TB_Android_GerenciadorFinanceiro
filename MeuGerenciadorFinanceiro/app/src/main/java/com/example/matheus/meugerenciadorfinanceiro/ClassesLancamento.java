package com.example.matheus.meugerenciadorfinanceiro;

import android.provider.BaseColumns;

/**
 * Created by Matheus-HP on 22/11/2016.
 */
public final class ClassesLancamento {

    private ClassesLancamento(){ }

    public static class Lancamento implements BaseColumns{
        public static final String TABLE_NAME = "lancamentos";
        public static final String COLUMN_NAME_CODIGO = "codigo";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_VALOR = "valor";
        public static final String COLUMN_NAME_PARCELAS = "parcelas";
        public static final String COLUMN_NAME_TIPO = "tipo";
        public static final String COLUMN_NAME_CATEGORIA = "categoria";
    }
}
