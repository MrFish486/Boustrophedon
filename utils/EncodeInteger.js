const t = 2/*Replace with integer to encode*/;

const bits = t.toString(2).split("");

let current = false;

console.log("FALSE");

console.log("CLEAR X");
for (let e = 0 ; e < bits.length ; e ++) {
	if (bits[e] == "0") {
		if (current == false) {
			console.log("COMPOSE X");
		} else {
			console.log("NOT\nCOMPOSE X");
			current = !current;
		}
	} else if (bits[e] == "1") {
		if (current == true) {
			console.log("COMPOSE X");
		} else {
			console.log("NOT\nCOMPOSE X");
			current = !current;
		}
	}
}
