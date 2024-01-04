package br.com.alura.services;

import br.com.alura.dominio.Abrigo;
import br.com.alura.services.util.Requisicao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AbrigoServiceTest {

    @Mock
    private Requisicao requisicao;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Quando um abrigo é cadastrado corretamente, então é apresentado uma mensagem de sucesso e o corpo da response")
    public void sucessoAoCadastrarNovoAbrigo() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        doReturn(mockResponse).when(requisicao).post(anyString(), any());
        doReturn(200).when(mockResponse).statusCode();
        doReturn("Teste status code 200").when(mockResponse).body();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setIn(InputStream.nullInputStream());

        AbrigoService service = new AbrigoService(requisicao);
        service.cadastrarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        assertEquals("Abrigo cadastrado com sucesso!", lines[3]);
        assertEquals("Teste status code 200", lines[4]);
    }

    @Test
    @DisplayName("Quando ocorrer um erro de status 400, então deve apresentar uma mensagem de erro e imprimir o corpo da response")
    public void deveImprimirUmaMensagemDeErroParaOStatus400() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        doReturn(mockResponse).when(requisicao).post(anyString(), any());
        doReturn(400).when(mockResponse).statusCode();
        doReturn("Teste status code 400").when(mockResponse).body();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setIn(InputStream.nullInputStream());

        AbrigoService service = new AbrigoService(requisicao);
        service.cadastrarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        assertEquals("Erro ao cadastrar o abrigo:", lines[3]);
        assertEquals("Teste status code 400", lines[4]);
    }

    @Test
    @DisplayName("Quando ocorrer um erro de status 500, então deve apresentar uma mensagem de erro e imprimir o corpo da response")
    public void deveImprimirUmaMensagemDeErroParaOStatus500() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        doReturn(mockResponse).when(requisicao).post(anyString(), any());
        doReturn(500).when(mockResponse).statusCode();
        doReturn("Teste status code 500").when(mockResponse).body();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setIn(InputStream.nullInputStream());

        AbrigoService service = new AbrigoService(requisicao);
        service.cadastrarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        assertEquals("Erro ao cadastrar o abrigo:", lines[3]);
        assertEquals("Teste status code 500", lines[4]);
    }

    @Test
    @DisplayName("Quando houver abrigos cadastrados, então deve-se imprimi-los")
    public void deveImprimirListaDeAbrigosCadastrados() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        List<Abrigo> abrigos = List.of(new Abrigo(0L, "PetFriend", "11987456321", "petfriend@gmail.com", List.of()));

        doReturn(mockResponse).when(requisicao).get(anyString());
        doReturn(new ObjectMapper().writeValueAsString(abrigos)).when(mockResponse).body();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        AbrigoService service = new AbrigoService(requisicao);
        service.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        assertEquals("Abrigos cadastrados:", lines[0]);
        assertEquals("0 - PetFriend", lines[1]);
    }

    @Test
    @DisplayName("Quando retornado lista vazia de Abrigos, então deve exibir uma mensagem informando que não há abrigos cadastrados")
    public void deveExibirMensagemQuandoNaoHaAbrigosCadastrados() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        doReturn(mockResponse).when(requisicao).get(anyString());
        doReturn("[]").when(mockResponse).body();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        AbrigoService service = new AbrigoService(requisicao);
        service.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        assertEquals("Não há abrigos cadastrados", lines[0]);
    }
}