import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperConnection {
    private ZooKeeper zoo;
    final CountDownLatch connectedSignal = new CountDownLatch(1);


    public ZooKeeper connect(String host) throws IOException, InterruptedException{
        zoo = new ZooKeeper(host, 5000, new Watcher() {
            public void process(WatchedEvent we) {
                if(we.getState()== Event.KeeperState.SyncConnected){
                    connectedSignal.countDown();
                }
            }
        });

        connectedSignal.await();
        return zoo;


    }

    public void close() throws InterruptedException{
        zoo.close();
    }

}
