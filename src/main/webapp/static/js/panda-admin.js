$(function () {
    /* ====================================================== Commons =============================================== */
    // Context path and notice modal
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var INFO_CODE = 0;
    var SUCCESS_CODE = 1;
    var DANGER_CODE = 2;
    var WARNING_CODE = 3;

    // Prevent the default submit action of form
    $("form").on("submit", function () {
        return false;
    });

    // Show the notice modal
    var showNoticeModal = function (code, msg) {
        var $noticeContent = $("#h-notice-content");
        // Clear the existed style of the notice object
        $noticeContent.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        if (INFO_CODE === code) {
            $noticeContent.parent().addClass("alert-info");
        } else if (SUCCESS_CODE === code) {
            $noticeContent.parent().addClass("alert-success");
        } else if (WARNING_CODE === code) {
            $noticeContent.parent().addClass("alert-warning");
        } else if (DANGER_CODE === code) {
            $noticeContent.parent().addClass("alert-danger");
        }
        $noticeContent.text(msg);
        $("#div-notice-modal").modal('show');
    };

    /* ====================================================== Login =============================================== */
    // Validation type
    var STATUS_SUCCESS = "success";
    var STATUS_WARNING = "warning"
    var STATUS_ERROR = "error";

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

    // Verify the format of username needed not empty
    $("#input-admin-username").change(function () {
        verify_username_format();
    });

    // Verify the format of password needed not empty
    $("#input-admin-password").change(function () {
        verify_password_format();
    });

    // Login button event
    $("#btn-login-admin").click(function () {
        // Verify the username and password format after admin click the login button
        if (!verify_username_format()) {
            return false;
        }
        if (!verify_password_format()) {
            return false;
        }

        var username = $("#input-admin-username").val();
        var password = $("#input-admin-password").val();
        $.ajax({
            url: contextPath + "admin/" + username + "/" + password,
            dataType: "json",
            method: "get",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    // Username and password are correct, ask the server for page dispatching
                    location.href = contextPath + "user";
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求登录失败，请稍后重试");
            }
        })
    });
});