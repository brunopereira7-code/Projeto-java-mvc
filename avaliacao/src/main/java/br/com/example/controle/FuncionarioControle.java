package controller;

import dao.FuncionarioDAO;
import model.Funcionario;
import model.Gerente;
import model.Operador;

import java.sql.SQLException;
import java.util.List;


public class FuncionarioController {

    private final FuncionarioDAO dao;

    public FuncionarioController() {
        this.dao = new FuncionarioDAO();
    }

    // ---------------------------------------------------------------
    // RF01 - Cadastrar gerente ou operador
    // ---------------------------------------------------------------
    public String cadastrarGerente(String matricula, String nome, String idade, String departamento) {
        int mat = validarMatricula(matricula);
        String nomeValido = validarNome(nome);
        int idadeValida = validarIdade(idade);
        String depto = validarTextoObrigatorio(departamento,
                "O departamento é obrigatório para gerentes.");

        return cadastrar(new Gerente(mat, nomeValido, idadeValida, depto));
    }

    public String cadastrarOperador(String matricula, String nome, String idade, String funcao) {
        int mat = validarMatricula(matricula);
        String nomeValido = validarNome(nome);
        int idadeValida = validarIdade(idade);
        String func = validarTextoObrigatorio(funcao,
                "A função é obrigatória para operadores.");

        return cadastrar(new Operador(mat, nomeValido, idadeValida, func));
    }

    private String cadastrar(Funcionario funcionario) {
        try {
            // Regra de negócio: matrícula única
            if (dao.existe(funcionario.getMatricula())) {
                throw new IllegalArgumentException(
                        "Já existe um funcionário com a matrícula " + funcionario.getMatricula() + ".");
            }
            dao.inserir(funcionario);
            return "Funcionário cadastrado com sucesso!";
        } catch (SQLException e) {
            throw erroBanco("cadastrar", e);
        }
    }

    // ---------------------------------------------------------------
    // RF02 - Listar todos os funcionários
    // ---------------------------------------------------------------
    public List<Funcionario> listar() {
        try {
            return dao.listar();
        } catch (SQLException e) {
            throw erroBanco("listar", e);
        }
    }

    // ---------------------------------------------------------------
    // RF03 - Pesquisar funcionário pela matrícula
    // ---------------------------------------------------------------
    public Funcionario pesquisar(String matricula) {
        int mat = validarMatricula(matricula);
        try {
            Funcionario f = dao.buscarPorMatricula(mat);
            if (f == null) {
                throw new IllegalArgumentException("Nenhum funcionário encontrado com a matrícula " + mat + ".");
            }
            return f;
        } catch (SQLException e) {
            throw erroBanco("pesquisar", e);
        }
    }

    // ---------------------------------------------------------------
    // RF04 - Alterar dados de funcionário existente
    // O tipo (gerente/operador) é mantido; "complemento" é o departamento
    // (se for gerente) ou a função (se for operador).
    // ---------------------------------------------------------------
    public String alterar(String matricula, String nome, String idade, String complemento) {
        int mat = validarMatricula(matricula);
        String nomeValido = validarNome(nome);
        int idadeValida = validarIdade(idade);

        try {
            // Regra de negócio: verificar existência antes de alterar
            Funcionario atual = dao.buscarPorMatricula(mat);
            if (atual == null) {
                throw new IllegalArgumentException("Matrícula " + mat + " não encontrada. Nada foi alterado.");
            }

            Funcionario atualizado;
            if (atual instanceof Gerente) {
                String depto = validarTextoObrigatorio(complemento,
                        "O departamento é obrigatório para gerentes.");
                atualizado = new Gerente(mat, nomeValido, idadeValida, depto);
            } else {
                String func = validarTextoObrigatorio(complemento,
                        "A função é obrigatória para operadores.");
                atualizado = new Operador(mat, nomeValido, idadeValida, func);
            }

            dao.atualizar(atualizado);
            return "Funcionário alterado com sucesso!";
        } catch (SQLException e) {
            throw erroBanco("alterar", e);
        }
    }

    // ---------------------------------------------------------------
    // RF05 - Excluir funcionário pela matrícula
    // A confirmação ("Deseja realmente excluir?") é feita na View,
    // antes de chamar este método.
    // ---------------------------------------------------------------
    public String excluir(String matricula) {
        int mat = validarMatricula(matricula);
        try {
            // Regra de negócio: verificar existência antes de excluir
            if (!dao.existe(mat)) {
                throw new IllegalArgumentException("Matrícula " + mat + " não encontrada. Nada foi excluído.");
            }
            dao.excluir(mat);
            return "Funcionário excluído com sucesso!";
        } catch (SQLException e) {
            throw erroBanco("excluir", e);
        }
    }

    // ---------------------------------------------------------------
    // RF06 - Exibir atributos específicos de gerente e operador
    // ---------------------------------------------------------------
    public String detalhar(Funcionario f) {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrícula: ").append(f.getMatricula()).append("\n");
        sb.append("Nome: ").append(f.getNome()).append("\n");
        sb.append("Idade: ").append(f.getIdade()).append("\n");

        if (f instanceof Gerente) {
            sb.append("Cargo: Gerente\n");
            sb.append("Departamento: ").append(((Gerente) f).getDepartamento());
        } else if (f instanceof Operador) {
            sb.append("Cargo: Operador\n");
            sb.append("Função: ").append(((Operador) f).getFuncao());
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------
    // Validações (regras de negócio)
    // ---------------------------------------------------------------
    private int validarMatricula(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("A matrícula é obrigatória.");
        }
        try {
            int matricula = Integer.parseInt(texto.trim());
            if (matricula <= 0) {
                throw new IllegalArgumentException("A matrícula deve ser um número inteiro maior que zero.");
            }
            return matricula;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A matrícula deve ser um número inteiro.");
        }
    }

    private String validarNome(String nome) {
        return validarTextoObrigatorio(nome, "O nome é obrigatório.");
    }

    private int validarIdade(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("A idade é obrigatória.");
        }
        try {
            int idade = Integer.parseInt(texto.trim());
            if (idade <= 0) {
                throw new IllegalArgumentException("A idade deve ser um inteiro maior que zero.");
            }
            return idade;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A idade deve ser um número inteiro.");
        }
    }

    private String validarTextoObrigatorio(String texto, String mensagemErro) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensagemErro);
        }
        return texto.trim();
    }

    private IllegalStateException erroBanco(String operacao, SQLException e) {
        return new IllegalStateException(
                "Erro de banco de dados ao " + operacao + ": " + e.getMessage(), e);
    }
}