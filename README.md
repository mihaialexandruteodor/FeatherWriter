# Feather Writer
Cloud Native Novel Writing Software

<img src="https://raw.githubusercontent.com/mihaialexandruteodor/mihaialexandruteodor/master/repoImages/FeatherWriter/1.PNG" width="800">

<img src="https://raw.githubusercontent.com/mihaialexandruteodor/mihaialexandruteodor/master/repoImages/FeatherWriter/2.PNG" width="800">

<img src="https://raw.githubusercontent.com/mihaialexandruteodor/mihaialexandruteodor/master/repoImages/FeatherWriter/3.PNG" width="800">

## Documentation (RO):
https://docs.google.com/document/d/1nxLXlA9yAB5z6LwPahIjBocP20p49MnoQWEqOpRBa3c/edit?usp=sharing

## Docker Repo:
https://hub.docker.com/repository/docker/mihaialexandruteodor/featherwriter

## Diagram:
<img src="https://raw.githubusercontent.com/mihaialexandruteodor/mihaialexandruteodor/master/repoImages/FeatherWriter/Diagram_FW.png" width="800">

## Deployment in Google Cloud

Simply clone the docker image
```
docker pull mihaialexandruteodor/featherwriter:latest
```
then run
```
kubectl apply -f mysqldb-service.yaml,web-app-service.yaml,mysqldb-deployment.yaml,web-app-deployment.yaml,web-app-claim0-persistentvolumeclaim.yaml
```

to create the deployments and services


## Accesing the app
The Feather Writer app requires the .traefik.me magic domain to bypass Google OAuth, unless you want to edit your own domain alias to the container's IP in your local machine's hosts file, or you don't want to run Feather Writer in Docker.

The url format is
```
<ip>.traefik.me:port
```

## Google Cloud Kubernetes Quickstart:

https://www.youtube.com/watch?v=jSYxW_c3M_E

https://cloud.google.com/kubernetes-engine/docs/quickstart

## Building the Docker application with the MySQL backend:

Navigate to the location of the docker-compose.yaml file and run

```
docker-compose up
```

or

```
 docker-compose up --force-recreate --build 
```


