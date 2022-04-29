$(function () {
    /* ====================================================== Commons =============================================== */
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var RESPONSE_INFO_CODE = 0;
    var RESPONSE_SUCCESS_CODE = 1;
    var RESPONSE_WARNING_CODE = 2;
    var RESPONSE_ERROR_CODE = 3;

    /* ====================================================== Login =============================================== */
    // Validation type
    var VALIDATION_SUCCESS_STATUS = "success";
    var VALIDATION_WARNING_STATUS = "warning"
    var VALIDATION_ERROR_STATUS = "error";

    // Verify the format of username needed not empty
    $("#input-admin-username").change(function () {
        verify_username_format();
    });

    // Verify the username format
    var verify_username_format = function () {
        var $input_username = $("#input-admin-username");
        var username = $input_username.val();
        if (username.length <= 0) {
            show_validation_status($input_username, VALIDATION_ERROR_STATUS);
            return false;
        } else {
            show_validation_status($input_username, VALIDATION_SUCCESS_STATUS);
            return true;
        }
    };

    // Verify the format of password needed not empty
    $("#input-admin-password").change(function () {
        verify_password_format();
    });

    // Verify the password format
    var verify_password_format = function () {
        var $input_password = $("#input-admin-password");
        var password = $input_password.val();
        if (password.length <= 0) {
            show_validation_status($input_password, VALIDATION_ERROR_STATUS);
            return false;
        } else {
            show_validation_status($input_password, VALIDATION_SUCCESS_STATUS);
            return true;
        }
    };

    // Show the validation status of the login form item
    var show_validation_status = function (element, status) {
        // Reset the existing style and clear the notice msg
        element.parent().removeClass("has-success has-error has-warning");
        element.next("span").removeClass("glyphicon-ok glyphicon-remove glyphicon-warning-sign");

        if (VALIDATION_SUCCESS_STATUS === status) {
            element.parent().addClass("has-success");
            element.next("span").addClass("glyphicon-ok");
        } else if (VALIDATION_ERROR_STATUS === status) {
            element.parent().addClass("has-error");
            element.next("span").addClass("glyphicon-remove");
        } else if (VALIDATION_WARNING_STATUS === status) {
            element.parent().addClass("has-warning");
            element.next("span").addClass("glyphicon-warning-sign");
        }
    };

    // Login button event
    $("#btn-login-admin").click(function () {
        if (!verify_username_format()) {
            return false;
        }
        if (!verify_password_format()) {
            return false;
        }

        return false;
        // TODO admin login
    });

    /* ================================================ Show Background ============================================= */
    //  After the page is loaded，send an ajax request to server for get background info and sentence info

    var array = [
        "http://ravh2ew5d.hn-bkt.clouddn.com/background/bee-gfb3960275_1920.jpg",
        "http://ravh2ew5d.hn-bkt.clouddn.com/background/insect-ge42409abe_1920.jpg",
        "http://ravh2ew5d.hn-bkt.clouddn.com/background/leaves-g46c107618_1920.jpg",
        "http://ravh2ew5d.hn-bkt.clouddn.com/background/river-g7d444abfc_1920.jpg",
        "http://ravh2ew5d.hn-bkt.clouddn.com/background/sunset-g687ef1e87_1920.jpg"
    ];

    // Show the picture and character content
    var show_picture_set_title = function (slideObj, imgLink, titleObj, titleContent, author) {
        slideObj.attr("src", imgLink);
        titleObj.children("p").children("a").attr("href", imgLink);
        titleObj.children("h3").text(titleContent);
        titleObj.children("p").first().text(author);
    };

    var $first_slide = $(".first-slide");
    var $first_title = $(".first-title");
    var $second_slide = $(".second-slide");
    var $second_title = $(".second-title");
    var $third_slide = $(".third-slide");
    var $third_title = $(".third-title");

    show_picture_set_title($first_slide, array[0], $first_title, "长相思兮长相忆", "李白");
    show_picture_set_title($second_slide, array[1], $second_title, "短相思兮无穷极", "李白");
    show_picture_set_title($third_slide, array[2], $third_title, "生如夏花之绚烂", "泰戈尔");

    var countingTime = 6;
    var time = setInterval(function () {
        countingTime--;
        if (countingTime <= 0) {
            alert("time out")
            clearInterval(time);
        }
    }, 1000)
});