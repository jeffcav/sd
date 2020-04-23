import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// Classe que implementa uma calculadora usando, por baixo, um serviço 
// de calculadora REST, via requisições HTTP.
public class Calculadora {
	
	// Classe que solicita operações através de requisições HTTP
	public class CalculadoraClientHTTP {
		
		// Valores usados pelo serviço de calculadora.
		private int OPER_SOMA = 1;
		private int OPER_SUB  = 2;
		private int OPER_MULT = 3;
		private int OPER_DIV  = 4;
		
		// Função que monta uma requisição HTTP com a operação matemática e seus operandos
		// Ela abre uma conexão com um servidor HTTP, monta uma requisição de cálculo
		// e envia usando o método POST do HTTP. Os parâmetros são convertidos de inteiro para
		// String ao montar a requisição, e de string para inteiro ao retornar o valor calculado.
		// O campo op pode possuir os seguintes valores:
		// 1-somar 2-subtrair 3-multiplicar 4-dividir
		private int compute(int a, int b, int op) {
			String result="";
			try{
				
				// Abrir conexão com servidor HTTP responsável pelo serviço de calculadora 
				URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
			    conn.setConnectTimeout(15000);
			    conn.setRequestMethod("POST");
			    conn.setDoInput(true);
			    conn.setDoOutput(true) ;
	
			    // Montagem e envio da requisição de cálculo
			    OutputStream os = conn.getOutputStream();
			    BufferedWriter writer = new BufferedWriter(
			    new OutputStreamWriter(os, "UTF-8"));
			    writer.write(String.format("oper1=%d&oper2=%d&operacao=%d", a, b, op));
			    writer.flush();
			    writer.close();
			    os.close();
	
			    // Recepção da resposta e geração do valor de retorno da função
			    int responseCode=conn.getResponseCode();
			    if (responseCode == HttpsURLConnection.HTTP_OK) {
			            BufferedReader br = new BufferedReader(
			                    new InputStreamReader(conn.getInputStream(), "utf-8"));
			            StringBuilder response = new StringBuilder();
			            String responseLine = null;
			            while ((responseLine = br.readLine()) != null) {
			                response.append(responseLine.trim());
			            }
			            result = response.toString();
		        }
			} catch (Exception e) {
				return 0;
			}
		    
		    return Integer.parseInt(result);
		}
		
		// Calcula a + b
		public int soma(int a, int b) {
			return compute(a, b, OPER_SOMA);
		}
		
		// Calcula a - b
		public int sub(int a, int b) {
			return compute(a, b, OPER_SUB);
		}
		
		// Calcula a * b
		public int mul(int a, int b) {
			return compute(a, b, OPER_MULT);
		}
		
		// Calcula a / b
		public int div(int a, int b) {
			return compute(a, b, OPER_DIV);
		}
	}

	public static void main(String[] args) {
		int a = 10, b = 2, c = 7, d = 13;
		
		Calculadora calc = new Calculadora();
		Calculadora.CalculadoraClientHTTP webcalc = calc.new CalculadoraClientHTTP();
		
		System.out.println(String.format("%d + %d = %d\n", d, c, webcalc.soma(d, c)));
		System.out.println(String.format("%d - %d = %d\n", d, c, webcalc.sub(d, c)));
		System.out.println(String.format("%d * %d = %d\n", c, b, webcalc.mul(c, b)));
		System.out.println(String.format("%d / %d = %d\n", a, b, webcalc.div(a, b)));

	}
}
