package com.example.matheus.meugerenciadorfinanceiro;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aluno on 22/09/2016.
 */
public class Lancamento implements Parcelable{


    public static ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>();
    private int codigo;
    private String descricao;
    private int data;
    private float valor;
    private String situacao;

    public static final Creator <Lancamento> CREATOR = new Creator<Lancamento>() {
        @Override
        public Lancamento createFromParcel(Parcel parcel) {
            return new Lancamento(parcel);
        }
        @Override
        public Lancamento[] newArray(int i) {
            return new Lancamento[i];
        }
    };
    public Lancamento (int controle, String descricao, int data, float valor , String situacao) {
        this.codigo = controle;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.situacao = situacao;
    }
    private Lancamento(Parcel parcel) {
        this.codigo = parcel.readInt();
        this.descricao = parcel.readString();
        this.data = parcel.readInt();
        this.valor = parcel.readFloat();
        this.situacao = parcel.readString();

    }
    public int describeContents() {return 0;}
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeInt(this.codigo);
        dest.writeString(this.descricao);
        dest.writeInt(this.data);
        dest.writeFloat(this.valor);
        dest.writeString(this.situacao);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
