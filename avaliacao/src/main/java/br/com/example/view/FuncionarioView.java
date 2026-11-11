package br.com.example.view;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import br.com.example.controle.FuncionarioControle;
import br.com.example.modelo.Funcionario;
import br.com.example.modelo.Gerente;
import br.com.example.modelo.Operador;

public class FuncionarioView {

    private FuncionarioControle controle;

    public FuncionarioView() {
        controle = new FuncionarioControle();
    }

    public void menu() {
        int opcao;
        do {
            String menu = """
                    ===== SISTEMA DE FUNCIONÁRIOS =====

                    1. Cadastrar Gerente
                    2. Cadastrar Operador
                    3. Listar Funcionários
                    4. Alterar Funcionário
                    5. Remover Funcionário
                    6. Sair

                    Escolha uma opção:
                    """;

            String entrada = JOptionPane.showInputDialog(menu);
            if (entrada == null) break;

            try {
                opcao = Integer.parseInt(entrada);
                switch (opcao) {
                    case 1: cadastrar(true); break;
                    case 2: cadastrar(false); break;
                    case 3: listar(); break;
                    case 4: alterar(); break;
                    case 5: remover(); break;
                    case 6: JOptionPane.showMessageDialog(null, "Sistema encerrado!"); break;
                    default: JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite uma opção válida!");
                opcao = 0;
            }
        } while (opcao != 6);
    }

    public void cadastrar(boolean isGerente) {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a matrícula:"));
            String nome = JOptionPane.showInputDialog("Digite o nome:");
            if (nome == null || nome.trim().isEmpty()) return;
            
            int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite a idade:"));
            if (idade <= 0) {
                JOptionPane.showMessageDialog(null, "Idade deve ser maior que zero!");
                return;
            }

            String mensagem;
            if (isGerente) {
                String depto = JOptionPane.showInputDialog("Digite o departamento:");
                mensagem = controle.cadastrarGerente(matricula, nome, idade, depto);
            } else {
                String funcao = JOptionPane.showInputDialog("Digite a função:");
                mensagem = controle.cadastrarOperador(matricula, nome, idade, funcao);
            }
            JOptionPane.showMessageDialog(null, mensagem);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos para matrícula e idade!");
        }
    }

    public void listar() {
        ArrayList<Funcionario> funcionarios = controle.listar();
        if (funcionarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado!");
            return;
        }

        StringBuilder mensagem = new StringBuilder("===== FUNCIONÁRIOS CADASTRADOS =====\n\n");
        for (Funcionario f : funcionarios) {
            mensagem.append("Matrícula: ").append(f.getMatricula()).append("\n");
            mensagem.append("Nome: ").append(f.getNome()).append("\n");
            mensagem.append("Idade: ").append(f.getIdade()).append("\n");
            mensagem.append("Específico: ").append(f.getDadosEspecificos()).append("\n");
            mensagem.append("------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, mensagem.toString());
    }

    public void alterar() {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Matrícula do funcionário a alterar:"));
            Funcionario f = controle.buscar(matricula);

            if (f == null) {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                return;
            }

            String nome = JOptionPane.showInputDialog("Novo nome:", f.getNome());
            int idade = Integer.parseInt(JOptionPane.showInputDialog("Nova idade:", f.getIdade()));

            f.setNome(nome);
            f.setIdade(idade);

            if (f instanceof Gerente) {
                String depto = JOptionPane.showInputDialog("Novo departamento:", ((Gerente) f).getDepartamento());
                ((Gerente) f).setDepartamento(depto);
            } else if (f instanceof Operador) {
                String funcao = JOptionPane.showInputDialog("Nova função:", ((Operador) f).getFuncao());
                ((Operador) f).setFuncao(funcao);
            }

            JOptionPane.showMessageDialog(null, controle.alterar(f));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos!");
        }
    }

    public void remover() {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a matrícula para remover:"));
            Funcionario f = controle.buscar(matricula);

            if (f == null) {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(null, 
                "Deseja remover " + f.getNome() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                if (controle.remover(matricula)) {
                    JOptionPane.showMessageDialog(null, "Removido com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao remover!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite uma matrícula válida!");
        }
    }
}