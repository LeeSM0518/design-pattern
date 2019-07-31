package object_oriented_principle;

public abstract class Pet {
  public abstract void talk();
}

class Main {
  public static void main(String[] args) {
    Pet[] pets = new Pet[]{
        new Dog(),
        new Cat(),
        new Parrot()
    };

    for (Pet pet : pets) {
      pet.talk();
    }
  }
}
