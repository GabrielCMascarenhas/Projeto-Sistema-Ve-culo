import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ServiceVeiculo {
    private static List<Veiculo> frota = new ArrayList<>();

    public static Veiculo save(Veiculo veiculo) {
        frota.add(veiculo);
        return veiculo;
    }

    public void cadastrar(Veiculo veiculo) throws Exception {
        if (veiculo.getMarca() == null || veiculo.getMarca().isEmpty()) {
            throw new Exception("A marca do veiculo não pode ser vazia.");
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty()) {
            throw new Exception("O modelo do veiculo não pode ser vazio.");
        }

        if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
            throw new Exception("A placa do veiculo não pode ser vazia.");
        }

        if (veiculo.getAno() <= 1800 || veiculo.getAno() > LocalDate.now().getYear()) {
            throw new Exception("Informe um ano maior que 1800...");
        }

        for (Veiculo v : frota) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                throw new Exception("Já existe um veículo cadastrado com a placa informada: " + veiculo.getPlaca());
            }
        }
        frota.add(veiculo);
    }

    public static List<Veiculo> findAll() {
        return frota;
    }

    public static Veiculo findByPlaca(String placa) throws Exception {
        Veiculo veiculoRet = null;
        for (Veiculo veiculo : frota) {
            if (veiculo.getPlaca().equals(placa)) {
                veiculoRet = veiculo;
                break;
            }
        }
        if (veiculoRet == null)
            throw new Exception("Veículo não encontrado com a placa informada");
        return veiculoRet;
    }

    public void removerPorPlaca(String placa) throws Exception {
        boolean placaRemovida = false;
        for (Veiculo veiculo : frota) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                frota.remove(veiculo);
                placaRemovida = true;
                break;
            }
        }
        if (!placaRemovida) {
            throw new Exception("Nenhum veiculo encontrado com essa placa" + placa);
        }
    }

    public List<Veiculo> pesquisarTodos() {
        return ServiceVeiculo.frota;
    }
}