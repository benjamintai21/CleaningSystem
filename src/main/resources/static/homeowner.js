// Nav Links Active Click
document.querySelectorAll('.navbar-link').forEach(link => {
    link.addEventListener('click', function(e) {
        document.querySelectorAll('.navbar-link').forEach(l => l.classList.remove('active'));
        this.classList.add('active');
    });
});

// PopUp Message when click on shortlist button
function showPopup(popupId) {
    const popup = document.getElementById(popupId);
    popup.classList.add("show");

    setTimeout(() => {
        popup.classList.remove("show");
    }, 2500);
}

//shortlist Cleaner pf button
const profileBtn = document.getElementById("shortListprofile-Btn");
if (profileBtn) {
    profileBtn.addEventListener("click", () => {
        showPopup("popupCleaner");
    });
}

//shortlist service buttons
const serviceBtns = document.getElementById("shortlist-service-btn");
if (serviceBtns) {
    serviceBtns.addEventListener("click", () => {
        showPopup("popupService");
    });
}

// Toggle FAQ bar

document.querySelectorAll('.toggle-arrow').forEach(arrow => {
    arrow.addEventListener('click', () => {
        arrow.classList.toggle('rotate');
        const card = arrow.closest('.service-card');
        const details = card.querySelector('.booking-details');
        if (details) {
            details.classList.toggle('show');
        }
    });
});

document.querySelectorAll('.faq-question').forEach(question => {
    question.addEventListener('click', () => {
        const current = question.parentElement;
        const isActive = current.classList.contains('active');

        document.querySelectorAll('.faq-item').forEach(item => {
            item.classList.remove('active');
            item.querySelector('.faq-toggle').textContent = '+';
        });

        if (!isActive) {
            current.classList.add('active');
            current.querySelector('.faq-toggle').textContent = '−';
        }
    });
});


// Make a slider image on Service Feature saction card
let currentIndex = 0;

function scrollCards(direction) {
    const track = document.getElementById("carouselTrack");
    const cardWidth = track.querySelector(".service-card").offsetWidth + 20;
    const totalCards = track.children.length;
    const visibleCards = 3;

    const maxIndex = totalCards - visibleCards;

    currentIndex += direction;
    if (currentIndex < 0) currentIndex = 0;
    if (currentIndex > maxIndex) currentIndex = maxIndex;

    track.scrollTo({
        left: currentIndex * cardWidth,
        behavior: "smooth"
    });
};