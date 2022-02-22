.PHONY: build help

help:
	@grep -E '^[a-zA-Z0-9_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

install: package.json ## install dependencies
	@if [ "$(CI)" != "true" ]; then \
		echo "Full install..."; \
		yarn; \
	fi
	@if [ "$(CI)" = "true" ]; then \
		echo "Frozen install..."; \
		yarn --frozen-lockfile; \
	fi

run: run-frontend

run-frontend: 
	@yarn -s run-frontend

run-frontend-watch: 
	@yarn -s run-frontend-watch

build-frontend: 
	@yarn -s build-frontend

build-frontend-data-generator:
	@echo "Transpiling data-generator files...";
	@cd ./frontend/data-generator && yarn -s build

build: build-frontend build-frontend-data-generator

lint: ## lint the code and check coding conventions
	@echo "Running linter..."
	@yarn -s lint

prettier: ## prettify the source code using prettier
	@echo "Running prettier..."
	@yarn -s prettier

test: build test-unit lint test-e2e ## launch all tests

test-unit: ## launch unit tests
	@if [ "$(CI)" != "true" ]; then \
		echo "Running unit tests..."; \
		yarn -s test-unit; \
	fi
	@if [ "$(CI)" = "true" ]; then \
		echo "Running unit tests in CI..."; \
		yarn -s test-unit-ci; \
	fi

test-unit-watch: ## launch unit tests and watch for changes
	echo "Running unit tests..."; \
	yarn -s test-unit --watch; \

test-e2e: ## launch end-to-end tests (ex. BROWSER=firefox make test-e2e)
	@if [ "$(build)" != "false" ]; then \
		echo 'Building example code (call "make build=false test-e2e" to skip the build)...'; \
		cd examples/simple && BABEL_ENV=cjs yarn -s build; \
	fi

	@NODE_ENV=test cd cypress && yarn -s test


test-e2e-local: ## launch end-to-end tests for development
	@echo 'Starting e2e tests environment. Ensure you started the simple example first (make run-simple)'
	@cd cypress && yarn -s start
