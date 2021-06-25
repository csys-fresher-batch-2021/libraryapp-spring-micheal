 class  Service {

	 static logIn() {
		event.preventDefault();
		let email = document.querySelector("#exampleInputEmail1").value;
		let password = document.querySelector("#exampleInputPassword1").value;
		let user = { "email": email, "password": password };
		localStorage.setItem("LOGGED_IN_USER", JSON.stringify(user));
		axios.post("UserLogin",user).then(res => {
		let verification=res.data;
		if (verification == "Invalid user credentials") {
			toastr.error(verification)
		}
		else {
			window.location.href = "TakeBook.html"
		}
		});
	}

	static AdminLogin() {
		event.preventDefault();

		let email = document.querySelector("#exampleInputEmail1").value;
		let password = document.querySelector("#exampleInputPassword1").value;
		let user = { "email": email, "password": password };

		axios.post("adminLogin", user).then(res => {
			let verification = res.data;
			if (verification == "Invalid user credentials") {
				toastr.error(verification)
			}
			else {
				window.location.href = "UploadBooks.html"
			}

		})
	}

	static getAllRecords() {
		let content = "";
		axios.get("getAllRecords").then(res => {
			let searhResult = res.data;
			if (searhResult.length == 0) {
				document.querySelector("#book_tbl").innerHTML = content;

			}
			else {
				let i = 0;
				for (let book of searhResult) {
					i++;

					content += "<tr><td>" + i + "</td><td>" + book.email + "</td><td>" + book.bookName + "</td><td>" + book.bookQuantity + " </td><td>" + book.strDate + "</td></tr>";
				}
				document.querySelector("#book_tbl").innerHTML = content;

			}
		});
	}

	static getFinedUser() {
		let content = "";
		axios.get("getFinedUsers").then(res => {
			let searhResult = res.data;
			if (searhResult.length == 0) {
				document.querySelector("#book_tbl").innerHTML = content;

			}
			else {
				let i = 0;
				for (let book of searhResult) {
					i++;

					content += "<tr><td>" + i + "</td><td>" + book.email + "</td><td>" + book.amount + "</td></tr>";
				}
				document.querySelector("#book_tbl").innerHTML = content;

			}
		});
	}
	static payFine() {
		event.preventDefault();
		let email = document.querySelector("#email").value;
		let url = "payFine?email=" + email;
		axios.get(url).then(res => {
			let confirmation = res.data;
			alert(confirmation);
		});
	}


	static userRegistration() {
		event.preventDefault();
		let name = document.querySelector("#name").value;
		let email = document.querySelector("#email").value;
		let password = document.querySelector("#password").value;
		let user = { "email": email, "password": password, "name": name };

		axios.post("userRegisteration", user).then(res => {
			let confirmation = res.data;
			alert(confirmation);
		})
	}


	static adminRegistraion() {
		event.preventDefault();
		let name = document.querySelector("#name").value;
		let email = document.querySelector("#email").value;
		let password = document.querySelector("#password").value;

		let user = { "email": email, "password": password, "name": name };

		axios.post("adminRegisteration", user).then(res => {
			let confirmation = res.data;
			alert(confirmation);
		})
	}
}