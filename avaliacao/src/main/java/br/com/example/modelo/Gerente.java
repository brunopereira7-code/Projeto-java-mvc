package br.com.example.modelo;

public class Gerente extends Funcionario {

    private String departamento;

    public Gerente() {
    }

    public Gerente(int matricula, String nome, int idade, String departamento) {
        super(matricula, nome, idade);
        this.departamento = departamento;
    }

    @Override
    public String getTipo() {
        return "Gerente";
    }

    @Override
    public String getNomeInformacao() {
        return "Departamento";
    }

    @Override
    public String getInformacao() {
        return departamento;
    }

    @Override
    public void setInformacao(String informacao) {
        this.departamento = informacao;
    }

    @Override
    public String getDepartamento() {
        return departamento;
    }

    @Override
    public String getFuncao() {
        return null;
    }

    @Override
    public boolean validar() {
        return departamento != null && !departamento.trim().isEmpty();
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}