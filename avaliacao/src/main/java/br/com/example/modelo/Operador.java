package br.com.example.modelo;

public class Operador extends Funcionario {

    private String funcao;

    public Operador() {
    }

    public Operador(int matricula, String nome, int idade, String funcao) {
        super(matricula, nome, idade);
        this.funcao = funcao;
    }

    @Override
    public String getTipo() {
        return "Operador";
    }

    @Override
    public String getNomeInformacao() {
        return "Função";
    }

    @Override
    public String getInformacao() {
        return funcao;
    }

    @Override
    public void setInformacao(String informacao) {
        this.funcao = informacao;
    }

    @Override
    public String getDepartamento() {
        return null;
    }

    @Override
    public String getFuncao() {
        return funcao;
    }

    @Override
    public boolean validar() {
        return funcao != null && !funcao.trim().isEmpty();
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}