import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// Classe que implementa uma calculadora usando um servi�o de calculadora acess�vel atrav�s de um socket.
public class Calculadora {
	
	// Classe interna que implementa a calculadora atraves de chamadas, via socket, a um servi�o de calculadora.
	public class CalculadoraClientSocket {
		
		// Valores usados pelo servi�o de calculadora para determinar a opera��o.
		private int OPER_SOMA = 1;
		private int OPER_SUB  = 2;
		private int OPER_MULT = 3;
		private int OPER_DIV  = 4;

		// Fun��o que efetivamente monta a mensagem para o servi�o de calculadora e a
		// transmite atrav�s de um socket, conectando-se ao servidor do servi�o calculadora
		// rodando na porta 9090 da m�quina local. Os operandos, opera��o e resultado s�o convertidos
		// do formato double do java para string, no formato que o servidor do servi�o usa para receber
		// os operandos e opera��o e para emitir um resultado.
		// a e b s�o os operandos, e operacao segue o formato:
		// 1=soma, 2=subtracao, 3=multiplica��o, 4=divis�o
		public double calcular(double a, double b, int operacao) {
			String result="0.0";
			
	        try {
	        	// Conex�o com o Servidor
	            Socket clientSocket = new Socket("127.0.0.1", 9090);
	            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
	            
	            // Enviando os dados
	            socketSaidaServer.writeBytes(String.valueOf(operacao) + "\n");
	            socketSaidaServer.writeBytes(String.valueOf(a) + "\n");
	            socketSaidaServer.writeBytes(String.valueOf(b) + "\n");
	            socketSaidaServer.flush();

	            //Recebendo a resposta
	            BufferedReader messageFromServer = new BufferedReader
	                    (new InputStreamReader(clientSocket.getInputStream()));
	            result = messageFromServer.readLine();
	            
	            clientSocket.close();

	        } catch (IOException e) {
	        	e.printStackTrace();
	        }

	        return Double.parseDouble(result);
		}
		
		// Calcula a + b atrav�s de chamada que invoca o servi�o de calculadora na rede  atrav�s de um socket.
		// a e b s�o os operandos
		public double soma(double a, double b) {
			return calcular(a, b, OPER_SOMA);
		}
		
		// Calcula a - b atrav�s de chamada que invoca o servi�o de calculadora na rede  atrav�s de um socket.
		// a e b s�o os operandos
		public double sub(double a, double b) {
			return calcular(a, b, OPER_SUB);
		}
		
		// Calcula a * b atrav�s de chamada que invoca o servi�o de calculadora na rede atrav�s de um socket.
		// a e b s�o os operandos
		public double mul(double a, double b) {
			return calcular(a, b, OPER_MULT);
		}
		
		// Calcula a / b atrav�s de chamada que invoca o servi�o de calculadora na rede atrav�s de um socket.
		// a e b s�o os operandos
		public double div(double a, double b) {
			return calcular(a, b, OPER_DIV);
		}

	}
	
	public static void main(String[] args) {
		double a = 10, b = 2, c = 7, d = 13;
		
		Calculadora calc = new Calculadora();
		Calculadora.CalculadoraClientSocket sockcalc = calc.new CalculadoraClientSocket();
		
		System.out.println(String.format("%.2f + %.2f = %.2f\n", d, c, sockcalc.soma(d, c)));
		System.out.println(String.format("%.2f - %.2f = %.2f\n", d, c, sockcalc.sub(d, c)));
		System.out.println(String.format("%.2f * %.2f = %.2f\n", c, b, sockcalc.mul(c, b)));
		System.out.println(String.format("%.2f / %.2f = %.2f\n", a, b, sockcalc.div(a, b)));

	}
	
}
