var initcarousel = function(){

    let items = document.querySelectorAll('.image-carousel .carousel .carousel-item');

    items.forEach((el) => {
        const minPerSlide = 6;
        let next = el.nextElementSibling
        for (var i=1; i<minPerSlide; i++) {
            if (!next) {
                // wrap carousel by using first child
                    next = items[0]
            }
            let cloneChild = next.cloneNode(true)
            el.appendChild(cloneChild.children[0])
            next = next.nextElementSibling
        }
    });
    
    let items2 = document.querySelectorAll('.video-carousel .carousel .carousel-item');

    items2.forEach((el) => {
        const minPerSlide = 6;
        let next = el.nextElementSibling;
        for (var i=1; i<minPerSlide; i++) {
            if (!next) {
                // wrap carousel by using first child
                    next = items[0];
            }
            let cloneChild = next.cloneNode(true);
            el.appendChild(cloneChild.children[0]);
            next = next.nextElementSibling
        }
    });
};

initcarousel();