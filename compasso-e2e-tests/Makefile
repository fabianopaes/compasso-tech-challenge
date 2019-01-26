include help.mk

.PHONY: test
test: ##@development run the unit and integration test
	gradle test






.PHONY: lint
lint: ##lint code base
	docker run \
		--name crmin-lint \
		--rm \
		-v $(shell pwd):/go/src/gitlab.neoway.com.br/malibu/crmin \
		-w /go/src/gitlab.neoway.com.br/malibu/crmin \
		registry.neoway.com.br/vulgar/golinter:latest \
		make do-lint