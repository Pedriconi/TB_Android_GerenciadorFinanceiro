package com.example.matheus.meugerenciadorfinanceiro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Matheus-HP on 22/11/2016.
 */
public class LancamentoDAO {

    SimpleDateFormat parseFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public void insert(Lancamento lancamento, Context context) {
        // Obtem a referencia do BD em modo escrita
        LancamentoSqlHelper lancamentoSqlHelper = new LancamentoSqlHelper(context);
        SQLiteDatabase db = lancamentoSqlHelper.getWritableDatabase();

        // Cria um Map de colunas (Key) e dados (Value)
        ContentValues values = new ContentValues();
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO, lancamento.getCodigo());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO, lancamento.getDescricao());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_DATA, lancamento.getData().toString());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_VALOR, lancamento.getValor());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS, lancamento.getParcelas());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_TIPO, lancamento.getTipo());
        values.put(ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA, lancamento.getCategoria());

        // Insere a nova linha e retorna a chave primaria atribuída ao objeto.
        long id = db.insert(ClassesLancamento.Lancamento.TABLE_NAME, null, values);
    }

    public ArrayList<Lancamento> selectAll(Context context) {
        // Obtem a referencia do BD em modo leitura
        LancamentoSqlHelper lancamentoSqlHelper = new LancamentoSqlHelper(context);
        SQLiteDatabase db = lancamentoSqlHelper.getReadableDatabase();

        // Define quais colunas devem ser lidas do BD.
        String[] projection = {
                ClassesLancamento.Lancamento._ID,
                ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO,
                ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO,
                ClassesLancamento.Lancamento.COLUMN_NAME_DATA,
                ClassesLancamento.Lancamento.COLUMN_NAME_VALOR,
                ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS,
                ClassesLancamento.Lancamento.COLUMN_NAME_TIPO,
                ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA
        };

        ArrayList<Lancamento> lancamentos = new ArrayList<>();

        // Ordenação da consulta
        String sortOrder = ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO + " ASC";

        //Montagem da Consulta
        Cursor cursorLancamentos = db.query(
                ClassesLancamento.Lancamento.TABLE_NAME,         // Tabela
                projection,                               // Colunas a serem lidas
                null,                                     // Clausula Where
                null,                                     // Valores da clausula where
                null,                                     // Definição de grupos
                null,                                     // Definição de filtros de grupo
                sortOrder                                 // Ordenação
        );

        if (cursorLancamentos.moveToFirst()) {
            while (cursorLancamentos.isAfterLast() == false) {
                int codigo = cursorLancamentos.getInt(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO));
                String descricao = cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO));
                Date data = null;
                //String[] auxiliarData = cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA)).split(" ");
                //descricao = gambiarraData(cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA)));
                try {
                    //Calendar cal = Calendar.getInstance();
                    //cal.setTime(data);
                    //String formatedData = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
                    //data = format.parse(formatedData);
                    //descricao = formatedData;
                    data = format.parse(gambiarraData(cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA))));
                    //java.sql.Date sql = new java.sql.Date(data.getTime());
                    //data = new Date(format.parse(String.valueOf(cursorLancamentos.getLong(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA)))).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Date data = new Date(cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA)));
                Float valor = cursorLancamentos.getFloat(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_VALOR));
                int parcelas = cursorLancamentos.getInt(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS));
                String tipo = cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_TIPO));
                String categoria = cursorLancamentos.getString(cursorLancamentos.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA));

                lancamentos.add(new Lancamento(codigo,descricao,data,valor,parcelas,tipo,categoria));
                cursorLancamentos.moveToNext();
            }
        }
        cursorLancamentos.close();
        return lancamentos;
    }
    public void alteraRegistro(Context context, int id, String descricao, Date data, float valor, String categoria) {
        // Obtem a referencia do BD em modo escrita
        LancamentoSqlHelper lancamentoSqlHelper = new LancamentoSqlHelper(context);
        SQLiteDatabase db = lancamentoSqlHelper.getWritableDatabase();

        ContentValues valores;
        String where;

        //db = banco.getWritableDatabase();

        where = ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO + "=" + id;

        valores = new ContentValues();
        valores.put(ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO, descricao);
        try {
            data = format.parse(gambiarraData(data.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        valores.put(ClassesLancamento.Lancamento.COLUMN_NAME_DATA, data.toString());
        valores.put(ClassesLancamento.Lancamento.COLUMN_NAME_VALOR, valor);
        valores.put(ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA, categoria);

        db.update(ClassesLancamento.Lancamento.TABLE_NAME, valores, where, null);
    }
    public void deleteThat(Context context, int id) {
        // Obtem a referencia do BD em modo escrita
        LancamentoSqlHelper lancamentoSqlHelper = new LancamentoSqlHelper(context);
        SQLiteDatabase db = lancamentoSqlHelper.getWritableDatabase();

        String where = ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO + "=" + id;

        //Exclui elementos do BD
        db.delete(ClassesLancamento.Lancamento.TABLE_NAME, where, null);
    }
    //faz busca de um lancamento especifico no banco, nao implementado no trabalho de gerenciador financeiro
    public Lancamento getById(Context context, Lancamento lancamento) {
        // Obtem a referencia do BD em modo leitura
        LancamentoSqlHelper lancamentoSqlHelper = new LancamentoSqlHelper(context);
        SQLiteDatabase db = lancamentoSqlHelper.getReadableDatabase();

        // Define quais colunas devem ser lidas do BD.
        String[] projection = {
                ClassesLancamento.Lancamento._ID,
                ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO,
                ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO,
                ClassesLancamento.Lancamento.COLUMN_NAME_DATA,
                ClassesLancamento.Lancamento.COLUMN_NAME_VALOR,
                ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS,
                ClassesLancamento.Lancamento.COLUMN_NAME_TIPO,
                ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA
        };

        Lancamento lancamentoBd = null;
        //Filtra pelo ID, usando a clausula WHERE
        String selection = ClassesLancamento.Lancamento._ID + " = ?";
        String[] selectionArgs = { String.valueOf(lancamento.getCodigo())};
        // Ordenação da consulta
        String sortOrder = ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO + " ASC";

        //Montagem da Consulta
        Cursor cursorLancamento = db.query(
                ClassesLancamento.Lancamento.TABLE_NAME,         // Tabela
                projection,                               // Colunas a serem lidas
                selection,                                // Clausula Where
                selectionArgs,                            // Valores da clausula where
                null,                                     // Definição de grupos
                null,                                     // Definição de filtros de grupo
                sortOrder                                 // Ordenação
        );

        if (cursorLancamento.moveToFirst()) {
            int codigo = cursorLancamento.getInt(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_CODIGO));
            String descricao = cursorLancamento.getString(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DESCRICAO));
            Date data = new Date(cursorLancamento.getLong(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_DATA)));
            Float valor = cursorLancamento.getFloat(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_VALOR));
            int parcelas = cursorLancamento.getInt(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_PARCELAS));
            String tipo = cursorLancamento.getString(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_TIPO));
            String categoria = cursorLancamento.getString(cursorLancamento.getColumnIndexOrThrow(ClassesLancamento.Lancamento.COLUMN_NAME_CATEGORIA));
            lancamentoBd = new Lancamento(codigo,descricao,data,valor,parcelas,tipo,categoria);
        }
        cursorLancamento.close();
        return lancamentoBd;
    }
    public String gambiarraData(String banco) {
        String frase, aux = "";
        String[] array = banco.split(" ");

        if(array[1].equals("Jan"))
            aux = "01";
        if(array[1].equals("Feb"))
            aux = "02";
        if(array[1].equals("Mar"))
            aux = "03";
        if(array[1].equals("Apr"))
            aux = "04";
        if(array[1].equals("May"))
            aux = "05";
        if(array[1].equals("Jun"))
            aux = "06";
        if(array[1].equals("Jul"))
            aux = "07";
        if(array[1].equals("Aug"))
            aux = "08";
        if(array[1].equals("Sep"))
            aux = "09";
        if(array[1].equals("Oct"))
            aux = "10";
        if(array[1].equals("Nov"))
            aux = "11";
        if(array[1].equals("Dec"))
            aux = "12";
        frase = array[2] + "/" + aux + "/" + array[5];

        return frase;
    }
}