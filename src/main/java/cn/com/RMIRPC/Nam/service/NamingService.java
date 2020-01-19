package cn.com.RMIRPC.Nam.service;

import cn.com.RMIRPC.HelloWorld;
import cn.com.RMIRPC.Impl.HelloWorldImpl;
import lombok.extern.log4j.Log4j;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@Log4j
public class NamingService {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(HelloWorld.PORT);
            HelloWorld helloWorld = new HelloWorldImpl();
            Naming.rebind(HelloWorld.REMOTEADDR,helloWorld);
            log.info("[NamingService] - 启动成功");
        } catch (RemoteException | MalformedURLException  e) {
            e.printStackTrace();
        }
    }
}
