include help.mk

.PHONY: clean-dev
clean-dev: ##@development clean the development environment
	rm -rf public;
	docker-compose down \
		--remove-orphans \
		--volumes

.PHONY: publish-documentation
publish-documentation: ##@development publish API documentation locally
	docker run \
		-e SWAGGER_JSON=/openapi/openapi.yaml \
		-p 7070:8080 \
		-v $(shell pwd):/openapi \
		--rm \
		swaggerapi/swagger-ui:v3.9.1

.PHONY: build
build: ## built the entire application
	cd compasso-rest-api && ${MAKE} build

.PHONY: run-local
run-local: build ## run the envirioment locally
	sudo docker-compose up 	--build

.PHONY: test
test: ##@development run the unit and integration test
	cd compasso-rest-api && ${MAKE} test

.PHONY: cover
cover: ##@development run the coverage report by jacoco
	gradle jacocoTestReport


.PHONY: test-acceptence
test-acceptence: build ## run te e2e tests to make sure the app is health
	docker-compose up --build -d;
	@echo 'Wainting the enviroment get up'
	sleep 15;
	cd compasso-e2e-tests && ${MAKE} test;
	docker-compose stop;




