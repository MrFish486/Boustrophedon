import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Boustrophedon {
	private String[] source;
	private int index = 0;
	private boolean forward = true;
	public File                 U;
	public char                 V;
	public ArrayList<Character> W;
	public int                  X;
	public float                Y;
	public boolean              Z;
	public Boustrophedon (String program) {
		this.source = program.split("\n");
		this.U = new File("/dev/null");
		this.V = '\u0000';
		this.W = new ArrayList <Character> ();
		this.X = 0;
		this.Y = 0f;
		this.Z = false;
	}
	public String getW () {
		String out = "";
		for (int i = 0; i < this.W.size(); i ++) {
			out += (char) this.W.get(i);
		}
		return out;
	}
	public void setW (String value) {
		this.W.clear();
		for (int i = 0; i < value.length(); i ++) {
			this.W.add(value.charAt(i));
		}
	}
	public void tick () {
		String instruction = this.source[this.index];
		if (instruction.equals("HELP")) {
			this.U = new File("/dev/stdout");
			this.setW("Hello, World!\n");
			this.Y = (float) Math.PI;
		} else if (instruction.equals("OPEN")) {
			this.U = new File(this.getW());
		} else if (instruction.equals("WRITE")) {
			Boustrophedon.writeToFile(this.U, this.getW(), false);
		} else if (instruction.equals("APPEND")) {
			Boustrophedon.writeToFile(this.U, this.getW(), true);
		} else if (instruction.equals("READ")) {
			this.setW(Boustrophedon.readFromFile(this.U));
		} else if (instruction.equals("SWAP X Y")) {
			int swp = Math.round(this.Y);
			this.Y = (float) this.X;
			this.X = swp;
		} else if (instruction.equals("NOT")) {
			this.Z = !this.Z;
		} else if (instruction.equals("FALSE")) {
			this.Z = false;
		} else if (instruction.equals("CLEAR U")) {
			this.U = new File("/dev/null");
		} else if (instruction.equals("CLEAR V")) {
			this.V = '\u0000';
		} else if (instruction.equals("CLEAR W")) {
			this.W.clear();
		} else if (instruction.equals("CLEAR X")) {
			this.X = 0;
		} else if (instruction.equals("CLEAR Y")) {
			this.Y = 0f;
		} else if (instruction.equals("CLEAR Z")) {
			this.Z = false;
		} else if (instruction.equals("COMPOSE X")) {
			this.X <<= 1;
			this.X += this.Z ? 1 : 0; 
		} else if (instruction.equals("COMPOSE Y")) {
			this.Y *= 2;
			this.Y += this.Z ? 1 : 0;
		} else if (instruction.equals("CHAR")) {
			this.V = (char) this.X;
		} else if (instruction.equals("BUILD")) {
			this.W.add(this.V);
		} else if (instruction.equals("POP W")) {
			this.V = this.W.get(this.W.size() - 1);
			this.W.remove(this.W.size() - 1);
		} else if (instruction.equals("SET W")) {
			this.W.set(this.X, (Character) this.V);
		} else if (instruction.equals("GET W")) {
			this.V = this.W.get(this.X);
		} else if (instruction.equals("IF")) {
			this.index += this.Z ? 0 : 1;
		} else if (instruction.equals("ADD")) {
			this.Y += this.X;
		} else if (instruction.equals("MUL")) {
			this.Y *= this.X;
		} else if (instruction.equals("DIV")) {
			this.Y /= this.X;
		} else if (instruction.equals("NEGATE X")) {
			this.X *= -1;
		} else if (instruction.equals("NEGATE Y")) {
			this.Y *= -1;
		} else if (instruction.equals("EQUALS")) {
			this.Z = ((float) this.X == this.Y);
		} else if (instruction.equals("GREATER THAN")) {
			this.Z = this.X > this.Y;
		} else if (instruction.equals("LESS THAN")) {
			this.Z = this.X < this.Y;
		} else if (instruction.equals("SERIALIZE U")) {
			this.setW(this.U.getName());
		} else if (instruction.equals("SERIALIZE V")) {
			this.setW(this.V + "");
		} else if (instruction.equals("SERIALIZE X")) {
			this.setW(this.X + "");
		} else if (instruction.equals("SERIALIZE Y")) {
			this.setW(this.Y + "");
		} else if (instruction.equals("SERIALIZE Z")) {
			this.setW(this.Z ? "true" : "false");
		} else if (instruction.equals("GO")) {
			this.index = this.X > 0 ? this.X + 1 : this.index;
		} else if (instruction.equals("EXIT")) {
			System.exit(this.X % 256); // Negative numbers are reserved
		} else if (instruction.equals("")) {
			// nada
		} else {
			System.err.println(String.format("Unknown instruction on line %d : '%s'", this.index + 1, instruction));
			System.exit(-1);
		}
		this.index += this.forward ? 1 : -1;
		if (this.index == this.source.length) {
			this.index --;
			this.forward = false;
		} else if (this.index == -1) {
			this.index ++;
			this.forward = true;
		}
	}
	public static void main (String args[]) {
		if (args.length == 1) {
			Boustrophedon program = new Boustrophedon (Boustrophedon.readFromFile(new File(args[0])));
			while (true) {
				program.tick();
			}
		}
	}
	public static void writeToFile (File file, String content, boolean append) {
		if (file == null || content == null) return;
		BufferedWriter handle = null;
		try {
			handle = new BufferedWriter(new FileWriter(file, append));
			handle.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (handle != null) {
				try {
					handle.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String readFromFile (File file) {
		String content = "";
		try {
			Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				content += s.nextLine() + "\n";
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
