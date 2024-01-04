package br.com.alura.services;

import br.com.alura.dominio.Pet;
import br.com.alura.services.util.Formulario;
import br.com.alura.services.util.Requisicao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PetService {

    private final Requisicao requisicao;

    public PetService(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

    public void listarPetsDoAbrigo() throws IOException, InterruptedException {
        String idOuNome = Formulario.getString("Digite o id ou nome do abrigo:");

        HttpResponse<String> response = requisicao.get("http://localhost:8080/abrigos/" +idOuNome +"/pets");
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
            return;
        }

        List<Pet> pets = Arrays.stream(new ObjectMapper().readValue(response.body(), Pet[].class)).toList();

        System.out.println("Pets cadastrados:");
        pets.forEach(Pet::imprimirPetCadastrado);
    }

    public void importarPetsDoAbrigo() throws IOException, InterruptedException {
        String idOuNome = Formulario.getString("Digite o id ou nome do abrigo:");
        String nomeArquivo = Formulario.getString("Digite o nome do arquivo CSV:");

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
            return;
        }
        String line;
        while ((line = reader.readLine()) != null) {
            Pet pet = toPet(line.split(","));

            HttpResponse<String> response = requisicao.post("http://localhost:8080/abrigos/" + idOuNome + "/pets", pet);

            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + pet.getNome());
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + pet.getNome());
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }

    private Pet toPet(String[] campos) {
        Pet pet = new Pet();
        pet.setTipo(campos[0]);
        pet.setNome(campos[1]);
        pet.setRaca(campos[2]);
        pet.setIdade(Integer.parseInt(campos[3]));
        pet.setCor(campos[4]);
        pet.setPeso(Float.parseFloat(campos[5]));
        return pet;
    }
}
