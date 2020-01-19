package cn.com.RMIRPC;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloWorld extends Remote {
    public final static int PORT = 1999;
    public final static String REMOTEADDR="rmi://localhost:"+PORT+"/HelloNaming";
    String run(String name)throws RemoteException;
}
