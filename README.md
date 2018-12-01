# Joguinho

Things to decide
- Story
- How characters will work
- How experience will work
- How battles will work
- How items will work
- How skills will work
- Etc.

## Characters
The game will have characters.

### Experience
Characters should have experience.

## Items
The game will have items.


## Running the code

`./gradlew docker && docker-compose up`

Might have to run `chmod +x gradlew` first.

In case there are exception connecting to postgres on startup just `ctrl+c` and run `docker-compose up` again.
There are at time a first startup issue due to some timings that I haven't solved yet.

`curl --header "Content-Type: application/json" --request POST --data '{"name":"xyz"}' http://localhost:8080/player`