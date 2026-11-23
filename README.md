# Calculadora — Control de Versiones

## Descripción

Proyecto desarrollado con IntelliJ IDEA utilizando Java para el backend y HTML para el frontend.

Se utiliza Git para controlar las versiones y GitHub para publicar el repositorio.

## Historial de desarrollo

El siguiente historial está ordenado cronológicamente, desde el primer commit hasta el más reciente.

| Commit | Descripción |
|---|---|
| `beb732b` | Creación inicial del repositorio. |
| `cb550b2` | Creación del frontend de la calculadora en HTML. |
| `a7ce54a` | Eliminación de la carpeta duplicada `src/main`. |
| `e4382c0` | Fusión del frontend con main. |
| `0ab32ef` | Creación de la clase Java `Ca.java`. |
| `d3bec89` | Fusión del backend con main. |
| `19f9963` | Corrección de la ruta del frontend para comunicar HTML con Java. |
| `7ca51c0` | Incorporación de una sección para mostrar el resultado en la misma página. |
| `5386bdd` | Nueva fusión del frontend con main. |
| `30e3c0a` | Actualización de la funcionalidad del backend para mostrar el resultado en el navegador. |
| `05a21c0` | Nueva fusión del backend con main. |

## Comandos Git y explicación

### Inicialización

`git init -b main`

Inicializa el repositorio Git y establece main como rama principal.

`git add .gitignore`

Añade el archivo .gitignore al área de preparación.

`git commit -m "first commit"`

Guarda los cambios iniciales del proyecto.

### Creación de ramas

`git branch frontend`

Crea la rama para desarrollar la interfaz HTML.

`git branch backend`

Crea la rama para desarrollar el código Java.

### Trabajo en las ramas

`git switch frontend`

Permite cambiar a la rama frontend.

`git add frontend/index.html`

Prepara el archivo HTML para su commit, suponiendo que esta sea su ubicación.

`git commit -m "feature: created a simple frontend for the basic calculator in index.html"`

Guarda la creación del frontend.

`git switch backend`

Permite cambiar a la rama backend.

`git add src/Ca.java`

Prepara el archivo Java para su commit.

`git commit -m "feat: created Ca.java class for the backend"`

Guarda la implementación del backend.

### Fusiones

`git switch main`

Cambia a la rama principal del proyecto.

`git merge frontend`

Integra los cambios del frontend en la rama principal.

`git merge backend`

Integra los cambios del backend en la rama principal.

### Comprobación del historial

`git log --all --oneline --graph`

Muestra el historial gráfico de todos los commits y ramas.

## Publicación del repositorio en GitHub

1. Crear un repositorio público llamado `calculadora` en GitHub.
2. Copiar la URL del repositorio.
3. Conectar Git local con GitHub:

   `git remote add origin https://github.com/USUARIO/calculadora.git`

4. Subir la rama principal:

   `git push -u origin main`

5. Subir las otras dos ramas:

   `git push -u origin frontend`

   `git push -u origin backend`

6. Comprobar que las tres ramas están disponibles públicamente.

## Conclusión

Con esta actividad se ha practicado el control de versiones mediante Git, la creación y utilización de ramas, el registro de cambios mediante commits y la integración de las diferentes partes de un proyecto mediante merges.

Además, se ha aprendido a publicar un repositorio en GitHub y a consultar su historial de desarrollo.