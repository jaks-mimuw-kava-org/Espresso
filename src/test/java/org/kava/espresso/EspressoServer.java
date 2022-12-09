package org.kava.espresso;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EspressoServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 7999;
        ServerSocket s = new ServerSocket(port);
        while (true) {
            Socket client = s.accept();

            ObjectInputStream is = new ObjectInputStream(client.getInputStream());

            String pathname = (String) is.readObject();

            String data = new String(Files.readAllBytes(Paths.get(pathname)), StandardCharsets.UTF_8);

            ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());

            os.writeObject(data);

            is.close();
        }
    }
}
