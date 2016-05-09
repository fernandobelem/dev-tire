package model;

public class Pneu {

    int id;
    String estado;
    String posicao;
    String codigo;
    String posicaoGrafica;
    int idVeiculo;
    String eixo;

    public String getEixo() {
        return eixo;
    }

    public void setEixo(String eixo) {
        this.eixo = eixo;
    }

    public Pneu() {
    }

    public Pneu(String codigo, String posicao, String estado) {
        this.codigo = codigo;
        this.posicao = posicao;
        this.estado = estado;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPosicaoGrafica() {
        return posicaoGrafica;
    }

    public void setPosicaoGrafica(String posicaoGrafica) {
        this.posicaoGrafica = posicaoGrafica;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

	@Override
	public String toString() {
		return "Pneu > id= " + id + ", codigo= " + codigo + ", idVeiculo= " + idVeiculo;
	}
    

}
