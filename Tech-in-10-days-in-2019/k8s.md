#### Kubernetes with Minikube

Followed - https://medium.com/free-code-camp/learn-kubernetes-in-under-3-hours-a-detailed-guide-to-orchestrating-containers-114ff420e882

## App Setup
* Default Nginx html path on mac - `/usr/local/Cellar/nginx/1.17.3_1/html`
    - change the port in config `/usr/local/etc/nginx/nginx.conf` to avoid conflict with Spring-boot
* Run spring-app with `mvn spring-boot:run -Dsa.logic.api.url=http://localhost:5000`

* Build images
```sh
> export DOCKER_USER_ID=rajsaxena
> docker build -t $DOCKER_USER_ID/sentiment-analysis-frontend .
> docker build -t $DOCKER_USER_ID/sentiment-analysis-web-app .
> docker build -t $DOCKER_USER_ID/sentiment-analysis-logic .
```

* Get dockerIP by inspecting a container `docker inspect <id-of-python-container> | jq '.[0] .NetworkSettings.IPAddress'`

* Check analyzer works
`curl -X POST "http://localhost:5050/analyse/sentiment" -H "Content-Type: application/json" -d '{ "sentence": "Love thy neighbour" }'`

## K8s
> Kubernetes is a Container Orchestrator, that abstracts the underlying infrastructure. (Where the containers are run).

* Install
```
> brew cask install minikube 
> minikube start --vm-driver=hyperkit
```

## Concepts 
**Pods** can be composed of one or even a group of containers that share the same execution environment. For cases when for e.g. two containers need to share volumes, or they communicate with each other using inter-process communication or are otherwise tightly coupled, then that’s made possible using Pods. 
Pods can have multiple containers _but they share port space and can use localhost_.
```yaml
apiVersion: v1
kind: Pod                                            
metadata:
  name: sa-frontend                                  
spec:                                                
  containers:
    - image: rajsaxena/sentiment-analysis-frontend 
      name: sa-frontend                              
      ports:
        - containerPort: 80       
```
**Note:** You shouldn't be creating pods manually. Use `deployments` instead.
___
The Kubernetes **Service** resource acts as the entry point to a set of pods that provide the same functional service. Use **labels** to mark pods that the service should target and how to select between them. Eg: LoadBalancer
```yaml
apiVersion: v1
kind: Service              
metadata:
  name: sa-frontend-lb
spec:
  type: LoadBalancer       
  ports:
  - port: 80               
    protocol: TCP          
    targetPort: 80         
  selector:                
    app: sa-frontend  
```

Get service with `kubectl get svc` and hit it with `minikube service sa-frontend-lb`

**Note:** default type of a service is `ClusterIP` (as seen with `kubectl get svc`).
___
**Deployments** are used to create the required number of pods for a service, have zero downtime deployment and other details.
```yaml
apiVersion: apps/v1
kind: Deployment                                          
metadata:
  name: sa-frontend
spec:
  selector:                                               
    matchLabels:
      app: sa-frontend                                    
  replicas: 2                                             
  minReadySeconds: 15
  strategy:
    type: RollingUpdate                                   
    rollingUpdate: 
      maxUnavailable: 1     # (replicas - this) number of pods should stay.
      maxSurge: 1           # Number of additional pods when rolling out new version.
  template:                 # Pod template that the Deployment will use.
    metadata:
      labels:
        app: sa-frontend                                  
    spec:
      containers:
        - image: rajsaxena/sentiment-analysis-frontend
          imagePullPolicy: Always                         
          name: sa-frontend
          ports:
            - containerPort: 80
```
```sh
# Rollout and save
> kubectl apply -f sa-frontend-deployment-green.yaml --record

# Check status
> kubectl rollout status deployment sa-frontend

# check history
> kubectl rollout history deployment sa-frontend

# rollback
> kubectl rollout undo deployment sa-frontend --to-revision=1
```
____

Kubernetes has a special pod the **kube-dns**. It creates a DNS record for each created service.

____
