package cn.com.RMIRPC.simple.service;

import cn.com.RMIRPC.HelloWorld;
import cn.com.RMIRPC.Impl.HelloWorldImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloWorldService {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1999);
            HelloWorld helloWorld = new HelloWorldImpl();

            registry.rebind("helloWorld1",helloWorld);
            System.out.println("======= 启动RMI服务成功! =======");

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
