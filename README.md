# Historial de navegación WebBrowser

**Proyecto correspondiente al Examen Parcial de la asignatura Estructura de Datos.**

## Participantes

- Laura García Miguel
- Anyelo Monterrey
- Carlos Daniel Hernández Díaz
- Alejandro Martínez González
- Reynaldo A. Ramos Alvarez

## Descripción del problema

El proyecto administra el historial de páginas visitadas en un navegador. De cada visita se guarda la URL, el título de la página y la hora. La estructura principal es una lista doblemente enlazada implementada en Java.

## Solución

`PaginaWeb` representa cada visita y guarda la hora como segundos transcurridos desde la medianoche. La hora se ingresa y muestra con el formato estricto `HH:mm:ss`.

`LinkedList<T>` implementa una lista doblemente enlazada genérica. Cada `Node<T>` mantiene referencias al nodo anterior y al siguiente; la lista administra los extremos `head` y `tail`.

`HistorialNavegacion` administra las páginas y ofrece estas operaciones:

- Registrar una visita, insertándola para mantener el historial ordenado de mayor a menor cantidad de segundos desde la medianoche.
- Mostrar las visitas desde la hora más alta hasta la más baja.
- Buscar por una palabra contenida en el título, sin distinguir mayúsculas y minúsculas. Los resultados contienen las URL coincidentes y conservan el orden del historial.
- Eliminar una visita por URL. Si la URL aparece varias veces, elimina la primera que encuentra en el orden del historial.
- Vaciar las visitas estrictamente anteriores a una hora límite y devolver la cantidad eliminada. Las visitas exactamente a la hora límite se conservan.

La comparación usa solo la hora del día, no una fecha. Por ello, el orden representa los segundos desde la medianoche y no puede determinar la recencia real entre visitas de días distintos que cruzan la medianoche.

## Estructura del proyecto

```text
src/
├── app/
│   └── Main.java
├── estructura/
│   ├── IList.java
│   ├── LinkedList.java
│   └── Node.java
├── historial/
│   └── HistorialNavegacion.java
├── modelo/
│   └── PaginaWeb.java
└── pruebas/
    └── PruebasProyecto.java
```

## Requisitos

- JDK 8 o posterior.
- Consola o terminal.

## Compilar y ejecutar

Desde la carpeta raíz del repositorio, crea el directorio de salida y compila la aplicación y las pruebas. El compilador encontrará las demás clases usando `src` como ruta de fuentes.

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -sourcepath src -d out src/app/Main.java src/pruebas/PruebasProyecto.java
java -cp out app.Main
```

### Linux o macOS

```sh
mkdir -p out
javac -encoding UTF-8 -sourcepath src -d out src/app/Main.java src/pruebas/PruebasProyecto.java
java -cp out app.Main
```

El programa presenta un menú para registrar visitas, mostrar el historial reciente, buscar por palabra, eliminar por URL, vaciar visitas anteriores a una hora y cargar datos de demostración.

## Pruebas

Para ejecutar las pruebas:

```sh
java -cp out pruebas.PruebasProyecto
```

La clase `PruebasProyecto` cubre conversiones y validaciones de hora, inserciones y eliminaciones en distintos índices, comportamiento de los enlaces, orden del historial, búsquedas, URL repetidas, vaciado por hora, lista vacía e índices e entradas inválidos. En la última ejecución, las **69 pruebas pasaron**.

## Presentación

Pendiente de agregar. Cuando esté disponible, se incluirá aquí el enlace al PDF o a las diapositivas.

## Uso de inteligencia artificial

ChatGPT apoyó la organización y pruebas del proyecto.
