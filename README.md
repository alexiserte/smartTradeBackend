<h1 align="center">
  <br>
  <img src="https://pbs.twimg.com/media/FQy68dqXwAkRjmK.jpg:large" alt="smartTrade" width="200">
  <br>
  smartTrade by FiveGuysUPV 🇪🇸 5️⃣👨‍👦‍👦
  <br>
</h1>

<h4 align="center">Parte de backend para el proyecto de aplicación de smartTradeUPV.</h4>

<p align="center">
  <a href="#descargar-el-repositorio">Descargar el repositorio</a> •
  <a href="#cosas-a-cambiar-o-a-implementar">Cosas a cambiar o a implementar</a> •
</p>

## Descargar el repositorio

```bash
# Clone this repository
$ git clone [https://github.com/amitmerchant1990/electron-markdownify](https://github.com/alexiserte/smartTradeBackend.git)

# Go into the repository
$ cd smartTradeBackend

# Run the project
$ mvn spring-boot:run
```

## Cosas a cambiar o a implementar

• Preparar consultas a la base de datos para cuando implementemos los métodos ya tenerlos preparados
• Decidir cual de los modelos creados vamos a usarlos y por tanto, crear sus DAOs, mappers y controladores
• Decidir que se hace con el patrón fábrica
• Tener claro que peticiones se van a hacer desde el front para tener claro que tipo de consultas debemos de soportar
• Actualizar el método DELETE de Comprador
• IMÁGENES EN LA BASE DE DATOS
• Reiniciar la máquina virtual y volver a ejecutar el siguiente comando cada vez que se haga un cambio:
```bash
$ cd smartTradeBackend
$ nohup mvn spring-boot:run
```
• Actualizar todos los archivos de modelos en base a cambios en la base de datos (SI HACE FALTA crear nuevos)
