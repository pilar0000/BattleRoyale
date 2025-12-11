package juego;


public class Arquero extends Roll {
	
	//String nombre, int vidaMax, int ataqueBase, int defensa, int manaMax
	public Arquero(String nombre)
	{
		super(nombre, 100, 50, 20, 100); 
	}
    @Override
    public void habilidadNormal()
    	{
  
        	System.out.println(nombre + " detecta la presencia de sus presas");
        	
       //     public void recibirAtaque(int danio)
 
    	}

    @Override
    public void habilidadEspecial() 
    	{
    		int danio = ataqueBase + 20;
        	System.out.println(nombre + " la flecha se cubre de esencia electrica paralizando a: " + nombre);
        	// public void recibirAtaque(int danio)
        	//objetivo.recibirAtaque(danio);
        	System.out.println("-" + danio);
    	}
}
