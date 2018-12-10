# Joguinho

This is the backend repository for joguinho
Things to decide
- Story
- How characters will work
- How experience will work
- How battles will work
- How items will work
- How skills will work
- Etc.

## Setting

Lovecraftian setting (horror, insanity)

## Characters
The game will have characters.

### Beginning
Player can create his/her character by defining:
 - Class
   - Detective
   - Explorer
   - Scientist/Professor(Who the fuck cares)
   - Sei lá eu ò man
 - Pet?
   - Wolf
   - Raven
   - Walking Potato (with irish accent)

### Character Attributes

 - Alignment
   - Chaos
   - Order
 - Stats
   - Offensive Stats
       - Intelligence (magic)
       - Markmanship (long range)
       - Strength (short range)
   - Character Stats
       - Perception/Cunning (research)
       - Fitness (health)
       - Charisma (Dating)
       - Speed

### Equipment

 - Armor
    - Head
    - Torso
    - Leggings
    - Hands
    - Shoest
    - Trinkets (rings, necklace)
 - Weapon (Class based)
    - Gun, Crossbow, 
    - Staff, Wand
    - Sword, Hammer

Olha uma ideia gira para NPC: Gustav Johanssen

### Experience
Characters should have experience.

## Items
The game will have items.


## Running the code

`./gradlew docker && docker-compose up`

Might have to run `chmod +x gradlew` first.

In case there are exception connecting to postgres on startup just `ctrl+c` and run `docker-compose up` again.
There are at time a first startup issue due to some timings that I haven't solved yet.

`curl --header "Content-Type: application/json" --request POST --data '{"name":"xyz", "password":"banana"}'  http://localhost:8080/sign-up`
`curl --header "Content-Type: application/json" --request POST --data '{"name":"xyz", "password":"banana"}' -u user:password  http://localhost:8080/sign-up`