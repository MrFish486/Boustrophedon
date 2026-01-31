import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Boustrophedon {
	private String[] source;
	private int index = 0;
	private int returnindex = -1;
	private boolean forward = true;
	private boolean analysismode;
	public File                 U;
	public char                 V;
	public ArrayList<Character> W;
	public int                  X;
	public float                Y;
	public boolean              Z;
	public Boustrophedon (String program, boolean analysismode) {
		this.source = program.split("\n");
		this.U = new File("/dev/null");
		this.V = '\u0000';
		this.W = new ArrayList <Character> ();
		this.X = 0;
		this.Y = 0f;
		this.Z = false;
		this.analysismode = analysismode;
	}
	public void renderState () {
		System.out.println(String.format("\033[1mU\033[0m=FILE   [%s]\n\033[1mV\033[0m=CHAR   ['%s']\n\033[1mW\033[0m=STRING [\"%s\"]\n\033[1mX\033[0m=INT    [%d]\n\033[1mY\033[0m=FLOAT  [%f]\n\033[1mZ\033[0m=BOOL   [%b]\nReturnIndex=%d", this.U.getName(), this.V == '\n' ? "\\n" : this.V + "", this.getW().replace("\n", "\\n"), this.X, this.Y, this.Z, this.returnindex));
		System.out.print(String.format("INSTRUCTION (\033[1m%d\033[0m / \033[1m%d\033[0m) = %s", this.getIndex() + 1, this.getLength(), this.getInstruction()));
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
	public int getIndex () {
		return this.index;
	}
	public String getInstruction () {
		return this.source[this.index];
	}
	public int getLength () {
		return this.source.length;
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
		} else if (instruction.equals("SWAP U W")) {
			String swp = this.U.getName();
			this.U = new File(this.getW());
			this.setW(swp);
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
		} else if (instruction.equals("SPLIT")) {
			this.setW(this.getW().split(this.V + "")[this.X]);
		} else if (instruction.equals("READ LINE")) {
			this.setW(Boustrophedon.readFromFile(this.U).split(this.V + "")[this.X]);
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
			this.returnindex = this.index;
			this.index = this.X > 0 ? this.X - 1 : this.index;
		} else if (instruction.equals("RETURN")) {
			if (this.returnindex != -1) {
				this.index = this.returnindex + 1;
				this.returnindex = -1;
			}
		} else if (instruction.equals("EXIT")) {
			if (this.analysismode) this.renderState();
			System.exit(this.X % 256); // Negative numbers are reserved
		} else if (instruction.equals("")) {
			// nada
		} else {
			System.err.println(String.format("\033[1;31mUnknown instruction\033[0m on line %d : '%s'", this.index + 1, instruction));
			if (this.analysismode) this.renderState();
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
			Boustrophedon program = new Boustrophedon (Boustrophedon.readFromFile(new File(args[0])), false);
			while (true) program.tick();
		} else if (args.length == 2 && args[1].equals("-debug")) {
			System.out.println("\033[?25h");
			Scanner sc = new Scanner (System.in);
			Boustrophedon program = new Boustrophedon (Boustrophedon.readFromFile(new File(args[0])), false);
			System.out.println("\033[1mBoustrophedon debugger\033[0m");
			int skip = 0;
			while (true) {
				if (skip == 0) {
					Boustrophedon.clear();
					program.renderState();
					String f = sc.nextLine();
					if (f.equals("skip")) {
						System.out.print("How many lines? : ");
						skip = sc.nextInt();
						try {
							System.in.read(new byte[System.in.available()]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (f.equals("nav")) {
						System.out.print("What line? : ");
						int tmp = sc.nextInt();
						if (program.getIndex() + 1 > tmp) {
							System.out.println("Cannot navigate backwards");
						} else {
							skip = tmp - (program.getIndex() + 1);
							sc.nextLine();
						}
						try {
							System.in.read(new byte[System.in.available()]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					skip --;
				}
				program.tick();
			}
		} else if (args.length == 2 && args[1].equals("-ana")) {
			//String[] content = Boustrophedon.readFromFile(new File(args[0])).split("\n");
			Boustrophedon program = new Boustrophedon (Boustrophedon.readFromFile(new File(args[0])), true);
			while (true) program.tick();
		} else {
			System.out.println("\033[1mUsage\033[0m:\n\tBoustrophedon <file>         - run a file\n\tBoustrophedon <file> -ana    - run a file in analysis mode\n\tBoustrophedon <file> -debug  - run a file in debug mode");
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
	public static void clear () {
		System.out.print("\033[H\033[2J\033[3J");
	}
}
