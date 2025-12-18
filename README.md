# Boustrophedon

## Variables

Bousrophedon is a programming language with strictly 6 variables.

|Variable name|type             |default value|
|-------------|-----------------|-------------|
|U            |File handle      |/dev/null    |
|V            |Char             |Null char    |
|W            |String (mutable) |""           |
|X            |Integer          |0            |
|Y            |Float            |0.0          |
|Z            |Boolean          |false        |

Each line contains at most one instruction and at least zero instructions.

## FLOw

The parser goes line by line down the file parsing instructions.

Once the parser reaches the end of the program, it goes backwards, hence the name Boustrophedon.

## Instructions

Here is a table of all the instructions in Boustrophedon:

|Command name |Function                                                     |
|-------------|-------------------------------------------------------------|
|HELP         |Set U to /dev/stdout/, set W to "Hello, World!", set Y to PI |
|OPEN         |Set U to W (open file W)                                     |
|WRITE        |Write W to file U                                            |
|APPEND       |Append W to file U                                           |
|READ         |Read U to W                                                  |
|SWAP X Y     |Swap X and Y (X is rounded)                                  |
|NOT          |Z = (!Z)                                                     |
|FALSE        |Z = false                                                    |
|CLEAR U      |U = /dev/null                                                |
|CLEAR V      |V = Null char                                                |
|CLEAR W      |W = ""                                                       |
|CLEAR X      |X = 0                                                        |
|CLEAR Y      |Y = 0.0                                                      |
|CLEAR Z      |Z = false (identical to FALSE)                               |
|COMPOSE X    |Add bit Z to end of X                                        |
|COMPOSE Y    |Add bit Y to end of X                                        |
|CHAR         |Set V to unicode codepoint X                                 |
|BUILD        |Append V to W                                                |
|POP W        |Pop char of W to V                                           |
|SET W        |Set Xth char of W to V                                       |
|GET W        |Set V to Xth char of W                                       |
|IF           |If Z execute next line, otherwise skip                       |
|ADD          |Y = Y + X                                                    |
|MUL          |Y = Y * X                                                    |
|DIV          |Y = Y / x                                                    |
|NEGATE X     |X = X * -1                                                   |
|NEGATE Y     |Y = Y * -1                                                   |
|EQUALS       |Z = (Y == X)                                                 |
|GREATER THAN |Z = X > Y                                                    |
|LESS THAN    |Z = X < y                                                    |
|SPLIT        |Split W with delimiter V and set W to Xth element            |
|READ LINE    |Set W to Xth line of file U                                  |
|SERIALIZE U  |Set W to filename of U                                       |
|SERIALIZE V  |Set W to V (string with length 1)                            |
|SERIALIZE X  |Set W to X as string                                         |
|SERIALIZE Y  |Set W to Y as string                                         |
|SERIALIZE Z  |Set W to Z as string                                         |
|GO           |Go to line X                                                 |
|RETURN       |Return to line of last GO call, plus 1                       |
|EXIT         |Exit with exit code X                                        |

Program flags :

## Boustrophedon

Here is a list of all of the flags:

### Analysis mode

Run a program in analysis mode (print the state when the program exits)

Usage : `Boustrophedon <filename> -ana`

### Debug mode

Run a program in debug mode (go line by line with some debug commands)

Usage : `Boustrophedon <filename> -debug`

Commands :
 - skip (skip a specific number of lines)
 - nav (go to a specific line number)

### Normal mode

Run a program in normal mode (nothing special, just run the program)

Usage : `Boustrophedon <filename>`
