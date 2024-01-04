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

    public void cadastrarAbrigo() throws IOException, InterruptedException {
        Abrigo abrigo = formularioCadastroAbrigo();

        HttpResponse<String> response = Requisicao.post("http://localhost:8080/abrigos", abrigo);
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }

    private Abrigo formularioCadastroAbrigo() {
        Abrigo abrigo = new Abrigo();

        abrigo.setNome(Formulario.getString("Digite o nome do abrigo:"));
        abrigo.setTelefone(Formulario.getString("Digite o telefone do abrigo:"));
        abrigo.setEmail(Formulario.getString("Digite o email do abrigo:"));

        return abrigo;
    }

    public void listarAbrigo() throws IOException, InterruptedException {
        HttpResponse<String> response = Requisicao.get("http://localhost:8080/abrigos");
        List<Abrigo> abrigos = Arrays.stream(new ObjectMapper().readValue(response.body(), Abrigo[].class)).toList();

        System.out.println("Abrigos cadastrados:");
        for (Abrigo abrigo : abrigos) {
            System.out.println(abrigo.getId() + " - " + abrigo.getNome());
        }
    }
}
