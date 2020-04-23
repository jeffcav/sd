import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    Calculadora calc = new Calculadora();
		try {
			welcomeSocket = new ServerSocket(9090);
			int i=0; //número de clientes
			
			System.out.println ("Servidor no ar");
			while(true) { 
	  
				Socket connectionSocket = welcomeSocket.accept(); 
				i++;
				System.out.println ("Nova conexão");
	           
				//Interpretando dados do servidor
				socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String operacao= socketEntrada.readLine();
				String oper1=socketEntrada.readLine();
				String oper2=socketEntrada.readLine();

				// Convertendo parâmetros recebidos no formato string para
				// a representação numérica usada pela classe que implementa
				// a calculadora.
				double a = Double.parseDouble(oper1);
				double b = Double.parseDouble(oper2);
				double res;
				int op = Integer.parseInt(operacao);
	           
				// Gerando o resultado de acordo com a operação solicitada
				switch(op) {
	           		case 1:
	           			res = calc.soma(a, b);
	           			break;
	           		case 2:
	           			res = calc.sub(a, b);
	           			break;
	           		case 3:
	           			res = calc.mult(a, b);
	           			break;
	           		case 4:
	           			res = calc.div(a, b);
	           			break;
	           		default:
	           			res = 0.0;
	           	}
				
				// Convertendo o resultado para o formato usado no envio dos dados
				// para o cliente.
				String result = String.valueOf(res); 

				//Enviando dados para o cliente
				socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
				socketOutput.writeBytes(result+ '\n');
				System.out.println (result);	           
				socketOutput.flush();
				socketOutput.close();        
	      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}

}
