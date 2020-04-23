// Clase que de fato implementa as operações de uma calculadora
// no lado do servidor.
public class Calculadora {
    public double soma(double oper1, double oper2) {
        return oper1 + oper2;
    }
    
    public double sub(double oper1, double oper2) {
        return oper1 - oper2;
    }
    
    public double mult(double oper1, double oper2) {
        return oper1 * oper2;
    }
    
    public double div(double oper1, double oper2) {
        return oper1 / oper2;
    }
}