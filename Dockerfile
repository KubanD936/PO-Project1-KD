FROM openjdk
#This is the environment we want the container to run in
COPY . /workspace
#This will copy everything in our project and put it into a folder called workspace
WORKDIR /workspace
#Tell it what folder to work out of
EXPOSE 8080
#Tell it what port to connect to
ENTRYPOINT [ "java", "-jar", "target/PO-Project1-KD-1.0-SNAPSHOT-jar-with-dependencies.jar" ]

#After finishing the project, run these commands
#docker build . -t po-project1-kd-api
#^This command creates an image in the docker, it might take a bit for the docker to build it the first time
#docker run -itd --name api_container -p 8888:8080 -e POSTGRES_SQL_DB=jdbc:postgresql://postgre_container:5432/postgres?currentSchema=project1 -e DB_USERNAME=postgres -e DB_PASSWORD=password --network api-network po-project1-kd-api
#Don't forget to check if it works with Postman