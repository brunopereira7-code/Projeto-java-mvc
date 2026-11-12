package br.com.example.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.example.controle.FuncionarioController;
import br.com.example.modelo.Funcionario;
import br.com.example.modelo.Gerente;
import br.com.example.modelo.Operador;

public class FuncionarioView {

    private FuncionarioController controle;

    public FuncionarioView() {
        controle = new FuncionarioController();
    }

    public void menu() {

        int opcao;

        do {

            String menu = """
                    ===== SISTEMA DE GERENCIAMENTO DE FUNCIONÁRIOS =====

                    1. Cadastrar gerente
                    2. Cadastrar operador
                    3. Listar funcionários
                    4. Pesquisar funcionário
                    5. Alterar funcionário
                    6. Excluir funcionário
                    7. Sair

                    Escolha uma opção:
                    """;

            String entrada = JOptionPane.showInputDialog(menu);

            if (entrada == null) {
                break;
            }

            try {

                opcao = Integer.parseInt(entrada);

                switch (opcao) {

                    case 1:
                        cadastrarGerente();
                        break;

                    case 2:
                        cadastrarOperador();
                        break;

                    case 3:
                        listar();
                        break;

                    case 4:
                        pesquisar();
                        break;

                    case 5:
                        alterar();
                        break;

                    case 6:
                        remover();
                        break;

                    case 7:
                        JOptionPane.showMessageDialog(null, "Sistema encerrado!");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(null, "Digite uma opção válida!");
                opcao = 0;
            }

        } while (opcao != 7);
    }

    public void cadastrarGerente() {

        try {

            String entradaMatricula = JOptionPane.showInputDialog("Digite a matrícula:");

            if (entradaMatricula == null) return;

            String nome = JOptionPane.showInputDialog("Digite o nome:");

            if (nome == null) return;

            String entradaIdade = JOptionPane.showInputDialog("Digite a idade:");

            if (entradaIdade == null) return;

            String departamento = JOptionPane.showInputDialog("Digite o departamento:");

            if (departamento == null) return;

            int matricula = Integer.parseInt(entradaMatricula.trim());
            int idade = Integer.parseInt(entradaIdade.trim());

            if (controle.cadastrarGerente(matricula, nome, idade, departamento)) {

                JOptionPane.showMessageDialog(null, "Gerente cadastrado com sucesso!");

            } else {

                JOptionPane.showMessageDialog(null, "Erro ao cadastrar gerente! Verifique os dados e a matrícula.");
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos!");
        }
    }

    public void cadastrarOperador() {

        try {

            String entradaMatricula = JOptionPane.showInputDialog("Digite a matrícula:");

            if (entradaMatricula == null) return;

            String nome = JOptionPane.showInputDialog("Digite o nome:");

            if (nome == null) return;

            String entradaIdade = JOptionPane.showInputDialog("Digite a idade:");

            if (entradaIdade == null) return;

            String funcao = JOptionPane.showInputDialog("Digite a função:");

            if (funcao == null) return;

            int matricula = Integer.parseInt(entradaMatricula.trim());
            int idade = Integer.parseInt(entradaIdade.trim());

            if (controle.cadastrarOperador(matricula, nome, idade, funcao)) {

                JOptionPane.showMessageDialog(null, "Operador cadastrado com sucesso!");

            } else {

                JOptionPane.showMessageDialog(null, "Erro ao cadastrar operador! Verifique os dados e a matrícula.");
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos!");
        }
    }

    public void listar() {

        ArrayList<Funcionario> funcionarios = controle.listar();

        if (funcionarios.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado!");
            return;
        }

        StringBuilder mensagem = new StringBuilder();

        mensagem.append("===== FUNCIONÁRIOS CADASTRADOS =====\n\n");

        for (Funcionario funcionario : funcionarios) {

            mensagem.append("Matrícula: ").append(funcionario.getMatricula()).append("\n");
            mensagem.append("Nome: ").append(funcionario.getNome()).append("\n");
            mensagem.append("Idade: ").append(funcionario.getIdade()).append("\n");
            mensagem.append("Tipo: ").append(funcionario.getTipo()).append("\n");
            mensagem.append(funcionario.getNomeInformacao()).append(": ")
                    .append(funcionario.getInformacao()).append("\n");

            mensagem.append("------------------------------------\n");
        }

        JOptionPane.showMessageDialog(null, mensagem.toString());
    }

    public void pesquisar() {

        try {

            String entrada = JOptionPane.showInputDialog("Digite a matrícula do funcionário:");

            if (entrada == null) return;

            int matricula = Integer.parseInt(entrada.trim());

            Funcionario funcionario = controle.buscar(matricula);

            if (funcionario == null) {

                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                return;
            }

            String mensagem = "Matrícula: " + funcionario.getMatricula()
                    + "\nNome: " + funcionario.getNome()
                    + "\nIdade: " + funcionario.getIdade()
                    + "\nTipo: " + funcionario.getTipo()
                    + "\n" + funcionario.getNomeInformacao()
                    + ": " + funcionario.getInformacao();

            JOptionPane.showMessageDialog(null, mensagem);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Digite uma matrícula numérica válida!");
        }
    }

    public void alterar() {

        try {

            String entrada = JOptionPane.showInputDialog("Digite a matrícula do funcionário:");

            if (entrada == null) return;

            int matricula = Integer.parseInt(entrada.trim());

            Funcionario funcionario = controle.buscar(matricula);

            if (funcionario == null) {

                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                return;
            }

            String nome = JOptionPane.showInputDialog(
                    null, "Digite o novo nome:", funcionario.getNome()
            );

            if (nome == null) return;

            String entradaIdade = JOptionPane.showInputDialog(
                    null, "Digite a nova idade:", funcionario.getIdade()
            );

            if (entradaIdade == null) return;

            String informacao = JOptionPane.showInputDialog(
                    null,
                    "Digite o novo " + funcionario.getNomeInformacao().toLowerCase() + ":",
                    funcionario.getInformacao()
            );

            if (informacao == null) return;

            funcionario.setNome(nome.trim());

            funcionario.setIdade(Integer.parseInt(entradaIdade.trim()));

            funcionario.setInformacao(informacao.trim());

            if (controle.alterar(funcionario)) {

                JOptionPane.showMessageDialog(null, "Funcionário alterado com sucesso!");

            } else {

                JOptionPane.showMessageDialog(null, "Erro ao alterar funcionário! Verifique os dados.");
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos!");
        }
    }

    public void remover() {

        try {

            String entrada = JOptionPane.showInputDialog("Digite a matrícula do funcionário:");

            if (entrada == null) return;

            int matricula = Integer.parseInt(entrada.trim());

            Funcionario funcionario = controle.buscar(matricula);

            if (funcionario == null) {

                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                return;
            }

            String mensagem = "Deseja realmente excluir este funcionário?\n\n"
                    + "Matrícula: " + funcionario.getMatricula()
                    + "\nNome: " + funcionario.getNome()
                    + "\nIdade: " + funcionario.getIdade()
                    + "\nTipo: " + funcionario.getTipo();

            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    mensagem,
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {

                if (controle.remover(matricula)) {

                    JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");

                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao excluir funcionário!");
                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Digite uma matrícula numérica válida!");
        }
    }
}