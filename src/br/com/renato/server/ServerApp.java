package br.com.renato.Server;

import br.com.renato.mensagem.Mensagem;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerApp {

    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");
        try {
            ServerSocket servidor = new ServerSocket(1234);
            InputStream entrada;
            BufferedReader read;
            OutputStream saida;
            PrintStream ps;

            while (true) {
                System.out.println("aguardando conexao...");
                Socket socket = servidor.accept();

                System.out.println("cliente conectado:" + socket.getRemoteSocketAddress());

                entrada = socket.getInputStream();
                read = new BufferedReader(new InputStreamReader(entrada));

                String msgCliente = read.readLine();
                System.out.println("TIPO:" + msgCliente);
                saida = socket.getOutputStream();
                ps = new PrintStream(saida);

                if ("DATA_HORA".equals(msgCliente)) {
                    ps.println(new Date().toString());
                }
                msgCliente = read.readLine();
                System.out.println("TIPO:" + msgCliente);

                if ("MSG".equals(msgCliente)) {
                    String json = read.readLine();
                    System.out.println("JSON" + json);
                    Gson gson = new Gson();
                    Mensagem ms = gson.fromJson(json, Mensagem.class);
                    System.out.println(""+ ms.getData());
                    System.out.println("Destinatario:" + ms.getDestino());
                    System.out.println("Assunto:" + ms.getMsg());
                    ps.println("OK");
                }
                msgCliente = read.readLine();
                System.out.println("TIPO:" + msgCliente);


                System.out.println("Servidor Finalizado!");

                read.close();
                entrada.close();
                socket.close();
            }
        } catch (IOException ex) {
        }
    }
}
