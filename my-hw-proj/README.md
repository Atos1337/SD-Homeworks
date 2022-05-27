# Execution 

### RabbitMQ Server
Run RabbitMq in the docker container:
```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
```

### RabbitMQ Worker
Now you need to run RabbitMQ worker that checks solutions:
```bash
pip install -r my-hw-proj-workers/requirements.txt 
python my-hw-proj-workers/main.py 
```

### Spring Server
Run Spring Applicatino inside `/my-hw-proj-web` directory using IDEA💁 or run from `my-hw-proj-web` directory
```shell
./gradlew bootRun
```

### usage
```http://localhost:8080/student/home```  

```http://localhost:8080/teacher/home```
