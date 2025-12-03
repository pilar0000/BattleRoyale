package juego;

public class Main 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Roll a = new Arquero("Legolas");
		System.out.println(a);
			
		System.out.println("-----------------------------------------------------");
			
		Roll g = new Guerrero("Miguel el retrasado");
		System.out.println(g);
			
		System.out.println("-----------------------------------------------------");
			
		Roll m = new Mago("Gilgamesh");
		System.out.println(m);
			
		System.out.println("-----------------------------------------------------");
	   	g.habilidadEspecial(m);
	   	m.habilidadEspecial(a);
	   	a.habilidadEspecial(g);
	    m.habilidadNormal(m);
	    	
	   	System.out.println("-----------------------------------------------------");
	    	
	    System.out.println("vida de:" + m.nombre + " " + m.vida);
	   	System.out.println("vida de :" + a.nombre + " " + a.vida);
	    System.out.println("vida de:" + g.nombre + " " + g.vida);
	    	
	}
}