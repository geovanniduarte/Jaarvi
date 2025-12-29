## Índice

0. [Ficha del proyecto](#0-ficha-del-proyecto)
1. [Descripción general del producto](#1-descripción-general-del-producto)
2. [Arquitectura del sistema](#2-arquitectura-del-sistema)
3. [Modelo de datos](#3-modelo-de-datos)
4. [Especificación de la API](#4-especificación-de-la-api)
5. [Historias de usuario](#5-historias-de-usuario)
6. [Tickets de trabajo](#6-tickets-de-trabajo)
7. [Pull requests](#7-pull-requests)

---

## 0. Ficha del proyecto

### **0.1. Tu nombre completo:**

Geovanni Duarte Guerrero    

### **0.2. Nombre del proyecto:**

Jaarvi

### **0.3. Descripción breve del proyecto:**

App movil asistente de viajes

### **0.4. URL del proyecto:**

> Puede ser pública o privada, en cuyo caso deberás compartir los accesos de manera segura. Puedes enviarlos a [alvaro@lidr.co](mailto:alvaro@lidr.co) usando algún servicio como [onetimesecret](https://onetimesecret.com/).

### 0.5. URL o archivo comprimido del repositorio

> Puedes tenerlo alojado en público o en privado, en cuyo caso deberás compartir los accesos de manera segura. Puedes enviarlos a [alvaro@lidr.co](mailto:alvaro@lidr.co) usando algún servicio como [onetimesecret](https://onetimesecret.com/). También puedes compartir por correo un archivo zip con el contenido


---

## 1. Descripción general del producto

Es un asistente de viajes  con el conocimiento de haber viajado por todas las ciudades del mundo, conoce como se mueven y manejan estas ciudades y sitios turísticos, con ese conocimiento te ayuda a programar y gestionar tus viajes ya sea anticipadamente o en sitio,  minimizando costos e inconvenientes que ocurren en los viajes por desconocimiento de los viajeros y mejorándoles la experiencia  basado en sus gustos.


### **1.1. Objetivo:**

> Propósito del producto. Qué valor aporta, qué soluciona, y para quién.
Disminuir el desgaste que implica programar un viaje, ya sea anticipadamente o en el sitio que se desea turistear, además durante el transcurso del viaje debe proveer asistencia, en cuanto a informar, guiar, alertar y resolver ante el disfrute, necesidades o incidentes no esperados que surgen, promoviendo así que el tiempo y dinero invertido sean aprovechados al máximo en lo que pretende ser una experiencia divertida y memorable.

### **1.2. Características y funcionalidades principales:**

* En el sitio para turistear e iniciando el día, con todo el conocimiento de alguien que ya ha hecho turismo en este sitio, debe programar un itinerario de actividades,  adaptado a tus preferencias en cuanto a experiencias, gustos, presupuestos etc. Lo más importante además de conocer lugares turísticos, listarlos y armar una ruta, es conocer las dinámicas culturales y económicas del sitio turístico que cambian de un lugar a otro y usarlas a favor del viajero, ejemplo: En Turquía es mejor comprar el vuelo en globo en el sitio y negociar con las diferentes agencias, si llevas efectivo es mejor, y si es liras turcas incluso mejor,  por ende una actividad del itinerario puede ser “Negociar vuelo en globo para el día siguiente (se podría hacer virtual con jaarvi)”

* Si el viajero es  planeador, anticipadamente y basado en las ciudades que el usuario quiere visitar y cantidad de días, debe poder programar itinerarios para viajes mas largos, como por ejemplo viajes a europa los cuales implican agendar vuelos, trenes, buses, **hospedajes** y planes turísticos fácil y fluidamente, ofreciendo una recomendación inicial que puede ser modificada y ajustada después por el usuario si este no está de acuerdo con alguna  de las sugerencias el itinerario, este plan de viaje es basado en un pipleline de días, y por cada día debe programar las actividades a realizar

* Con base en la información de itinerario, contexto, ubicación, tiempos, costos y preferencias debe brindar información de ayuda que  le permita al viajero resolver problemas en sitio de forma eficiente y segura, dando pasos, indicaciones y consejos para resolverlo, como por ejemplo la mejor forma de transportarse desde el aeropuerto al hotel y permitirle al viajero armar una actividad.

* Cada día del viaje es un compendio de actividades, desde trasladarse del aeropuerto al hotel o recoger el auto de alquiler hasta ir a tomarse un café en un coffe shop famoso, jaarvi actúa como si fuera un viajero que ya hubiera hecho estas actividades antes y conoce problemas comunes, y la mejor forma de hacerlo,  la app debe permitir trazar la hoja de ruta de cada una de estas actividades usando:

    * Mapas para dar feedback en tiempo real de la ubicación del viajero en la ruta de la actividad.
    * Proveer indicaciones explicitas para el viajero en la ejecución de la ruta, como puntos de referencia, por ejemplo: “Deberías estar viendo una panadería llamada HORNITOS, cruza por esa calle a la derecha” ya que debe estar orientada a personas que pueden estar ejecutando esa ruta a pie.
    * Dar indicaciones que debe hacer en cada hito de la actividad como, por ejemplo: “Haz llegado a la maquina para comprar el ticket de transporte público en Gante, entra a la opción de cambiar idioma, selecciona español, y escoge el ticket de 3 días con recargo aeropuerto, dado que durarás 2 días y medio en esta ciudad, es la mejor opcion” (Las indicaciones y explicaciones en estas maquinas son muy difíciles de comprender)

* La app almacena documentación relévante para los viajeros, como tickets, permisos, documentos asociados a cada actividad del itinerario de viaje.

MONETIZACION

* Jaarvi es también un afiliado a los distintos comercios que venden productos y servicios del ambito turístico, como vuelos, toures o entradas a museos, trajes de baño y por ser redireccionados desde Jaarvi, jaarvi recibe comision por compra hecha.

### **1.3. Diseño y experiencia de usuario:**

> Proporciona imágenes y/o videotutorial mostrando la experiencia del usuario desde que aterriza en la aplicación, pasando por todas las funcionalidades principales.

### **1.4. Instrucciones de instalación:**
> Documenta de manera precisa las instrucciones para instalar y poner en marcha el proyecto en local (librerías, backend, frontend, servidor, base de datos, migraciones y semillas de datos, etc.)

---

## 2. Arquitectura del Sistema

### **2.1. Diagrama de arquitectura:**
> Usa el formato que consideres más adecuado para representar los componentes principales de la aplicación y las tecnologías utilizadas. Explica si sigue algún patrón predefinido, justifica por qué se ha elegido esta arquitectura, y destaca los beneficios principales que aportan al proyecto y justifican su uso, así como sacrificios o déficits que implica.


### **2.2. Descripción de componentes principales:**

> Describe los componentes más importantes, incluyendo la tecnología utilizada

### **2.3. Descripción de alto nivel del proyecto y estructura de ficheros**

> Representa la estructura del proyecto y explica brevemente el propósito de las carpetas principales, así como si obedece a algún patrón o arquitectura específica.

### **2.4. Infraestructura y despliegue**

> Detalla la infraestructura del proyecto, incluyendo un diagrama en el formato que creas conveniente, y explica el proceso de despliegue que se sigue

### **2.5. Seguridad**

> Enumera y describe las prácticas de seguridad principales que se han implementado en el proyecto, añadiendo ejemplos si procede

### **2.6. Tests**

> Describe brevemente algunos de los tests realizados

---

## 3. Modelo de Datos

### **3.1. Diagrama del modelo de datos:**

> Recomendamos usar mermaid para el modelo de datos, y utilizar todos los parámetros que permite la sintaxis para dar el máximo detalle, por ejemplo las claves primarias y foráneas.


### **3.2. Descripción de entidades principales:**

> Recuerda incluir el máximo detalle de cada entidad, como el nombre y tipo de cada atributo, descripción breve si procede, claves primarias y foráneas, relaciones y tipo de relación, restricciones (unique, not null…), etc.

---

## 4. Especificación de la API

> Si tu backend se comunica a través de API, describe los endpoints principales (máximo 3) en formato OpenAPI. Opcionalmente puedes añadir un ejemplo de petición y de respuesta para mayor claridad

---

## 5. Historias de Usuario

> Documenta 3 de las historias de usuario principales utilizadas durante el desarrollo, teniendo en cuenta las buenas prácticas de producto al respecto.

**Historia de Usuario 1**

**Historia de Usuario 2**

**Historia de Usuario 3**

---

## 6. Tickets de Trabajo

> Documenta 3 de los tickets de trabajo principales del desarrollo, uno de backend, uno de frontend, y uno de bases de datos. Da todo el detalle requerido para desarrollar la tarea de inicio a fin teniendo en cuenta las buenas prácticas al respecto. 

**Ticket 1**

**Ticket 2**

**Ticket 3**

---

## 7. Pull Requests

> Documenta 3 de las Pull Requests realizadas durante la ejecución del proyecto

**Pull Request 1**

**Pull Request 2**

**Pull Request 3**

