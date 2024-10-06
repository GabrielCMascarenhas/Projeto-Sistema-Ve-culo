import java.util.Scanner;
import java.util.Comparator;
import java.util.List;

public class ViewCadVeiculo {
    private static ServiceVeiculo service = new ServiceVeiculo();
    static Scanner input = new Scanner(System.in);

    public static void limparTela() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void aguardarEnter() {
        System.out.print("Pressione Enter para continuar...");
        input.nextLine();
    }

    private static int inputNumerico(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        System.out.print(mensagem);
        do {
            String valorStr = input.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (Exception e) {
                System.out.println("ERRO. Valor informado deve ser um número Inteiro");
            }
        } while (!entradaValida);
        return valor;
    }

    private static void pesquisarPelaPlaca() {
        limparTela();
        System.out.println("==== Pesquisar Veiculo por Placa ====");
        System.out.print("Informe a placa do veículo a ser pesquisado: ");
        String placa = input.nextLine();

        List<Veiculo> veiculos = service.pesquisarTodos();
        boolean veiculoEncontrado = false;

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoEncontrado = true;
                System.out.println("\nVeículo encontrado:");
                System.out.println("Placa: " + veiculo.getPlaca());
                System.out.println("Marca: " + veiculo.getMarca());
                System.out.println("Modelo: " + veiculo.getModelo());
                System.out.println("Ano: " + veiculo.getAno());

                if (veiculo instanceof Carro) {
                    System.out.println("Número de Portas: " + ((Carro) veiculo).getNumeroPortas());
                } else if (veiculo instanceof Moto) {
                    System.out.println("Partida Elétrica: " + (((Moto) veiculo).isPartidaEletrica() ? "Sim" : "Não"));
                }
                break;
            }
        }

        if (!veiculoEncontrado) {
            System.out.println("Veículo não encontrado com a placa: " + placa);
        }

        aguardarEnter();
    }

    private static void cadastrar() {
        limparTela();
        Veiculo novoVeiculo = new Veiculo();
        System.out.println("==== Cadastrar Veiculo ====");
        int tipo = inputNumerico("""
                Qual o tipo de veículo: (1) Carro - (2) Moto
                """);
        System.out.print("Digite a marca: ");
        String marca = input.nextLine();
        System.out.print("Digite a modelo: ");
        String modelo = input.nextLine();
        System.out.print("Digite a placa: ");
        String placa = input.nextLine();
        int ano = inputNumerico("Digite o Ano: ");

        if (tipo == 1) {
            Carro carro = new Carro();
            carro.setMarca(marca);
            carro.setModelo(modelo);
            carro.setPlaca(placa);
            carro.setAno(ano);

            int numeroPortas;
            do {
                numeroPortas = inputNumerico("Digite o número de portas (1 a 4): ");
                if (numeroPortas < 1 || numeroPortas > 4) {
                    System.out.println("\nNúmero de portas deve estar entre 1 e 4");
                }
            } while (numeroPortas < 1 || numeroPortas > 4);

            carro.setNumeroPortas(numeroPortas);
            novoVeiculo = carro;

        } else if (tipo == 2) {
            Moto moto = new Moto();
            moto.setMarca(marca);
            moto.setModelo(modelo);
            moto.setPlaca(placa);
            moto.setAno(ano);

            boolean partidaValida = false;
            while (!partidaValida) {
                System.out.println("""
                        É partida elétrica? (1) Sim - (2) Não""");
                String partida = input.nextLine().toLowerCase();

                if (partida.equals("1")) {
                    moto.setPartidaEletrica(true);
                    partidaValida = true;
                } else if (partida.equals("2")) {
                    moto.setPartidaEletrica(false);
                    partidaValida = true;
                } else {
                    System.out.println("\nEntrada inválida, digite apenas (1) ou (2)");
                }
            }
            novoVeiculo = moto;
        }

        try {
            service.cadastrar(novoVeiculo);
            System.out.println("VEÍCULO CADASTRADO COM SUCESSO!\n");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        aguardarEnter();
    }

    private static void listar() {
        limparTela();
        List<Veiculo> veiculos = service.pesquisarTodos();
        veiculos.sort(Comparator.comparing(Veiculo::getPlaca));
        System.out.println("============ LISTA DE VEÍCULOS ===========");
        for (Veiculo veiculo : veiculos) {
            System.out.println("Placa: " + veiculo.getPlaca());
            System.out.println("Marca: " + veiculo.getMarca());
            System.out.println("Modelo: " + veiculo.getModelo());
            System.out.println("Ano: " + veiculo.getAno());
            if (veiculo instanceof Carro) {
                System.out.println("Número de Portas: " + ((Carro) veiculo).getNumeroPortas());
            } else if (veiculo instanceof Moto) {
                System.out.println("Partida Elétrica: " + (((Moto) veiculo).isPartidaEletrica() ? "Sim" : "Não"));
            }
            System.out.println();
        }
        aguardarEnter();
    }

    private static void removerPorPlaca() {
        limparTela();
        System.out.println("==== Remover Veiculo por Placa ====");
        System.out.println("Informe a placa que deseja remover: ");
        String placa = input.nextLine();

        try {
            service.removerPorPlaca(placa);
            System.out.println("Veiculo removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEnter();
    }

    public static void main(String[] args) {
        String menu = """
                SISTEMA DE GERENCIAMENTO DE FROTAS
                Menu de Opções:
                1 - Cadastrar novo Veículo
                2 - Listar todos Veículos cadastrados
                3 - Pesquisar Veículo pela placa
                4 - Remover Veículo
                0 - Sair

                Digite a opção desejada:
                """;
        int opcao;
        do {
            limparTela();
            opcao = inputNumerico(menu);
            switch (opcao) {
                case 0:
                    limparTela();
                    System.out.println("VOLTE SEMPRE!!!");
                    break;
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    pesquisarPelaPlaca();
                    break;
                case 4:
                    removerPorPlaca();
                    break;
                default:
                    System.out.println("Opção Inválida!!!");
                    aguardarEnter();
                    break;
            }
        } while (opcao != 0);
    }
}