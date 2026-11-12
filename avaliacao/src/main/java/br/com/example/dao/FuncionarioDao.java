package br.com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import br.com.example.conexao.Conexao;
import br.com.example.modelo.Funcionario;

public class FuncionarioDAO {

    public boolean cadastrar(Funcionario funcionario) {

        String sql = "INSERT INTO funcionarios "
                + "(matricula, nome, idade, tipo, departamento, funcao) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, funcionario.getMatricula());
            comando.setString(2, funcionario.getNome());
            comando.setInt(3, funcionario.getIdade());
            comando.setString(4, funcionario.getTipo());

            if (funcionario.getDepartamento() == null) {
                comando.setNull(5, Types.VARCHAR);
            } else {
                comando.setString(5, funcionario.getDepartamento());
            }

            if (funcionario.getFuncao() == null) {
                comando.setNull(6, Types.VARCHAR);
            } else {
                comando.setString(6, funcionario.getFuncao());
            }

            comando.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Funcionario> listar() {

        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT * FROM funcionarios ORDER BY matricula";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                Funcionario funcionario = Funcionario.criarFuncionario(
                        resultado.getInt("matricula"),
                        resultado.getString("nome"),
                        resultado.getInt("idade"),
                        resultado.getString("tipo"),
                        resultado.getString("departamento"),
                        resultado.getString("funcao")
                );

                if (funcionario != null) {
                    funcionarios.add(funcionario);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return funcionarios;
    }

    public Funcionario buscar(int matricula) {

        String sql = "SELECT * FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    return Funcionario.criarFuncionario(
                            resultado.getInt("matricula"),
                            resultado.getString("nome"),
                            resultado.getInt("idade"),
                            resultado.getString("tipo"),
                            resultado.getString("departamento"),
                            resultado.getString("funcao")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }

        return null;
    }

    public boolean alterar(Funcionario funcionario) {

        String sql = "UPDATE funcionarios SET nome = ?, idade = ?, "
                + "tipo = ?, departamento = ?, funcao = ? "
                + "WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, funcionario.getNome());
            comando.setInt(2, funcionario.getIdade());
            comando.setString(3, funcionario.getTipo());

            if (funcionario.getDepartamento() == null) {
                comando.setNull(4, Types.VARCHAR);
            } else {
                comando.setString(4, funcionario.getDepartamento());
            }

            if (funcionario.getFuncao() == null) {
                comando.setNull(5, Types.VARCHAR);
            } else {
                comando.setString(5, funcionario.getFuncao());
            }

            comando.setInt(6, funcionario.getMatricula());

            return comando.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(int matricula) {

        String sql = "DELETE FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);

            return comando.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
            return false;
        }
    }
}