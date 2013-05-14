package br.com.renato.Server;

import br.com.renato.mensagem.Mensagem;
import br.com.renato.utilitarios.io.Console;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ServerApp {

    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");
        try {
            ArrayList ClienteBean = new ArrayList<>();
            ServerSocket servidor = new ServerSocket(1234);
            InputStream entrada;
            BufferedReader read;
            OutputStream saida;
            PrintStream ps;

            while (true) {
                Console.escrever("aguardando conexao...");
                Socket socket = servidor.accept();

                Console.escreverln("cliente conectado:" + socket.getRemoteSocketAddress());

                entrada = socket.getInputStream();
                read = new BufferedReader(new InputStreamReader(entrada));

                if (ClienteBean.add(new Gson())) {

                    ClienteBean = new ArrayList<>();
                    ArrayList<String> nomes = new ArrayList<>();
                    nomes.listIterator();
                    Console.escreverln("cliente:" + ClienteBean.toArray().toString());
                    Console.escreverln("nomes:" + nomes.toString());
                }
                String msgCliente = read.readLine();
                Console.escreverln("TIPO:" + msgCliente);
                saida = socket.getOutputStream();
                ps = new PrintStream(saida);

                if ("DATA_HORA".equals(msgCliente)) {
                    ps.println(new Date().toString());
                }
                msgCliente = read.readLine();
                Console.escreverln("TIPO:" + msgCliente);

                if ("MSG".equals(msgCliente)) {
                    String json = read.readLine();
                    Console.escrever("JSON:" + json);
                    Gson gson = new Gson();
                    Mensagem ms = gson.fromJson(json, Mensagem.class);
                    Console.escreverln("data:" + ms.getData());
                    Console.escreverln("Destinatario:" + ms.getDestino());
                    Console.escreverln("Assunto:" + ms.getMsg());
                    ps.println("OK");
                }

                Console.escreverln("Servidor Finalizado!");

                read.close();
                entrada.close();
                socket.close();
            }
        } catch (IOException ex) {
        }
    }
}
