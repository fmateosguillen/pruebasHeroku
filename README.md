# Real Estate (backend repository)
Este proyecto se basa en una API para una aplicación de compra, venta y alquiler de viviendas.

### Funcionalidades

La app ***Real Estate*** cuenta con varias funcionalidades dependiendo del tipo de usuario que seas:

- **En usuarios sin *loguear***

1. Al acceder a la aplicación se te mostrará un listado de viviendas.
2. Podrás filtrar el listado de las viviendas dependiendo de la ***Ciudad***, ***Categoría***, y ***Precio***.
3. También podrás pulsar sobre una vivienda del listado para mostrar la información completa de esa vivienda.
4. Evidentemente también podrás loguearte para acceder a las funciones de los usuarios logueados y por ende acceder también al formulario de registro en caso de que no tengas una cuenta aún.

- **En usuarios *registrados***

1. Así como los usuarios sin registrar, al acceder a la aplicación también accedes al listado de viviendas. 
2. Puedes loguearte para acceder a la app como un usuario logueado y obtener los beneficios de estos (además de tener la posibilidad de hacer un *logout* en caso de que sea necesario).
3. Como un usuario logueado puedes  marcar y desmarcar una vivienda como favorita para agregarla a tu listado personal de viviendas favoritas.
4. Puedes añadir un nuevo inmueble a la app a través de un listado. Este nuevo inmueble se mostrará posteriormente en el listado.
5. Puedes ver el listado de los inmuebles de los que eres propietario (los que has añadido).
6. Puedes también editar la información de los inmuebles que hayas subido.
7. Para terminar también tienes la posibilidad de eliminar un inmueble que hayas subido.

### Metodologías

Para la realización de este proyecto hemos empleado la siguiente metodología:

- **JIRA**: Para la gestión de las tareas del proyecto.
- **SLACK**: Para la comunicación entre los integrantes del grupo.
- **GUITHUB**: Para almacenar el código del proyecto y gestionar el control de versiones.
- **GITFLOW**: Para incrementar la productividad a la hora del uso de Github y mejorar la calidad de la gestión del control de flujo de trabajo y versiones.

### Peticiones

Las peticiones que puedes realizar en esta API son las siguientes:

##### *Gestión de usuarios*
- `/auth/register` (POST) - Registrar un usuario

- `/auth/login` (POST) - Acceder como un usuario registrado

##### *Gestión de viviendas*

- `/viviendas` (POST) - Crear una nueva vivienda

- `/viviendas` (GET) - Obtener todas las viviendas

- `/viviendas?cat=v1&ciu=v2&pre=v3` (GET) - Obtener todas las viviendas según un flitro

- `/viviendas/{id}` (GET) - Obtener los datos de una vivienda

- `/viviendas/{id}` (PUT) - Modificar una vivienda

- `/viviendas/{id}` (DELETE) - Eliminar una vivienda

- `/viviendas/mine` (GET) - Obtener todas las viviendas pertenecientes al usuario logueado en ese momento

- `/viviendas/favs` (GET) - Obtener todas las viviendas que el usuario actual ha marcado como favorito

- `/viviendas/favs/{id}` (POST) - Añadir una vivienda al listado de viviendas favoritas del usuario actual

- `/viviendas/favs/{id}` (DELETE) - Eliminar una vivienda del listado de viviendas favoritas del usuario actual

##### *Subida de imágenes*

- `/viviendas/{id}/img` (POST) - Añadir una imagen a una vivienda que el usuario actual haya subido

- `/viviendas/{id}/img/{hash}` (DELETE) - Eliminar una foto de una vivienda


*Algunas de las anteriores peticiones reciben un 'token' de autenticación para poder funcionar correctamente.*

### Herramientas
- IntelliJ IDEA
- PostMan

### Instrucciones adicionales

Para que el proyecto funcione correctamente debes sustituir en todos los puntos del proyecto en los que se requiera, la IP que hay puesta por su IP local y también cambiarlo en el fichero `network_security_config.xml`.

### Integrantes

- Pablo Mancina Castro (Coordinador)
- Christian Payo Parra
- Antonio Joaquín Montero García
- Francisco Javier Mateos Guillén