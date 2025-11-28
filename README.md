# BATTLE ROYALE - PROYECTO FINAL POO

Proyecto final de programación orientada a objetos.

# Descripción

El jugador participa en un Battle Royale en un mapa que se va cerrando poco a poco. En la partida:

- El jugador elije su rol (Mago, Arquero o Guerrero)
- El resto de combatientes son bots controlados por "IA"
- Todos se mueven en el mapa, buscan pociones y luchan entre sí.
- El personaje puede usar habilidades normales y especiales consumiendo maná.
- El mapa contiene pociones de vida, pociones de maná y zonas seguras que se van reduciendo.
- La partida finaliza cuando solo queda uno vivo.

**Objetivo**

- Sobrevivir hasta el final
- O ser el bot ganador si el jugador muere

---

# Roles

## Guerrero

- Vida: 100
- Maná maximo: 20
- Habilidad normal: correr, se mueve de 2 a 3 casillas.
- Habilidad especial: ataque doble en un mismo turno.

## Arquero

- Vida: 100
- Maná maximo: 20
- Habilidad normal: detección de enemigos
- Habilidad especial: flecha paralizante que inmoviliza un turno.

## Mago

- Vida: 80
- Maná maximo: 40
- Habilidad normal: curación de +5
- Habilidad especial: bola de veneno que causa daño durante tres turnos.

Todas las habilidades cuestan 2 de maná. Las habilidades especiales cuestan 20 de maná, y este se regenera +1 por turno.

---

# Herramientas y mejoras

Clases disponibles:

- **Espada**
- **Arco**
- **Baculo**

Cada herramienta tiene:

- Bonus de ataque
- Mejora automatica si se cumplen ciertas condiciones 

---

# Mapa y jugabilidad

El mapa contiene:

- Casillas libres
- Paredes
- Zonas seguras que se reducen
- Items (pociones de vida y de maná)
- Bots enemigos

### Movimientos posibles:

ARRIBA, ABAJO, IZQUIERDA, DERECHA
ARRIBA_IZQ, ARRIBA_DER
ABAJO_IZQ, ABAJO_DER

---

# IA de los bots

Hay tres niveles de dificultad

### **Fácil**

- Movimiento aleatorio
- Ataques simples

### **Normal**

- Busca al enemigo más cercano
- Se mueve hacia él

### **Dificil**

- Predice movimientos
- Usa habilidades
- Evita zonas peligrosas

---

# Arquitectura del proyecto

Clases principales:

- **Roll**: Clase abstracta tipo padre
- **Guerrero, Arquero, Mago**: Heredan de Roll
- **Herramienta** + **Espada, Arco, Báculo** 
- **Jugador** (puede ser humano o bot)  
- **Mapa** (gestión de zonas + items)  
- **Partida** (ciclo del juego, turnos, IA, ganador)  
- **Item** + Tipos de poción  
- **Logger** (ultimas 1000 acciones)  
- **GestorFicheros** (leer o guardar logs y configuración)  
- **Excepciones**  
- **Menu**  
- **VentanaJuego** 
- **Enums:** Dirección, TipoItem, Dificultad 

---

# Ficheros y Loggin

El juego:

- Guarda en fichero el log de acciones  
- Lee la configuración 
- Lanza excepciones si falla algo

---

# Como ejecutar el juego

1. Abrir eclipse
2. Importar el proyecto como existing java project
3. compilar
4. ejecutar Main.java
5. seguir las instrucciones del menu

---

# Autores

- Francisco Javier Gomes
- Miguel Peñaranda
- Pilar Escolar