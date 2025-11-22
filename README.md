📘 Spring Boot URL Shortener (Base62, Swagger, Docker)

A production-ready URL Shortener microservice built using Spring Boot, Base62 encoding, H2 in-memory database, Swagger/OpenAPI, and Docker.

This project provides:

  1.Convert Long URL → Short URL

  2.Redirect Short URL → Original URL

  3.Request Logging (stored in H2 DB)

  4.API to fetch logs

  5.API to clear logs

  6.Fully Dockerized

  7.Published on Docker Hub

  8.Unit tests included


🚀 Live Docker Image

Pull and run the image directly:

docker pull hkthakur/url-shortener:1.1
docker run -p 8080:8080 hkthakur/url-shortener:1.1

Swagger UI will be available at:

👉 http://localhost:8080/swagger-ui/index.html


📁 Project Structure

src/
 ├── main/
 │   ├── java/com/example/springboot_url_shortener/
 │   │      ├── controller/
 │   │      ├── service/
 │   │      ├── repository/
 │   │      ├── entity/
 │   │      ├── dto/
 │   │      └── SpringbootUrlShortenerApplication.java
 │   └── resources/
 │          ├── application.properties
 └── test/
         ├── UrlServiceTest.java
         └── UrlControllerTest.java

  
🧠 How Base62 Shortening Works

Base62 consists of:

  0–9  (10 chars)
  A–Z  (26 chars)
  a–z  (26 chars)
  Total = 62 characters

Short code generation steps:

Auto-increment numeric ID in DB

Convert numeric ID → Base62 string

Example:

ID = 1 → Base62 = "1"
ID = 62 → Base62 = "10"
ID = 238327 → "ZZZ"


This ensures:

✔ Unique short URLs
✔ Fast encoding
✔ No collisions
✔ No hashing required

📚 API Documentation (Swagger UI)

Once the app is running, open:

👉 http://localhost:8080/swagger-ui/index.html

All APIs are documented using OpenAPI v3.

🔥 Available REST Endpoints
1️⃣ Shorten a long URL
POST /api/shorten

{
  "url": "https://google.com"
}

Response:

{
  "shortUrl": "http://localhost:8080/resolve/1"
}

2️⃣ Redirect to the original URL
GET /resolve/{code}


Example:

GET /resolve/1


Redirects → original URL (302)

3️⃣ Fetch request logs
GET /api/logs

4️⃣ Clear logs
DELETE /api/logs

🐳 Running with Docker
Build Docker Image
docker build -t url-shortener:1.1 .

Tag the image
docker tag url-shortener:1.1 hkthakur/url-shortener:1.1

Push to Docker Hub
docker push hkthakur/url-shortener:1.1

Run container locally
docker run -d -p 8080:8080 --name url-app hkthakur/url-shortener:1.1

🧪 Unit Tests

Tests included:

  UrlServiceTest

  UrlControllerTest

🗄 In-Memory H2 Database

Console available at:

👉 http://localhost:8080/h2-console

Use:

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (empty)

🧰 Tech Stack

Spring Boot 3, Java 17, Maven, H2 Database, Spring Data JPA, Spring Web, Swagger / OpenAPI, Docker, JUnit

🏗 Architecture Diagram
Client → REST API → UrlController → UrlService → UrlMappingRepository → H2 DB
                                              ↓
                                       Base62 Encoder

🚀 How to Run Locally (Without Docker)
git clone https://github.com/hkthakur13/springboot-url-shortener
cd springboot-url-shortener
mvn spring-boot:run


Open Swagger UI:

👉 http://localhost:8080/swagger-ui/index.html
