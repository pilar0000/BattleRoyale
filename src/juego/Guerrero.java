package juego;

public class Guerrero extends Roll {
	
	//String nombre, int vidaMax, int ataqueBase, int defensa, int manaMax
	public Guerrero(String nombre)
	{
		super(nombre, 150, 20, 50, 70); 
	}

    @Override
    public void habilidadNormal(Roll Guerrero)
	{
    	System.out.println(nombre + " Dashea hacia el frente");
	}

    @Override
    public void habilidadEspecial(Roll objetivo) 
	{
    	int danio = ataqueBase * 2;
    	System.out.println(nombre + " El guerrero entra en un estado de Berserk y ataque dos veces a: " + objetivo.nombre);
    	objetivo.recibirAtaque(danio);
    	System.out.println("-" + danio);
	}
}
