package juego;

public class Mago extends Roll 
{
	
	//String nombre, int vidaMax, int ataqueBase, int defensa, int manaMax
	public Mago(String nombre)
	{
		super(nombre, 60, 70, 5, 200); 
	}
    @Override
    	
    public void habilidadNormal(Roll Mago)
	{
  	  	int nuevaVida = Math.min(Mago.getVidaMax(), Mago.getVida() + 5);
      
        	System.out.println(nombre + " llama a los espiritus y se cura 5 puntos");
        	System.out.println(nuevaVida);
	}

    @Override
    public void habilidadEspecial(Roll objetivo) 
    	{
    		int danio = ataqueBase + 20;
    		System.out.println(nombre + " canaliza un hechizo creando una bola de fuego y la lanza a: " + objetivo.nombre);
    		objetivo.recibirAtaque(danio);
    		System.out.println("-" + danio);
    	}
}
