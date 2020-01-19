package cn.com.RMIRPC.Impl;

import cn.com.RMIRPC.HelloWorld;
import lombok.extern.log4j.Log4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Log4j
public class HelloWorldImpl extends UnicastRemoteObject implements HelloWorld {
    public HelloWorldImpl() throws RemoteException {
        super();
    }

    @Override
    public String run(String name) throws RemoteException {
        log.info(String.format("this is HelloWorldImpl info: %s ",name));
        return String.format("this is HelloWorldImpl info: %s ",name);
    }
}
