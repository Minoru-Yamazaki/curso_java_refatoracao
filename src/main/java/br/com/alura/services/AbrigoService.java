package br.com.alura.services;

import br.com.alura.dominio.Abrigo;
import br.com.alura.services.util.Formulario;
import br.com.alura.services.util.Requisicao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class AbrigoService {

    private final Requisicao requisicao;

    public AbrigoService(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

    public void cadastrarAbrigo() throws IOException, InterruptedException {
        Abrigo abrigo = formularioCadastroAbrigo();

        HttpResponse<String> response = requisicao.post("http://localhost:8080/abrigos", abrigo);
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(response.body());
        }
    }

    public void listarAbrigo() throws IOException, InterruptedException {
        HttpResponse<String> response = requisicao.get("http://localhost:8080/abrigos");
        List<Abrigo> abrigos = Arrays.stream(new ObjectMapper().readValue(response.body(), Abrigo[].class)).toList();

        if (abrigos.isEmpty()) {
            System.out.println("Não há abrigos cadastrados");
        } else {
            System.out.println("Abrigos cadastrados:");
            abrigos.forEach(Abrigo::imprimirAbrigoCadastrado);
        }
    }

    private Abrigo formularioCadastroAbrigo() {
        Abrigo abrigo = new Abrigo();

        abrigo.setNome(Formulario.getString("Digite o nome do abrigo:"));
        abrigo.setTelefone(Formulario.getString("Digite o telefone do abrigo:"));
        abrigo.setEmail(Formulario.getString("Digite o email do abrigo:"));

        return abrigo;
    }
}
