package cn.com.RMIRPC.Nam.client;

import cn.com.RMIRPC.HelloWorld;
import lombok.extern.log4j.Log4j;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@Log4j
public class NamingClient {
    public static void main(String[] args) {
        try {
            LocateRegistry.getRegistry(HelloWorld.PORT);
            HelloWorld helloWorld = (HelloWorld)Naming.lookup(HelloWorld.REMOTEADDR);
            helloWorld.run("我是 Naming 实现");
            log.info("[NamingClient] - 调用成功");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
