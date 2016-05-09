package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Veiculo {

    int id;
    String marca;
    String modelo;
    String placa;
    String peso;
    String ano;
    int idTipo;
    double km;
    byte[] img;
    List<Pneu> pneus;
    List<Pneu> excludedPneus;
    List<Pneu> includedPneus;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public List<Pneu> getPneus() {
        if (pneus == null) {
            pneus = new ArrayList<Pneu>();
        }
        return pneus;
    }

    public void setPneus(List<Pneu> pneus) {
        this.pneus = pneus;
    }

    public List<Pneu> getExcludedPneus() {
        if (excludedPneus == null) {
            excludedPneus = new ArrayList<Pneu>();
        }
        return excludedPneus;
    }

    public void setExcludedPneus(List<Pneu> excludedPneus) {
        this.excludedPneus = excludedPneus;
    }

    public List<Pneu> getIncludedPneus() {
        if (includedPneus == null) {
            includedPneus = new ArrayList<Pneu>();
        }
        return includedPneus;
    }

    public void setIncludedPneus(List<Pneu> includedPneus) {
        this.includedPneus = includedPneus;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

	@Override
	public String toString() {
		return "Veiculo [id=" + id + ", marca=" + marca + ", modelo=" + modelo
				+ ", placa=" + placa + ", peso=" + peso + ", ano=" + ano
				+ ", idTipo=" + idTipo + ", km=" + km + ", img="
				+ Arrays.toString(img) + ", pneus=" + pneus
				+ ", excludedPneus=" + excludedPneus + ", includedPneus="
				+ includedPneus + "]";
	}

    
}
