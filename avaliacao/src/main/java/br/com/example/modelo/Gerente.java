package br.com.example.modelo;

public class Gerente extends Funcionario{
    
    private String departamento;

    public Gerente(){}

    public Gerente(String departamento) {
        this.departamento = departamento;
    }

    public Gerente(int matricula, String nome, int idade, String departamento) {
        super(matricula, nome, idade);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
