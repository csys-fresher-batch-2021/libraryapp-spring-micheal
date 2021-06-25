class Security {

	static logOut() {
		event.preventDefault();
		axios.post("logOut").then(res => {
			let url = res.data;
			window.location.href = url;
		})
	}
	
	static isLoggedIn() {
		return axios.post("isLoggedInCheck");
	}

	static isLoggedIn() {
		axios.post("isLoggedInCheck").then(res => {
			let confirmation = res.data;
			if (!confirmation) {
				window.location.href = "index.html";
			}


		})


	}
}