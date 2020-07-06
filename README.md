# BOOK APP

## Build

To build there is prepared sh script which run docker-compose-build.yml and run tests and build app inside docker then
docker build is made with jar build by compose run. Docker build is creating book-app image.

```$bash
chmod +x build.sh
./build.sh
```

## Run

There is already docker image pushed to docker hub so to run app just use
`docker-compose up` it will start postgres db and pull book-app:0.0.1 image

To run on your local image build just use
`docker-compose -f docker-compose-local up` this will also start postgres db but will use your local book-app image.

For debugging start only postgres db from compose file like this `docker-compose up -d book_database` and then you can run
app using gradle `./gradlew bootRun --args='--spring.profiles.active=dev'`

## API

There is swagger added to project under
`http://localhost:8081/swagger-ui.html`

api gives possibility to make CRUD operations on books and adding comments to choosen book.