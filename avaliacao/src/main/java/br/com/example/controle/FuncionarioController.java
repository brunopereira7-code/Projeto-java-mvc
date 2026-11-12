package br.com.example.controle;

import java.util.ArrayList;

import br.com.example.dao.FuncionarioDAO;
import br.com.example.modelo.Funcionario;
import br.com.example.modelo.Gerente;
import br.com.example.modelo.Operador;

public class FuncionarioController {

    private FuncionarioDAO dao;

    public FuncionarioController() {
        dao = new FuncionarioDAO();
    }

    public boolean cadastrarGerente(int matricula, String nome, int idade, String departamento) {

        if (matricula <= 0 || nome == null || nome.trim().isEmpty()
                || idade <= 0 || departamento == null
                || departamento.trim().isEmpty()) {
            return false;
        }

        Gerente gerente = new Gerente(
                matricula, nome.trim(), idade, departamento.trim()
        );

        return dao.cadastrar(gerente);
    }

    public boolean cadastrarOperador(int matricula, String nome, int idade, String funcao) {

        if (matricula <= 0 || nome == null || nome.trim().isEmpty()
                || idade <= 0 || funcao == null
                || funcao.trim().isEmpty()) {
            return false;
        }

        Operador operador = new Operador(
                matricula, nome.trim(), idade, funcao.trim()
        );

        return dao.cadastrar(operador);
    }

    public ArrayList<Funcionario> listar() {
        return dao.listar();
    }

    public Funcionario buscar(int matricula) {
        return dao.buscar(matricula);
    }

    public boolean alterar(Funcionario funcionario) {

        if (funcionario == null
                || funcionario.getMatricula() <= 0
                || funcionario.getNome() == null
                || funcionario.getNome().trim().isEmpty()
                || funcionario.getIdade() <= 0
                || !funcionario.validar()) {
            return false;
        }

        return dao.alterar(funcionario);
    }

    public boolean remover(int matricula) {
        return dao.remover(matricula);
    }
}