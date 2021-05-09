# docker-pipeline



Setup Docker:

1. created an ec2 instance with amazon linux
2. install docker-ce
	a. sudo amazon-linux-extras install docker
	b. sudo service docker start
	c. sudo usermod -a -G docker ec2-user
3. make docker autostart
	a. sudo chkconfig docker on
	b. sudo yum install -y git
	c. sudo reboot
4. docker-compose binary download
	sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
5. set proper permission
	sudo chmod +x /usr/local/bin/docker-compose
6. verify installation
	docker-compose version


Starting a docker container:

1. mkdir srcpostgres
2. cd srcpostgres
3. vi docker-compose.yml:

version: "3.9"
services:
  db:
    image: postgres
    ports:
      - '5433:5432'
    environment:
      - POSTGRES_DB=src_postgres
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres

4. run docker-compose cmd from the same folder where docker-compose.yml 
	docker-compose up 

5. to check docker process
	docker ps

	(to stop a container: docker stop 'container_id')

6. to connect to postgres running in docker
	docker exec -it 'container_id' psql -U postgres src_postgres


repeat steps 1-5 for target docker container with necessary modifications
1. to check docker process
	docker ps
