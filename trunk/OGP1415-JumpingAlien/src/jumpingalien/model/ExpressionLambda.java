package jumpingalien.model;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public interface ExpressionLambda {
	public Object f(Object[] parameterArray);
	//Bij Expression moet niet staan "implements ExpressionLambda" aangezien
	//Expression als attributiet een instantie heeft van ExpressionLambda.
	//Expression gebruikt geen methode van ExpressionLambda, maar eerder
	//een object ervan.
	
	//Dit is een functionele interface (slechts een abstracte methode).
	//Naam vd methode kan dus vermeden worden wanneer ze geimplementeerd wordt.
}
