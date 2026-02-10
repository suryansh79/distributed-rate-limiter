# Distributed Rate Limiter

A backend service built using Spring Boot that implements an in-memory sliding window rate limiting algorithm.

## Features
- Sliding window rate limiting
- Per-client request tracking
- Configurable limits
- REST API endpoint

## Tech Stack
- Java
- Spring Boot
- Maven

## API Endpoint
GET /check?clientId={id}

## How it works
- Tracks request timestamps per client
- Removes expired timestamps
- Blocks requests exceeding the limit

## Future Enhancements
- Redis-based distributed rate limiting
- Dockerization
- CI/CD pipeline
