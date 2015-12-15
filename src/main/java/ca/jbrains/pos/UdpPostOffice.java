package ca.jbrains.pos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpPostOffice implements PostOffice {
    private final String hostName;
    private final int port;
    private final String textEncoding;

    public UdpPostOffice(String hostName, int port, String textEncoding) {
        this.hostName = hostName;
        this.port = port;
        this.textEncoding = textEncoding;
    }

    @Override
    public void sendMessage(String text) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverInetAddress = InetAddress.getByName(hostName);
            byte[] payload = text.getBytes(textEncoding);
            DatagramPacket message = new DatagramPacket(
                    payload, payload.length, serverInetAddress, port);
            clientSocket.send(message);
        }
        catch (IOException unrecoverable) {
            throw new RuntimeException(
                    String.format(
                            "Unable to send message '%s' by UDP to %s:%d",
                            text, hostName, port
                    ),
                    unrecoverable
            );
        }
    }
}
