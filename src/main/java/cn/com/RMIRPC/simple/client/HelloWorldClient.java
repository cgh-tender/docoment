package cn.com.RMIRPC.simple.client;

import cn.com.RMIRPC.HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloWorldClient {

    public static void main(String[] args) {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(1999);
            HelloWorld helloWorld1 = (HelloWorld)registry.lookup("helloWorld1");
            helloWorld1.run("aaa");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
