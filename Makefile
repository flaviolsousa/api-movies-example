test:
	mvn clean test
build:
	mvn clean package -Dmaven.test.skip=true
run-dependencies:
	docker-compose -f docker/docker-compose.dependencies.yml up
run:
	docker-compose -f docker/docker-compose.yml up
clean:
	docker-compose -f docker/docker-compose.yml down -v --rmi all --remove-orphans
