# Solitario Klondike en Java

Proyecto final de **Estructuras de Datos** – Universidad Autónoma del Cauca.

Juego de Solitario Klondike para la consola, hecho en Java y construido solo con estructuras de datos programadas desde cero (sin `ArrayList`, `Stack`, `LinkedList` ni otras colecciones de Java).

## Autor

- Brayan Estiven Chantre Sánchez
- Docente: Ana María Caviedes Castillo

## Requisitos

- JDK 27
- Terminal con soporte UTF-8 (para ver ♠ ♥ ♦ ♣)

## Cómo ejecutarlo

**Desde IntelliJ IDEA**
1. Abrir la carpeta del proyecto.
2. Verificar que el SDK sea JDK 27.
3. Ejecutar `Main.java` (Shift + F10).

**Desde la consola**
```
cd src
javac *.java
java Main
```

## Cómo se juega

Menú principal: 1 Nueva partida, 2 Mejores puntajes, 3 Reglas, 4 Salir.

Durante la partida:

| N.º | Acción |
|-----|--------|
| 1 | Robar del mazo |
| 2 | Mover entre columnas |
| 3 | Mover una carta a la fundación |
| 4 | Mover la carta del descarte a una columna |
| 5 | Mover la carta del descarte a la fundación |
| 6 | Deshacer la última jugada |
| 7 | Abandonar la partida |

Cada carta que llega a una fundación suma 10 puntos. Al terminar, el puntaje se guarda en el ranking (mientras el programa siga abierto).

## Estructuras de datos usadas

| Estructura | Dónde se usa |
|------------|--------------|
| Pila (`Pila<T>`) | Fundaciones, historial de movimientos (deshacer) |
| Cola (`Cola<T>`) | Mazo |
| Lista circular (`ListaCircular<T>`) | Descarte |
| Lista doblemente enlazada (`ListaDoble<T>`) | Las 7 columnas del tablero |
| Árbol binario de búsqueda (`ArbolBST<T>`) | Ranking de mejores puntajes |

## Organización del código

- **Estructuras:** `Nodo`, `Pila`, `Cola`, `ListaCircular`, `NodoDoble`, `ListaDoble`, `NodoArbol`, `ArbolBST`
- **Dominio:** `Palo`, `Carta`, `Baraja`, `Puntaje`, `Movimiento`, `Solitario`
- **Interfaz:** `Entrada`, `Main`

El manual de usuario, el manual técnico, el diagrama de clases y las tablas de complejidad están en el PDF de entrega.

## Limitaciones

- "Perder" equivale a abandonar la partida; no se detecta automáticamente que no haya jugadas.
- El ranking no se guarda en archivo.
- El árbol no está balanceado (peor caso O(n)).