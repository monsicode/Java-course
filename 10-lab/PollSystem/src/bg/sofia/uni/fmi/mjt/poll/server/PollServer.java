package bg.sofia.uni.fmi.mjt.poll.server;

import bg.sofia.uni.fmi.mjt.poll.server.command.CommandCreator;
import bg.sofia.uni.fmi.mjt.poll.server.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class PollServer {

    private static final int BUFFER_SIZE = 1024;
    private static final String HOST_NAME = "localhost";

    private CommandExecutor commandExecutor;
    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    private PollRepository pollRepository;

    public PollServer(int port, PollRepository pollRepository) {
        this.port = port;
        this.pollRepository = pollRepository;
        commandExecutor = new CommandExecutor(pollRepository);
    }

    public PollServer(int port, CommandExecutor commandExecutor) {
        this.port = port;
        this.commandExecutor = commandExecutor;
    }

    public void start() {
        System.out.println("Server is waiting for clients..");

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            selector = Selector.open();
            configureServerSocketChannel(serverSocketChannel, selector);

            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            isServerWorking = true;

            while (isServerWorking) {
                try {
                    int readyChannels = selector.select();

                    if (readyChannels == 0) {
                        continue;
                    }

                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    iteratingKeys(keyIterator);

                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("Error occurred while processing client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to start server", e);
        }
    }

    private void iteratingKeys(Iterator<SelectionKey> keyIterator) throws IOException {
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();

            if (key.isReadable()) {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                String clientInput = getClientInput(clientChannel);
                System.out.println(clientInput);

                if (clientInput == null) {
                    continue;
                }
                clientInput = clientInput.trim().replaceAll("[\\r\\n]+", "").replaceAll("\\s+", " ");
                String output =
                    commandExecutor.execute(CommandCreator.newCommand(clientInput));
                writeClientOutput(clientChannel, output);

            } else if (key.isAcceptable()) {
                accept(selector, key);
            }

            keyIterator.remove();

        }
    }

    public void stop() {
        isServerWorking = false;

        if (selector != null && selector.isOpen()) {
            try {
                selector.close();
            } catch (IOException e) {
                System.out.println("Failed to close selector: " + e.getMessage());
            }
        }
        System.out.println("Server stopped and resources released.");
    }

    private void configureServerSocketChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        channel.bind(new InetSocketAddress(HOST_NAME, this.port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private String getClientInput(SocketChannel clientChannel) throws IOException {
        buffer.clear();

        int readBytes = clientChannel.read(buffer);

        if (readBytes < 0) {
            clientChannel.close();
            return null;
        }

        buffer.flip();

        //?????
        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        String rawInput = new String(clientInputBytes, StandardCharsets.UTF_8);
        return rawInput.trim().replaceAll("[\\r\\n]+", "").replaceAll("\\s+", " ");
    }

    private void writeClientOutput(SocketChannel clientChannel, String output) throws IOException {
        buffer.clear();
        buffer.put(output.getBytes());
        buffer.flip();

        clientChannel.write(buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);

        System.out.println("New client connected: " + accept.getRemoteAddress());
    }

    public static void main(String[] args) {
        PollRepository repo = new InMemoryPollRepository();
        CommandExecutor commandExecutor = new CommandExecutor(repo);

        int port = 8080;
        PollServer server = new PollServer(port, commandExecutor);
        server.start();
    }

}

