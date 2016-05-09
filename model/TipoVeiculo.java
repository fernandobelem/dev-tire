/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;

/**
 *
 * @author Fernando
 */
public class TipoVeiculo {

    private int id;
    private String nome;
    private int numeroEixos;
    private int rodasPorEixo;
    private byte[] img;

    public TipoVeiculo(String nome) {
        this.nome = nome;
    }
    
    public TipoVeiculo() {
    }
   
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroEixos() {
        return numeroEixos;
    }

    public void setNumeroEixos(int numeroEixos) {
        this.numeroEixos = numeroEixos;
    }

    public int getRodasPorEixo() {
        return rodasPorEixo;
    }

    public void setRodasPorEixo(int rodasPorEixo) {
        this.rodasPorEixo = rodasPorEixo;
    }

	@Override
	public String toString() {
		return "TipoVeiculo [id=" + id + ", nome=" + nome + ", numeroEixos="
				+ numeroEixos + ", rodasPorEixo=" + rodasPorEixo + ", img="
				+ Arrays.toString(img) + "]";
	}

}
