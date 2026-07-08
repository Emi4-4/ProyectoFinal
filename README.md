# Proyecto Final - Sistema de Gestión de Tienda de Mascotas

**Número de grupo:** 11

Integrantes:

Emiliano León Allen Robles

Lenin Stevee Diaz Cabezo

Valentina Alejandra Serón Canales

## Enunciado

Este simulador ofrecerá al usuario la experiencia de gestionar su propia tienda de mascotas virtual. El jugador comenzará con un presupuesto inicial para adquirir diferentes tipos de mascotas (ej. perros, gatos, peces, pájaros), cada una con atributos y necesidades específicas como alimentación (tipo de comida, frecuencia), higiene, nivel de felicidad, tipo de recinto y salud. El usuario deberá gestionar el inventario de mascotas y de suministros (comida, medicinas). Las interacciones incluirán alimentar a las mascotas, limpiar sus hábitats, jugar con ellas para mantener su felicidad, y atender su salud. El estado de cada mascota deberá ser visible y cambiará según los cuidados recibidos. El sistema también simulará la llegada de clientes virtuales interesados en adoptar mascotas, permitiendo al jugador venderlas y así obtener ingresos para continuar gestionando y expandiendo la tienda.

## Patrones de Diseño Implementados

* **Patrón Strategy (Estrategia):**
  * **Justificación:** Se implementó para encapsular las diferentes acciones que pueden aplicarse a las mascotas. En lugar de usar múltiples sentencias condicionales, delegamos esta lógica a clases específicas. Esto respeta el principio Abierto/Cerrado (Open/Closed), ya que permite agregar nuevas actividades en el futuro creando nuevas clases sin modificar la clase `Mascotas`.
  * **Clases utilizadas:** `Actividad` (Interfaz), `Alimentar`, `Curar`, `Jugar`, `Limpiar`.

* **Patrón Observer (Observador):**
  * **Justificación:** Es una parte esencial de la interfaz grafica. Separa la lógica de negocio de la interfaz de usuario. Cuando los atributos de una mascota cambian, el objeto notifica automáticamente a los paneles observadores para que se actualicen en tiempo real, sin que `Mascotas` deba importar librerías visuales.
  * **Clases utilizadas:** `Mascotas` (Sujeto), `MascotaObserver` (Interfaz), `PanelMascota` (Observador concreto).

* **Patrón Singleton (Instancia Única):**
  * **Justificación:** Se utilizó para garantizar que exista una única instancia global de la clase `Proveedor`. Al ser la entidad centralizada que genera y abastece de recursos a la tienda, permitir múltiples instancias podría generar inconsistencias en el manejo del inventario.
  * **Clases utilizadas:** `Proveedor`.

* **Patrón Factory / Clase de Utilidad:**
  * **Justificación:** Se implementó para centralizar la creación y carga de los recursos visuales del sistema. Abstrae la complejidad de acceder al sistema de archivos e incluye un mecanismo de resiliencia que genera íconos automáticos (emojis) si una imagen no es encontrada.
  * **Clases utilizadas:** `IconLoader`.

 ## Decisiones Importantes y Mejoras a la Temática

Durante la fase de diseño y desarrollo, el equipo analizó los requerimientos del simulador (basado en la temática de Tienda de Mascotas Virtual) y tomó decisiones arquitectónicas para llevar el proyecto más allá de los requisitos mínimos, aportando robustez y una mejor experiencia de simulación:

* **Estandarización de Inventarios (Uso de Genéricos):**
  * *Requisito original:* Gestionar el inventario de mascotas y suministros por separado.
  * *Decisión del equipo:* Se implementó la clase genérica `Deposito<E>`. En lugar de tener listas separadas y métodos redundantes para guardar gatos, perros o comida, esta clase unifica la lógica de almacenamiento, permitiendo buscar, agregar y remover elementos de forma segura y reutilizable para cualquier tipo de objeto en el juego.

* **Gestión Dinámica de la Economía Inicial:**
  * *Requisito original:* El jugador comenzará con un presupuesto inicial.
  * *Decisión del equipo:* En lugar de asignar un valor estático en el código central, se diseñó una `VentanaInicio` interactiva. Esto transfiere el control al usuario, permitiéndole definir el nivel de dificultad (presupuesto inicial) antes de instanciar la `Tienda` y desplegar la `VentanaPrincipal`.

* **Automatización de la Interfaz Visual (Reactividad):**
  * *Requisito original:* El estado de cada mascota deberá ser visible y cambiará según los cuidados.
  * *Decisión del equipo:* Para evitar cuellos de botella actualizando manualmente los textos de la interfaz después de cada acción, se implementó el patrón *Observer*. La clase `PanelMascota` se suscribe a los cambios de la mascota que representa; así, cuando una acción como `Jugar` altera los niveles de felicidad e higiene, la interfaz se repinta de manera autónoma.

* **Resiliencia Visual del Simulador:**
  * *Requisito original:* Proporcionar una experiencia de tienda virtual con un entorno gráfico.
  * *Decisión del equipo:* Tratándose de un simulador donde la identificación visual de las especies es clave, se desarrolló la clase `IconLoader`. Se decidió que, si por problemas de rutas de sistema operativo un archivo PNG no logra cargarse, el programa no debe fallar. En su lugar, el sistema intercepta el error y dibuja un ícono vectorizado de respaldo (un emoji representativo) programáticamente, garantizando la continuidad de la experiencia.

* **Simulación de Mercado Aleatoria:**
  * *Requisito original:* Simular la llegada de clientes virtuales interesados en adoptar.
  * *Decisión del equipo:* Para añadir realismo e incertidumbre al simulador, se configuró a los clientes con una generación procedural de características. Al instanciar un `Cliente` desde la `Tienda`, se le asigna un nombre de un conjunto predefinido y un presupuesto calculado aleatoriamente. Esto obliga al jugador a evaluar estratégicamente si la oferta del cliente es viable económicamente para el progreso de la tienda.
 
## Problemas Identificados y Autocrítica

### Problemas Identificados durante el Desarrollo
* **Acoplamiento entre Lógica e Interfaz:** En las primeras iteraciones, la actualización visual dependía de llamadas directas desde los métodos de acción (ej. al alimentar a una mascota, se debía ordenar a la ventana que se refrescara). Esto generaba un alto nivel de acoplamiento y un código difícil de mantener. La solución definitiva fue la implementación del patrón **Observer**, delegando la responsabilidad visual a `PanelMascota`.
* **Manejo de Estados Nulos y Excepciones Lógicas:** Al centralizar el inventario en la clase genérica `Deposito<E>`, surgieron vulnerabilidades lógicas, como intentar extraer productos agotados o vender mascotas inexistentes, lo que derivaba en `NullPointerException`. Se resolvió estructurando un sistema de excepciones personalizadas (`StockInsuficienteException`, `MascotaNoEncontradaException`) para mantener la estabilidad del programa.
* **Flujo de Eventos en Java Swing:** Tuvimos dificultades iniciales al gestionar múltiples eventos simultáneos (como la actualización de estado de mascotas y la interacción de clientes). Si las validaciones lógicas eran muy pesadas, la interfaz gráfica (UI) sufría de pequeños bloqueos o parpadeos por saturación del hilo principal.

### Autocrítica sobre la Gestión del Proyecto
* **Subestimación del Diseño de Interfaces:** Como equipo, subestimamos el tiempo requerido para diseñar y maquetar correctamente una interfaz gráfica en Swing utilizando diferentes `LayoutManagers`. Invertimos la mayor parte del tiempo inicial perfeccionando la lógica en consola, lo que nos obligó a acelerar el desarrollo visual en las etapas finales del proyecto.
* **Estrategia de Pruebas Tardías (Testing):** Cometimos el error de retrasar las pruebas de integración hasta tener la gran mayoría de las clases terminadas. Esto provocó que errores de diseño (como las dependencias iniciales entre `Tienda` y `Proveedor`) fueran más difíciles de rastrear y corregir. Aprendimos que probar cada módulo o clase (`Deposito`, `Cliente`, etc.) de forma aislada inmediatamente después de programarlo ahorra mucho tiempo de depuración.
* **Organización y División de Tareas:** Aunque el resultado final es funcional, la división del trabajo inicial no fue óptima al principio. Al inicio, varios integrantes trabajaban sobre las mismas clases principales (`VentanaPrincipal` o `Tienda`), lo que generó cuellos de botella y conflictos de integración en el código al comiezo. Esto nos dejó como lección la importancia de dividir las tareas por dominios o capas (ej. una persona encargada exclusivamente de la lógica del Modelo y otra de las Vistas) mas adelante en el codigo.
