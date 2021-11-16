# Feather Writer
Web-based Novel Writing Software

## Documentation (RO):
https://docs.google.com/document/d/1nxLXlA9yAB5z6LwPahIjBocP20p49MnoQWEqOpRBa3c/edit?usp=sharing

![Diagram](Diagram.png)

## Google Cloud Kubernetes Quickstart

https://cloud.google.com/kubernetes-engine/docs/quickstart

## Running Docker locally

The port exposed in Dockerfile is 8081, so after you find the container image, you can build and run the container like so:
1. cd into Dockerfile folder
2. 

```
docker build . -t featherwriter
docker run -p 80:8081 featherwriter -v FeatherWriter
```

and access the image via container-ip:80
