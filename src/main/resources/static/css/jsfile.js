(function () {
    $(window).load(function () {
        setTimeout(function () {
           $('.loader').fadeOut();
            start();
        }, 200);
    });
    $(document).ready(function () {
        $('.splash').slideToggle(3000);
        $('.frame1').delay(3200).show('slide', { direction: 'right' }, 1000);
        $('.frame2').delay(3300).show('slide', { direction: 'left' }, 1000);
        $('.frame3').delay(3800).slideToggle(1000);
        $('.frame3').css('line-height', '400px');
        $('.frame2').delay(4000).hide('slide', { direction: 'left' }, 1000);
        $('.frame1').delay(4200).hide('slide', { direction: 'right' }, 1000);
        $('.splash').delay(4500).slideToggle(2000);
        $('.frame3').delay(4800).transition({
            scale: [
                1.2,
                1.2
            ],
            duration: 1000
        });
        $('.splash').delay(500).animate({ width: '50%' }, 1000);
    });
}.call(this));