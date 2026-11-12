package br.com.example.modelo;

public abstract class Funcionario {

    private int matricula;
    private String nome;
    private int idade;

    public Funcionario() {
    }

    public Funcionario(int matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    public abstract String getTipo();

    public abstract String getNomeInformacao();

    public abstract String getInformacao();

    public abstract void setInformacao(String informacao);

    public abstract String getDepartamento();

    public abstract String getFuncao();

    public abstract boolean validar();

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public static Funcionario criarFuncionario(
            int matricula,
            String nome,
            int idade,
            String tipo,
            String departamento,
            String funcao) {

        if (tipo.equals("Gerente")) {
            return new Gerente(matricula, nome, idade, departamento);
        }

        if (tipo.equals("Operador")) {
            return new Operador(matricula, nome, idade, funcao);
        }

        return null;
    }
}