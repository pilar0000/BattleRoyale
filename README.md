# Battle Royale - Proyecto final POO

Proyecto final para la asignatura **Programación orientada a objetos** en Java.

El juego simula un todos contra todos entre jugadores que utilizan personajes, herramientas y habilidades especiales. Incluye herencia, polimorfismo, interfaces, ficheros y excepciones.

## Descripción del juego

Cada jugador participa en una partida donde:

- Elige un personaje. (guerrero, mago o arquero)
- Obtiene una herramienta. (espada, arco o báculo)
- Usa habilidades especiales que consumen maná
- Realiza ataques por turnos
- La partida sigue hasta que queda un ganador

Los jugadores pueden ser humanos o bots con distintos niveles de dificultad y la partida va generando un log de acciones que puede volcarse a un fichero.

## Personajes

##### 'Personaje' (abstracto)
Clase base con:
- vida
- ataque base
- defensa
- maná
- métodos comunes
- método abstracto 'habilidadEspecial()'

##### 'Guerrero'
Aumenta su ataque temporalmente

##### 'Mago'
Usa maná para curarse o lanzar ataques especiales.

##### 'Arquero'
Realiza disparos precisos

Incluye Override de:
- toString()
- equals()
- compareTo()

## Herramientas e interfaz modificable

#### 'Herramienta'
Clase base con:
- nombre
- bonusAtaque

#### Subclases:
- Espada
- Arco
- Báculo

## Interfaz
Incluye:

- boolean comprobarModificacion();
- void modifica();

Las herramientas pueden mejorar si se cumplen condiciones (vida, turnos, etc...)

## Jugador
Compuesto de:
- un personaje
- una herramienta
- si es humano o bot

Métodos clave:
- atacar(Jugador otro)
- estaVivo()
- toString()

## Partida
Gestiona:
- jugadores
- dificultad
- turnos
- ataques
- uso de habilidades
- condiciones de victoria

Incluye:
- IA según dificultad
- comprobación de recursos
- integración con Logger
- lanzamiento de excepciones

## Lectura y escritura de ficheros
Incluye:
- un sistema de log con las últimas 1000 acciones
- volcado a fichero de resultados y estadísticas
- gestión de errores mediante excepciones

## Excepciones
- RecursoInsuficienteException
- FicheroLecturaException
- FicheroEscrituraException

Se lanza en situaciones como:
- no hay suficientes personajes disponibles
- no se puede crear o cargar el fichero
- error al escribir log

## Menú interactivo
El usuario puede:
- Iniciar partida
- Ver personajes disponibles
- Ver herramientas disponibles
- Mostrar log
- Guardar los resultados en fichero
- Salir

## IA del juego
- Fácil: ataques aleatorios
- Normal: ataca al jugador más débil
- Difícil: usa habilidades y elige objetivos

## Como ejecutar el proyecto
1. abrir en eclipse
2. ejecutar main.java
3. seguir las instrucciones del menú