from starship import Skirmish
import random
def main():
    starfleet_fleet = Skirmish("Starfleet")
    borg_fleet = Skirmish("Borg")

    
    aggressor = random.choice([starfleet_fleet, borg_fleet])
    defender = starfleet_fleet if aggressor == borg_fleet else borg_fleet

    print(f"The {aggressor.faction} fleet and {defender.faction} fleet are about to fight!")
    print("Battle engaged! The {} have fired the first shot.".format(aggressor.faction))

    result = aggressor.engage(defender)

    if result == "victory":
        print(f"The {aggressor.faction} fleet emerged victorious!")
        print(f"The {defender.faction} fleet has been defeated.")
    elif result == "defeat":
        print(f"The {aggressor.faction} fleet has been defeated.")
        print(f"The {defender.faction} fleet emerged victorious!")
    else:
        print("The battle ended in a draw!")

if __name__ == "__main__":
    
    main()