import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote{
	// Stub das operações suportadas pela calculadora remota
	public int soma(int a, int b) throws RemoteException;
	public int sub(int a, int b) throws RemoteException;
	public int mult(int a, int b) throws RemoteException;
	public int div(int a, int b) throws RemoteException;
}
