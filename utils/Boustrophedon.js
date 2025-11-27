const t = /*String to encode */;

const codes = t.split("").map(e => e.charCodeAt(0).toString(2).split(""));

let current = false;

console.log("FALSE");

for (let i = 0 ; i < codes.length ; i ++) {
	console.log("CLEAR X");
	for (let e = 0 ; e < codes[i].length ; e ++) {
		if (codes[i][e] == "0") {
			if (current == false) {
				console.log("COMPOSE X");
			} else {
				console.log("NOT\nCOMPOSE X");
				current = !current;
			}
		} else if (codes[i][e] == "1") {
			if (current == true) {
				console.log("COMPOSE X");
			} else {
				console.log("NOT\nCOMPOSE X");
				current = !current;
			}
		}
	}
	console.log("CHAR\nBUILD");
}
