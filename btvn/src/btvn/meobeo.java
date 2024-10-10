package btvn;

public class meobeo {
	public static class Cat {
		String name, gender;
		int old;

		public Cat() {
		}

		public Cat(String name_, String gender_, int old_) {
			name = name_;
			gender = gender_;
			old = old_;
		}

		public String getCatName() {
			return this.name;
		}

		public String getCatGender() {
			return this.gender;
		}

		public int getCatOld() {
			return this.old;
		}

		public void Meow() {
			System.out.println("Meow");
		}

		public void Walk() {
			System.out.println("Walking..");
		}

		public void Feed() {
			System.out.println("Eating..");
		}
	}

	public static class Tabby_Cat extends Cat {
		String pattern;

		public Tabby_Cat(String name_, String gender_, int old_, String pattern_) {
			super(name_, gender_, old_);
			pattern = pattern_;
		}

		public void showPattern() {
			System.out.println("Pattern: " + this.pattern);
		}
	}

	public static void main(String[] args) {
		Tabby_Cat t_Cat1 = new Tabby_Cat("Pat Fusty", "Male", 2, "Spotted");
		t_Cat1.showPattern();
		String Cat_Name = t_Cat1.getCatName();
		System.out.println("Name: " + Cat_Name);
		t_Cat1.Meow();
		t_Cat1.Walk();
		t_Cat1.Feed();
	}
}