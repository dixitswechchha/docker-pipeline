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


Setup Airflow:

1. mkdir pipeline
2. cd pipeline

3. Fetching docker-compose.yml
	curl -LfO 'https://airflow.apache.org/docs/apache-airflow/2.0.2/docker-compose.yaml'

4. Prepare the environment with necessary files, directories and initialize the database
	mkdir ./dags ./logs ./plugins
	echo -e "AIRFLOW_UID=$(id -u)\nAIRFLOW_GID=0" > .env

5. Run database migration and create first user account
	docker-compose up airflow-init

	The account created has the login airflow and the password airflow

6. Start all the services
	docker-compose up
	
Running the pipeline

1. create a jar file for java code
2. docker exec -it <container id> bash (where airflow is running)
3. cd /usr/local/airflow/dags
4. cat > d_migration.py
5. chmod 777 d_migration.py
