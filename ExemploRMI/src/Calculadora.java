import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// Essa classe implementa os métodos presentes no skeleton, servido as chamadas remotas feitas pelo cliente.
// Além de criar um registry ao qual o cliente se conecta para descoberta do serviço remoto implementando o serviço de calculadora.
public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas = 0;

	public int soma(int a, int b) throws RemoteException {
		System.out.println("Metodo soma chamado " + chamadas++);
		return a + b;
	}
	
	public int sub(int a, int b) throws RemoteException {
		System.out.println("Metodo subtrai chamado " + chamadas++);
		return a - b;
	}
	
	public int mult(int a, int b) throws RemoteException {
		System.out.println("Metodo multiplica chamado " + chamadas++);
		return a * b;
	}
	
	public int div(int a, int b) throws RemoteException {
		System.out.println("Metodo divide chamado " + chamadas++);
		return a / b;
	}

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		Calculadora calculadora = new Calculadora();		
		Registry reg = null;
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100);
		try {
			System.out.println("Creating registry\n");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		System.out.println("Registry created\n");
		reg.rebind("calculadora", stub);
	}
}
