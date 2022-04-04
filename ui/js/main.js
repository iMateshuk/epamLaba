// Selecting The Container.
const flexContainer = document.querySelector('.flexContainer');
// data
let names = ['sport', 'spa', 'center', 'yoga', 'puzzle', 'rent', 'fun', 'book'];
let descriptions = ['You need it - sport', 'Just relax in spa', 'It is your center', 'All you need is yoga',
    'Kind of puzzle', 'Need rent?', 'Fun for you', 'Read a book'];
let tags = ['yoga', 'tourism', 'sport', 'rent', 'book', 'entertainment', 'puzzle'];
let scrollPosition = 0;
// ------------------------ CreateElement
window.onload = Create;

function Create() {
    for (let i = 0; i < 14; i++) {
        createPost();
    }
}

// ------------------------ InfiniteScroll
// The Scroll Event.
window.addEventListener('scroll', () => {
    const {scrollHeight, scrollTop, clientHeight} = document.documentElement;
    if (scrollTop + clientHeight > scrollHeight - 5) {
        for (let i = 0; i < 3; i++) {
            setTimeout(createPost, 150);
        }
    }
});
// The createPost function creates The HTML for the blog post.
// It append it to the container.
function createPost() {
    let price = Math.floor((Math.random() * 999) + 1);
    let expire = Math.floor((Math.random() * 10) + 1);
    let name = names[Math.floor(Math.random() * names.length)] + " " + names[Math.floor(Math.random() * names.length)];
    name = name.charAt(0).toUpperCase() + name.substr(1);
    let description = descriptions[Math.floor(Math.random() * descriptions.length)];
    let tag = tags[Math.floor(Math.random() * tags.length)];
    let img = Math.floor(Math.random() * 9);

    const post = document.createElement('div');
    post.className = 'container';
    post.innerHTML = `
            <img src="../../img/${img}.jpg" alt="no imh" class="bigBox">
            <table class="table">
                <tr>
                    <td class="name"><b>${name}</b></td>
                    <td class="icons"> 
                        <span class="material-icons">favorite</span> 
                        <div class="tag">${tag}</div>
                    </td>
                </tr>
                <tr class="trMarkBottomLine">
                    <td class="description">${description}</td>
                    <td class="expire">Expires in ${expire} days</td>
                </tr>
                <tr>
                    <td class="price">$${price} </td>
                    <td>
                        <input type="submit" class="cart" name="Cart" value="Add to Cart">
                    </td>
                </tr>
            </table>
    `;
//   Appending the post to the container.
    flexContainer.appendChild(post);
    if (selectTag !== "all") {
        filterSelection(selectTag);
    }
    if (document.getElementById('searchbar').value.toLowerCase() !== '') {
        myFunction();
    }
}

// ------------------------ Search
let selectTag = "all";

// ------------------------ TagSearch
function filterSelection(selectTag) {
    if (selectTag === "all") selectTag = "";
    let container = document.getElementsByClassName("container");

    let tag;
    for (let i = 0; i < container.length; i++) {
        tag = container[i].getElementsByClassName('tag');

        if (!(tag[0].innerHTML.toLowerCase().includes(selectTag))) {
            container[i].style.display = "none";
        } else {
            container[i].style.display = "";
        }
    }
    document.getElementById('searchbar').value = '';
}

// ------------------------ InputSearch
function myFunction() {
    let input = document.getElementById('searchbar').value.toLowerCase();
    let container = document.getElementsByClassName("container");

    let name, desc, tag;
    for (let i = 0; i < container.length; i++) {

        name = container[i].getElementsByClassName('name');
        desc = container[i].getElementsByClassName('description');
        tag = container[i].getElementsByClassName('tag');

        if (!(name[0].innerHTML.toLowerCase().includes(input) && desc[0].innerHTML.toLowerCase().includes(input) &&
            tag[0].innerHTML.toLowerCase().includes(input))) {
            container[i].style.display = "none";
        } else {
            container[i].style.display = "";
        }
    }
}

// ------------------------ ChangeButtonColor
/*let btnContainer = document.getElementById("nav");
let btns = btnContainer.getElementsByClassName("btn");
for (let i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        let current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
    });
}*/
// ------------------------ PositionButton
//Get the button:
posButton = document.getElementById("posBtn");

function posFunction() {
    document.body.scrollTop = scrollPosition; // For Safari
    document.documentElement.scrollTop = scrollPosition; // For Chrome, Firefox, IE and Opera
    scrollPosition = 0;
    posButton.style.display = "none";
}

// ------------------------ TopButton
//Get the button:
mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.style.display = "block";
        scrollPosition = 0; // Reset scroll position after move down. If you don't want reset position simple comment this line.
    } else {
        mybutton.style.display = "none";
    }

    if (scrollPosition !== 0) {
        posButton.style.display = "block";
    } else {
        posButton.style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    scrollPosition = window.pageYOffset | document.body.scrollTop;
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}