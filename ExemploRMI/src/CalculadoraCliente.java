import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CalculadoraCliente {
	
	public static void main(String[] args) {
		Registry reg = null;
		ICalculadora calc;
		int a = 10, b = 2, c = 7, d = 13;
		try {
			reg = LocateRegistry.getRegistry(1099);
			calc = (ICalculadora) reg.lookup("calculadora");
			
			// Objeto cliente invocando as chamads remotas via RMI usando os métodos do stub
			System.out.println(String.format("%d + %d = %d\n", d, c, calc.soma(d, c)));
			System.out.println(String.format("%d - %d = %d\n", d, c, calc.sub(d, c)));
			System.out.println(String.format("%d * %d = %d\n", c, b, calc.mult(c, b)));
			System.out.println(String.format("%d / %d = %d\n", a, b, calc.div(a, b)));
			
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
