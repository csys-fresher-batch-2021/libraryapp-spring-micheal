
class Service {

	static bookService() {
		event.preventDefault();
		let bookName = document.querySelector("#bookName").value;
		let url = "getBookByName?bookName=" + bookName;
		let content = "";
		axios.get(url).then(res => {
			let searchedBooks = res.data;
			if (searchedBooks.length == 0) {
				document.querySelector("#book_tbl").innerHTML = content;
				toastr.error('Sorry we dont have that book');

			}
			else {
				let bookN = "";
				let quant = null;
				let i = 0;
				for (let book of searchedBooks) {
					i++;
					bookN = book.name;
					quant = book.quantity;
					content += "<tr><td>" + i + "</td><td>" + book.name + "</td><td>" + book.quantity + " </td><td><button onclick=\"Service.saveBook(('" + bookN + "'),('" + quant + "'))\" ' class='btn btn-warning'>Take Book</button></td></tr>";
					document.querySelector("#book_tbl").innerHTML = content;

				}

			}


		});
	}

	static takeBook() {
		event.preventDefault();
		let bookName = document.querySelector("#bookName").value;
		let quantity = document.querySelector("#quantity").value;
		let url = "takeBook?bookName=" + bookName + "&quantity=" + quantity;
		axios.get(url).then(res => {
			let confirmation = res.data;

			if (confirmation == 'BOOK TAKEN SUCCESSFULLY') {
				toastr.success(confirmation);
			}
			else {
				toastr.error(confirmation);
			}
		});
	}

	static returnBook() {
		event.preventDefault();
		let bookName = document.querySelector("#bookName").value;
		let quantity = document.querySelector("#quantity").value;
		let url = "returnBook?bookName=" + bookName + "&quantity=" + quantity;
		axios.get(url).then(res => {
			let confirmation = res.data;
			if (confirmation == 'YOU RETURNED ALL THE BOOKS' || confirmation == 'YOU RETURNED SOME BOOKS') {
				toastr.success(confirmation);
			} else {
				toastr.error(confirmation);
			}
		});

	}

	static viewDebtBooks() {
		let content = "";
		axios.get("getDebtUserDetail").then(res => {
			let searhResult = res.data;
			if (searhResult.length == 0) {
				document.querySelector("#book_tbl").innerHTML = content;
				toastr.success('You didnt took any book so far')
			}
			else {
				let i = 0;
				for (let book of searhResult) {
					i++;

					content += "<tr><td>" + i + "</td><td>" + book.bookName + "</td><td>" + book.bookQuantity + " </td><td>" + book.strDate + "</td></tr>";
				}
				document.querySelector("#book_tbl").innerHTML = content;
				toastr.warning('Only free for three days after that  Fine should be paid');

			}
		});
	}

	static uploadBooks() {

		event.preventDefault();
		let bookName = document.querySelector("#bookName").value;
		let quantity = document.querySelector("#quantity").value;
		let url = "uploadBooks?bookName=" + bookName + "&quantity=" + quantity;
		axios.get(url).then(res => {
			let confirmation = res.data;
			alert(confirmation);
		});
	}
	
	  static saveBook(book, quant) {
		localStorage.setItem("books", JSON.stringify(book));
		window.location.href="LoginForm.html";
	}
	
	static loadTakeBook(){
		document.querySelector("#bookName").value=JSON.parse(localStorage.getItem("books"));
		localStorage.clear();
	}
}


