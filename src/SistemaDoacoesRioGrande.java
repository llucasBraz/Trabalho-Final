import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Enum para representar os tipos de doação
enum TipoDoacao {
    ROUPAS,
    ALIMENTOS,
    PRODUTOS
}

// Classe para representar uma doação
class Doacao {
    private String nome;
    private TipoDoacao tipo;
    private int quantidade;
    private Date data;

    public Doacao(String nome, TipoDoacao tipo, int quantidade, Date data) {
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public TipoDoacao getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Date getData() {
        return data;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Nome: %s | Item doado: %s | Quantidade: %d | Data: %s",
                nome, tipo, quantidade, formatadorData.format(data));
    }
}

// Classe principal que gerencia o sistema de doações
public class SistemaDoacoesRioGrande {
    private static final String ARQUIVO_DOACOES = "doacoes.txt";
    private static SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

    private static void armazenarDoacao(Doacao doacao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DOACOES, true))) {
            String linha = String.format("%s | %s | %d | %s\n",
                    doacao.getNome(), doacao.getTipo(), doacao.getQuantidade(), formatadorData.format(doacao.getData()));
            writer.write(linha);
            System.out.println("Doação registrada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao armazenar a doação.");
        }
    }

    private static void visualizarTodasDoacoes() {
        System.out.println("\n=== Todas as doações realizadas ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DOACOES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler as doações.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Doações para o Rio Grande do Sul ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Registrar doação");
            System.out.println("2 - Visualizar todas as doações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();

                    // Exibir opções de tipo de doação
                    System.out.println("Selecione o tipo de doação: ");
                    System.out.println("1 - Roupas");
                    System.out.println("2 - Alimentos");
                    System.out.println("3 - Produtos");
                    System.out.print("Opção: ");
                    int opcaoTipo = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    TipoDoacao tipoDoacao;
                    switch (opcaoTipo) {
                        case 1:
                            tipoDoacao = TipoDoacao.ROUPAS;
                            break;
                        case 2:
                            tipoDoacao = TipoDoacao.ALIMENTOS;
                            break;
                        case 3:
                            tipoDoacao = TipoDoacao.PRODUTOS;
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            continue;
                    }

                    // Solicitar quantidade de itens doados
                    System.out.print("Digite a quantidade doada: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    // Obter a data atual
                    Date dataAtual = Calendar.getInstance().getTime();

                    // Criar objeto de doação com as informações coletadas
                    Doacao novaDoacao = new Doacao(nome, tipoDoacao, quantidade, dataAtual);

                    // Armazenar a doação no arquivo de texto
                    armazenarDoacao(novaDoacao);
                    break;
                case 2:
                    // Visualizar todas as doações registradas
                    visualizarTodasDoacoes();
                    break;
                case 0:
                    System.out.println("Encerrando programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}

