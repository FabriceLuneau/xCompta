 package etatViewer;

import java.util.StringTokenizer;


class Expression {
    String			text;
    StringTokenizer	tokens;
    String			token;
    

    Expression(String text) {
    	this.text = text.trim();
	}

    float eval() {
    	tokens = new StringTokenizer(text, "*/+-()", true );
    	//tokens = new StringTokenizer(text, "()", true );

    	try {
    		System.out.println(text.matches("(.+[^()])"));
    		//System.out.println(text.regionMatches(0, "(.+[^()])",0,0) );
    		
    		//String [] tab = text.split("(.+[^()])");
    		String [] tab = text.split("(.+[^()])");
    		
    		System.out.println( tab.length );

    		for(int i=0;i<tab.length;i++)
    			System.out.println( tab[i] );

    		//System.out.println( text.split("(.*") );
    		
    		
/*    		
    		while(tokens.hasMoreTokens()) {
    			System.out.print(tokens.nextElement() + " ");
    			//System.out.print( (tokens.nextToken()).m + " ");
			}
    		System.out.print("\n");
*/
    		return 1;
		} catch ( Exception e ) {
			System.out.println( e );
			return 0;
		}
	}

  public static void main( String [] args ) {
	  Expression expression = new Expression( "((1+2)+3)*4+5");
	  
	  System.out.println( expression.eval() );
  }
}