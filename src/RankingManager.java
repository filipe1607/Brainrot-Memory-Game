import javax.swing.*;
import java.io.*;
import java.util.*;

public class RankingManager {
    private static final String ARQUIVO = "ranking.csv";
    private static final int MAX_ENTRADAS = 10;

    public static void salvar(String nome, int tentativas) {
        List<String[]> ranking = carregar();

        ranking.add(new String[]{nome, String.valueOf(tentativas)});

        ranking.sort(Comparator.comparingInt(o -> Integer.parseInt(o[1])));

        if (ranking.size() > MAX_ENTRADAS) {
            ranking = ranking.subList(0, MAX_ENTRADAS);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (String[] entrada : ranking) {
                writer.write(entrada[0] + "," + entrada[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> carregar() {
        List<String[]> ranking = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return ranking;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    ranking.add(partes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ranking;
    }

    public static void mostrarPlacar() {
        List<String[]> ranking = carregar();
        StringBuilder placar = new StringBuilder("🏆 TOP 10 Jogadores (menos tentativas):\n\n");

        if (ranking.isEmpty()) {
            placar.append("Nenhuma pontuação salva ainda.");
        } else {
            int i = 1;
            for (String[] entrada : ranking) {
                placar.append(i++).append("º - ")
                        .append(entrada[0]).append(": ")
                        .append(entrada[1]).append(" tentativas\n");
            }
        }

        JOptionPane.showMessageDialog(null, placar.toString(), "Placar", JOptionPane.INFORMATION_MESSAGE);
    }
}