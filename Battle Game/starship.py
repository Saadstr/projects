import random

class Starship:
    def __init__(self,ship_class,firepower,hit_chance,integrity=100):
        self.ship_class=ship_class
        self.firepower=firepower
        self.hit_chance=hit_chance
        self.integrity=integrity
    def ship_class(self):
        return self.ship_class
    def ship_class(self,value):
        self.ship_class=value
    def firepower(self):
        return self.firepower
    def firepower(self,value):
        self.firepower=value
    def hit_chance(self):
        return self.hit_chance
    def hit_chance(self,value):
        self.hit_chance=value
    def integrity(self):
        return self.integrity
    def integrity(self,value):
        self.integrity=value
    def take_damage(self,incoming: int):
        self.integrity-=incoming
        if self.integrity<0:
            self.integrity=0
    def fire_torpedoes(self,target):
        if self.hit_chance>= random.random():
            target.take_damage(self.firepower)
    def __repr__(self):
        return f"{self.ship_class}  Firepower: {self.firepower}, Hit chance: {self.hit_chance}, Integrity: {self.integrity}"
      
class Starfleetship(Starship):
    def __init__(self, ship_class, firepower, ship_name, ship_id):
        super().__init__(ship_class, firepower, hit_chance=.50)
        self.ship_name=ship_name
        self.ship_id=ship_id
    def __repr__(self):
        return f" {self.ship_name} ({self.ship_id})-{super().__repr__()}"
    
class BorgCube(Starship):
    def __init__(self, ship_class, firepower, num_collectives):
        super().__init__(ship_class, firepower, hit_chance=0.65)
        self.num_collectives = num_collectives

    def __repr__(self):
        return f"CUBE - {super().__repr__()}, Num Collectives: {self.num_collectives}"
    
starship = Starship("Star Cruiser", 20, 0.75)
starfleet_ship = Starfleetship("Explorer", 15, "USS Enterprise", "NCC-1701")
borg_cube = BorgCube("Assimilator", 25, 50)

starship.fire_torpedoes(starfleet_ship)



class Fleet:
    def __init__(self,faction):   
        self.faction=faction
        self.fleet_ships=self.generateships()
        self.fleet_size= len(self.fleet_ships)
    
    def generateships(self):
        num_ships = random.randint(5, 10)
        ships_list = []
        if self.faction == "Starfleet":
            id = ''
            for i in range(num_ships):
                if i < 10:
                    id = "SMS-0010" 
                else:
                    id = "SMS-000" + str(i)
                ships_list.append(Starfleetship(f"{self.faction} Ship", random.randint(5, 35), random.uniform(0.25, 1.00), id))
        elif self.faction == "Borg":
            for i in range(num_ships):
                ships_list.append(BorgCube(f"{self.faction} Ship", random.randint(5, 35), random.uniform(0.25, 1.00)))
        return ships_list
        
    def fleet_ships(self):
        return self.fleet_ships
    def fleet_ships(self,value):
        self.fleet_ships=value
    def fleet_size(self):
        return self.fleet_size
    def fleet_size(self,value):
        self.fleet_size=value
    def faction(self):
        return self.faction
   
 
class Skirmish(Fleet):
   
        
    def engage(self,enemyfleet):
        for ship in self.fleet_ships:
            for _ in range(3):
                if not enemyfleet.fleet_ships:
                    break
                targetship= random.choice(enemyfleet.fleet_ships)
                ship.fire_torpedoes(targetship)
                targetship.fire_torpedoes(ship)
            self.checkdestroyed(self.fleet_ships)
            enemyfleet.checkdestroyed(enemyfleet.fleet_ships)
            if len(self.fleet_ships) > len(enemyfleet.fleet_ships):
                return "victory"
            elif len(self.fleet_ships) < len(enemyfleet.fleet_ships):
                return "defeat"
            else:
                return "draw"
  
# Removes ship when destroyed  
    def remove_ship(self, destroyed_ship):
        if destroyed_ship in self.fleet_ships:
            self.fleet_ships.remove(destroyed_ship)
  
  
   # checks to see if ship is destroyed
    def checkdestroyed(self, ships):
        for ship in ships.copy():
            if ship.integrity <= 30:
                #print(f"{ship.ship_class} is destroyed.")
                self.remove_ship(ship)


#starfleet_fleet = Skirmish("Starfleet")
#borg_fleet = Skirmish("Borg")
#result = starfleet_fleet.engage(borg_fleet)
#print(f"{starfleet_fleet.faction} vs. {borg_fleet.faction}: {result}")

