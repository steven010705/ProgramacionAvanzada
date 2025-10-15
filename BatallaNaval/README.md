# Batalla Naval
***
## Integrantes
Juan Camilo Soto Martínez - 20231020111

Steven Alberto Niño Rivera - 20231020209
***
## Diagrama de Clases y Casos de Uso
[BatallaNaval.drawio](https://drive.google.com/file/d/1ORyuyzQe1-W5KCIlmCWIxOnh_Pghttyt/view?usp=sharing)
***
## Manual de Usuario
El juego Batalla Naval puede ejecutarse fácilmente a través del archivo **`BatallaNaval.exe`**, sin necesidad de usar comandos ni abrir la consola.  
Este ejecutable fue creado especialmente para facilitar la ejecución del programa en equipos con sistema operativo Windows.

### Ejecución del programa

1. Asegúrese de tener instalado **Java Runtime Environment (JRE)** o **Java Development Kit (JDK)**.  
2. Localice el archivo **`BatallaNaval.exe`** dentro de la carpeta del proyecto.  
3. Haga **doble clic** sobre el archivo para iniciar el juego.  
4. La ventana principal del programa se abrirá automáticamente y podrá comenzar a jugar.

***

### ¿Por qué es posible ejecutar el programa solo con el `.exe`?

El funcionamiento del archivo **`BatallaNaval.exe`** se logra gracias a la combinación de tres componentes clave:

1. **`BatallaNaval.jar`**
   - Contiene todas las clases compiladas del juego y los recursos necesarios para su ejecución.  
   - Fue generado a partir del código fuente mediante el comando:
     ```bash
     jar cfm BatallaNaval.jar MANIFEST.MF -C bin .
     ```

2. **`MANIFEST.MF`**  
   - Define la clase principal del proyecto, permitiendo que el `.jar` sea **ejecutable por sí mismo**.  
   - Su contenido indica a Java cuál clase iniciar:
     ```
     Main-Class: co.edu.udistrital.controlador.Main
     ```

3. **`config.xml`**  
   - Es el archivo de configuración utilizado por Launch4j para convertir el `.jar` en un archivo **`.exe`** de Windows.  
   - Gracias a este archivo, el ejecutable sabe qué JAR debe ejecutar, cómo debe comportarse la interfaz y dónde buscar los recursos.  
   - Ejemplo de la estructura:
     ```xml
     <launch4jConfig>
       <jar>BatallaNaval.jar</jar>
       <outfile>BatallaNaval.exe</outfile>
       <headerType>gui</headerType>
       <errTitle>Batalla Naval</errTitle>
     </launch4jConfig>
     ```

En conjunto:
- El `MANIFEST.MF` indica qué clase ejecutar dentro del JAR.  
- El `BatallaNaval.jar` contiene toda la lógica y recursos del juego.  
- El `config.xml` permite que Launch4j genere un `.exe` que se comporta igual que el `.jar`, pero puede abrirse directamente en Windows.  

De esta forma, el usuario final solo necesita hacer doble clic en el archivo **`BatallaNaval.exe`** para iniciar el juego, sin preocuparse por rutas, comandos o configuraciones de Java.