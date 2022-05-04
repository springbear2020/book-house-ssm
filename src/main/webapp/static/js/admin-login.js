$(function () {
    /* ====================================================== Commons =============================================== */
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var INFO_CODE = 0;
    var SUCCESS_CODE = 1;
    var WARNING_CODE = 2;
    var DANGER_CODE = 3;

    // Prevent the default submit action of form
    $("form").on("submit", function () {
        return false;
    });

    // Show the notice modal
    var show_notice_modal = function (responseCode, msg) {
        var $modalObj = $("#div-modal-notice");
        var $noticeObj = $("#h-notice-content");
        // Clear the existed style of the notice object
        $noticeObj.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        if (INFO_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-info");
        } else if (SUCCESS_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-success");
        } else if (WARNING_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-warning");
        } else if (DANGER_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-danger");
        }
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    /* ====================================================== Login =============================================== */
    // Validation type
    var STATUS_SUCCESS = "success";
    var STATUS_WARNING = "warning"
    var STATUS_ERROR = "error";

    // Verify the format of username needed not empty
    $("#input-admin-username").change(function () {
        verify_username_format();
    });
    // Verify the username format
    var verify_username_format = function () {
        var $input_username = $("#input-admin-username");
        var username = $input_username.val();
        if (username.length <= 0) {
            show_validation_status($input_username, STATUS_ERROR);
            return false;
        } else {
            show_validation_status($input_username, STATUS_SUCCESS);
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
            show_validation_status($input_password, STATUS_ERROR);
            return false;
        } else {
            show_validation_status($input_password, STATUS_SUCCESS);
            return true;
        }
    };

    // Show the validation status of the login form item
    var show_validation_status = function (element, status) {
        // Reset the existing style and clear the notice msg
        element.parent().removeClass("has-success has-error has-warning");
        element.next("span").removeClass("glyphicon-ok glyphicon-remove glyphicon-warning-sign");

        if (STATUS_SUCCESS === status) {
            element.parent().addClass("has-success");
            element.next("span").addClass("glyphicon-ok");
        } else if (STATUS_ERROR === status) {
            element.parent().addClass("has-error");
            element.next("span").addClass("glyphicon-remove");
        } else if (STATUS_WARNING === status) {
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

        var username = $("#input-admin-username").val();
        var password = $("#input-admin-password").val();
        $.ajax({
            url: contextPath + "admin/login",
            data: "username=" + username + "&password=" + password,
            dataType: "json",
            method: "get",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    // Username and password are correct, ask the server for page dispatch
                    location.href = contextPath + "manage";
                } else {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(DANGER_CODE, "请求登录失败，请稍后重试");
            }
        })
    });

    /* ================================================ Show Background ============================================= */
    //  After the page is loaded，send an ajax request to server for get background info and sentence info

    // Show the picture and set character content of the page
    var show_picture_set_title = function (slideObj, imgLink, titleObj, titleContent) {
        slideObj.attr("src", imgLink);
        titleObj.children("p").children("a").attr("href", imgLink);
        titleObj.children("h3").text(titleContent);
    };

    var $first_slide = $(".first-slide");
    var $first_title = $(".first-title");
    var $second_slide = $(".second-slide");
    var $second_title = $(".second-title");
    var $third_slide = $(".third-slide");
    var $third_title = $(".third-title");

    show_picture_set_title($first_slide, array[0], $first_title, "长相思兮长相忆");
    show_picture_set_title($second_slide, array[1], $second_title, "短相思兮无穷极");
    show_picture_set_title($third_slide, array[2], $third_title, "生如夏花之绚烂");

    // // 循环定时器
    // setInterval(function () {
    //     var myDate = new Date();
    //     alert("timeOut")
    // }, 3000);
});