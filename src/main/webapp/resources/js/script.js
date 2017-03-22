//Jquery that we are using in mocks has no dialog function so I'm using the alert.
function sendToReview(form) {
    console.log(form);

    var id = parseInt($(form).attr('id').replace(/^[^0-9]+/, ''), 10);
    var url = 'http://localhost:8080/courses/' + id + '/update';

    console.log(id);
    console.log(url);

    $.ajax({
        type: 'post',
        url: url,
        data: 'action=review&courseId=' + id,

        success: function () {
            alert("Successfully sent to review");
            window.location.replace("http://localhost:8080/courses");
        }
    });
}