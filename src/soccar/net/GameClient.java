package soccar.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import static com.esotericsoftware.minlog.Log.LEVEL_TRACE;

/**
 * Created by Marc on 5-10-2016.
 */
public class GameClient {

    private Client client;
//    private String host = "192.168.178.10";
    private String host = "83.162.231.77";
    private int port = 9001;

    public GameClient() {

        client = new Client();
        Log.set(LEVEL_TRACE);

        Kryo kryo = client.getKryo();
        kryo.register(java.util.ArrayList.class);
        kryo.register(ThrottleAction.class);
        kryo.register(SteerAction.class);
        kryo.register(Player.class);
        kryo.register(Room.class);
    }

    public void connect() {

        client.start();

        try {
            client.connect(5000, host, port);

            listen();
            sendPlayer(null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen() {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                System.out.println("New connection from " + connection.getRemoteAddressTCP().getHostString());

                if (object instanceof Room) {
                    Room room = (Room) object;
                    System.out.println(room.name);
                }
            }
        });
    }

    public void sendPlayer(Player player) {
        Player p = new Player();
        p.name = "Marc";
        client.sendTCP(p);
    }

}
