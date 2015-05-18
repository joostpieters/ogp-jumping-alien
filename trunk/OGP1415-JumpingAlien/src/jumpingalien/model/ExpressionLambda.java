package jumpingalien.model;


/** 
 * A functional interface that is used to evaluate functions of expressions.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public interface ExpressionLambda {
	public Object f(Object[] parameterArray);
	//Bij Expression moet niet staan "implements ExpressionLambda" aangezien
	//Expression als attribuut een instantie heeft van ExpressionLambda.
	//Expression gebruikt geen methode van ExpressionLambda, maar eerder
	//een object ervan.
	
	//Dit is m.a.w. een functionele interface (slechts 1 abstracte methode).
	//Lambda's zijn synctionele suiker voor instanties van functionele interfaces.
}
