# Job Portal

Job Portal is a microservices-based application designed to manage **job listings**, **company profiles**, and **user reviews**. Built with **Spring Boot**, **Docker**, **Kubernetes**, **PostgreSQL**, and other modern technologies, it allows users to browse job listings, review companies, and explore different companies. The system follows a **microservices architecture**, with services like **Job Microservice**, **Review Microservice**, **Company Microservice**, **API Gateway**, and more.

## Architecture

The system is composed of the following main components:

### Microservices:
- **JobMS**: Manages job listings and job-related operations. Companies can post jobs, and users can browse and view available job listings.
- **ReviewMS**: Allows users to leave reviews for companies they have interacted with or worked for. Reviews can provide ratings and textual feedback about a company’s culture, work environment, and more.
- **CompanyMS**: Manages company profiles, including the company’s name, location, description, and the jobs they are posting. Each company can have multiple job listings, and users can leave reviews for each company.

### Supporting Services:
- **PostgreSQL**: The relational database used to store all data for companies, jobs, and reviews.
- **Docker**: Containers each microservice to enable seamless deployment and portability.
- **Kubernetes**: Orchestrates and manages the containers for scalability in production environments.
- **API Gateway**: A single entry point that routes client requests to the appropriate microservices.
- **Config Server**: Centralized configuration management for all services.
- **Service Registry (Eureka)**: Enables dynamic service discovery and load balancing between microservices.
- **Zipkin**: For distributed tracing, allowing monitoring of request flows through the microservices.

## Features

- **Job Listings**: Companies can post job openings, and users can browse available job listings.
- **Company Reviews**: Users can leave reviews about companies they have interacted with or worked for. Reviews contain ratings and comments.
- **Browse Companies**: Users can explore company profiles, view job listings, and read reviews from other users.
- **Distributed Tracing**: Zipkin traces the flow of requests across the system to monitor performance and identify bottlenecks.

## Database Model

The **PostgreSQL** database stores data related to jobs, companies, and reviews. The schema is designed with the following relationships:

- **One-to-Many between Company and Job**: A company can have multiple job listings, but each job is associated with one company.
- **One-to-Many between Company and Review**: A company can have multiple reviews, but each review belongs to a single company.

### Database Schema:
- **Company Table**: Stores company profiles.
  - `id`, `name`, `description`, `rating`
- **Job Table**: Stores job postings.
  - `id`, `title`, `description`, `minSalary`,`maxSalary`,`company_id` (foreign key to Company)
- **Review Table**: Stores reviews for companies.
  - `id`,`title`, `description`, `rating`, `company_id` (foreign key to Company),
  
## Technology Stack

- **Spring Boot**: For building the backend microservices.
- **PostgreSQL**: A relational database for storing job, company, and review data.
- **Docker**: Containerizes the microservices for easier deployment.
- **Kubernetes**: Manages and orchestrates the deployment of containers in production.
- **API Gateway**: Routes incoming requests to the appropriate
