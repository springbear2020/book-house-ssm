$(function () {
    /* ====================================================== Commons =============================================== */
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var RESPONSE_INFO_CODE = 0;
    var RESPONSE_SUCCESS_CODE = 1;
    var RESPONSE_WARNING_CODE = 2;
    var RESPONSE_ERROR_CODE = 3;

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
        if (RESPONSE_INFO_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-info");
        } else if (RESPONSE_SUCCESS_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-success");
        } else if (RESPONSE_WARNING_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-warning");
        } else if (RESPONSE_ERROR_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-danger");
        }
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    /* ========================================= Login and Register Common ========================================== */
    // Validation type
    var VALIDATION_SUCCESS_STATUS = "success";
    var VALIDATION_WARNING_STATUS = "warning"
    var VALIDATION_ERROR_STATUS = "error";

    // Show validate msg of the form item
    var show_form_item_validate_msg = function (element, status, msg) {
        // Reset the existing style and clear the notice msg
        element.parent().removeClass("has-success has-error has-warning");
        element.next("span").next("span").removeClass("glyphicon-ok glyphicon-remove glyphicon-warning-sign");
        element.next("span").text("");

        if (VALIDATION_SUCCESS_STATUS === status) {
            element.parent().addClass("has-success");
            element.next("span").next("span").addClass("glyphicon-ok");
        } else if (VALIDATION_ERROR_STATUS === status) {
            element.parent().addClass("has-error");
            element.next("span").next("span").addClass("glyphicon-remove");
        } else if (VALIDATION_WARNING_STATUS === status) {
            element.parent().addClass("has-warning");
            element.next("span").next("span").addClass("glyphicon-warning-sign");
        }
        element.next("span").text(msg);
    };

    // Remove the existed data and style, clear the validate notice msg
    var modal_item_reset_default = function (element) {
        element.val("");
        element.next("span").text("");
        element.parent().removeClass("has-success has-error has-warning");
        element.next("span").next("span").removeClass("glyphicon-ok glyphicon-remove glyphicon-warning-sign");
    };

    /* ==================================================== Login =================================================== */
    // Open the login modal when click login link
    $("#link-login").click(function () {
        // Can not close it unless click close symbol
        $("#modal-login").modal({
            backdrop: "static"
        })
    });

    // Login button event
    $("#btn-login").click(function () {
        /*
         * Verify the username and password again,
         * to fix the problem when user login with do nothing after opening the modal,
         * return false to prevent the send request
         */
        if (!verify_login_username_format()) {
            return false;
        }
        if (!verify_login_password_format()) {
            return false;
        }

        // Send an ajax request the verify the username and password
        $.ajax({
            url: contextPath + "user/login",
            method: "get",
            data: $("#form-login").serialize(),
            dataType: "json",
            success: function (response) {
                // Username and password are correct, resend an request to server for page redirect
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    location.href = contextPath + "dispatcher/login";
                } else {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求登录失败，请稍后重试",);
            }
        })
    });

    // Login modal close symbol click event,
    // reset the style and clean notice content, close the login modal finally
    $("#btn-login-modal-close").click(function () {
        // Clear the style and content of the login form item
        modal_item_reset_default($("#input-login-username"));
        modal_item_reset_default($("#input-login-password"));
    });

    // Verify the format of the username when content has changed
    $("#input-login-username").change(function () {
        verify_login_username_format();
    });
    // Verify login username format
    var verify_login_username_format = function () {
        var $usernameObj = $("#input-login-username");
        var username = $usernameObj.val();
        if (username.length <= 0) {
            show_form_item_validate_msg($usernameObj, VALIDATION_ERROR_STATUS, "用户名不能为空");
            return false;
        } else {
            show_form_item_validate_msg($usernameObj, VALIDATION_SUCCESS_STATUS, "");
            return true;
        }
    };

    // Verify the format of the password when content has changed
    $("#input-login-password").change(function () {
        verify_login_password_format();
    });
    // Verify login password format
    var verify_login_password_format = function () {
        var $passwordObj = $("#input-login-password");
        var password = $passwordObj.val();
        if (password.length <= 0) {
            show_form_item_validate_msg($passwordObj, VALIDATION_ERROR_STATUS, "密码不能为空");
            return false;
        } else {
            show_form_item_validate_msg($passwordObj, VALIDATION_SUCCESS_STATUS, "");
            return true;
        }
    };

    /* =================================================== Register ================================================= */
    // Open the register modal when click register link
    $("#link-register").click(function () {
        // Can not close it unless click close symbol
        $("#modal-register").modal({
            backdrop: "static"
        });
    });

    // Ajax verify the existence of email
    var isEmailExists = true;
    var isObtainBtnClicked = false;
    $("#input-register-email").change(function () {
        // Verify the format of email at first
        if (!verify_email_format()) {
            $("#btn-obtain-code").attr("disabled", "disabled");
            return false;
        }

        // Send an ajax request to server for verifying whether this email has been used
        var $emailObj = $("#input-register-email");
        var email = $emailObj.val();
        $.ajax({
            url: contextPath + "email/" + email,
            type: "GET",
            dataType: "json",
            success: function (response) {
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    isEmailExists = false;
                    show_form_item_validate_msg($emailObj, VALIDATION_SUCCESS_STATUS, response.msg);

                    // If the obtain button has not clicked, unblock it
                    if (!isObtainBtnClicked) {
                        $("#btn-obtain-code").attr("disabled", false);
                    }
                } else {
                    isEmailExists = true;
                    $("#btn-obtain-code").attr("disabled", "disabled");
                    show_form_item_validate_msg($emailObj, VALIDATION_WARNING_STATUS, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求验证邮箱可用性失败");
            }
        });
    })
    // Verify email format
    var verify_email_format = function () {
        var $emailObj = $("#input-register-email");
        let email = $emailObj.val();
        let regExp = new RegExp("^([a-z0-9_-]+)@([\\da-z-]+)\\.([a-z]{2,6})$");
        if (!regExp.test(email)) {
            show_form_item_validate_msg($emailObj, VALIDATION_ERROR_STATUS, "无效的邮箱地址");
            return false;
        } else {
            show_form_item_validate_msg($emailObj, VALIDATION_SUCCESS_STATUS, "");
            return true;
        }
    };
    // Ajax obtain email verify code  button click event
    $("#btn-obtain-code").on('click', function () {
        // Verify the format of the email
        if (!verify_email_format()) {
            return false;
        }

        // Let the status of the current button start counting down
        isObtainBtnClicked = true;
        let $registerBtn = $(this);
        $registerBtn.attr("disabled", 'disabled');
        // Block the obtain code button until time out
        let countingTime = 120;
        let time = setInterval(function () {
            countingTime--;
            $registerBtn.val(countingTime);
            if (countingTime <= 0) {
                isObtainBtnClicked = false;
                $registerBtn.val("获取");
                $registerBtn.attr("disabled", false);
                clearInterval(time);
            }
        }, 1000)

        // Send an ajax to obtain code from server
        var email = $("#input-register-email").val();
        $.ajax({
            url: contextPath + "email",
            data: "email=" + email,
            type: "get",
            dataType: "json",
            success: function (response) {
                // If the email code don't sent successfully, give user some notice info
                if (RESPONSE_SUCCESS_CODE !== response.code) {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求发送邮箱验证码失败");
            }
        })
    });

    // Verify the format of the username
    var isUsernameExists = true;
    $("#input-register-username").change(function () {
        if (!verify_register_username_format()) {
            return false;
        }

        // Send an ajax to server for verifying the existence of the username entered by user
        var $usernameObj = $("#input-register-username");
        var username = $usernameObj.val();
        $.ajax({
            url: contextPath + "user/" + username,
            type: "get",
            dataType: "json",
            success: function (response) {
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    isUsernameExists = false;
                    show_form_item_validate_msg($usernameObj, VALIDATION_SUCCESS_STATUS, response.msg);
                } else {
                    isUsernameExists = true;
                    show_form_item_validate_msg($usernameObj, VALIDATION_WARNING_STATUS, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求验证用户名可用性失败");
            }
        })
    });
    // Verify register username format
    var verify_register_username_format = function () {
        var $usernameObj = $("#input-register-username");
        var username = $usernameObj.val();
        if (username.length < 2 || username.length > 16) {
            show_form_item_validate_msg($usernameObj, VALIDATION_ERROR_STATUS, "用户名为 2-16 位中英文字符");
            return false;
        } else {
            show_form_item_validate_msg($usernameObj, VALIDATION_SUCCESS_STATUS, "");
            return true;
        }
    };

    // Register button event
    $("#btn-register").click(function () {
        // Fix the problem that user do nothing after opening the modal
        if (isUsernameExists) {
            show_form_item_validate_msg($("#input-register-username"), VALIDATION_WARNING_STATUS, "用户名已被占用，请重新输入");
            return false;
        } else {
            show_form_item_validate_msg($("#input-register-username"), VALIDATION_SUCCESS_STATUS, "");
        }
        if (!verify_register_password_format()) {
            return false;
        }
        if (!verify_email_format()) {
            return false;
        }
        if (isEmailExists) {
            show_form_item_validate_msg($("#input-register-email"), VALIDATION_WARNING_STATUS, "邮箱已被占用，请重新输入");
            return false;
        } else {
            show_form_item_validate_msg($("#input-register-email"), VALIDATION_SUCCESS_STATUS, "");
        }
        if (!verify_code_format()) {
            return false;
        }

        // Send an ajax request to server for user register
        $.ajax({
            url: contextPath + "user",
            type: "post",
            data: $("#form-register").serialize(),
            dataType: "json",
            success: function (response) {
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    show_notice_modal(response.code, response.msg);
                } else {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求注册失败，请稍后重试",);
            }
        });
    });

    // Register modal close event
    $("#btn-register-modal-close").click(function () {
        // Clear the style and content of the register form item
        modal_item_reset_default($("#input-register-username"));
        modal_item_reset_default($("#input-register-password"));
        modal_item_reset_default($("#input-register-email"));
        // The verify code input text is a special one
        input_register_verify_code($("#input-register-verify-code"));
    });
    // Remove the style and data of the input-register-verify-code
    var input_register_verify_code = function (element) {
        element.val("");
        var $helpObj = element.parent().next("span");
        var $commonParentObj = element.parent().parent();
        // Reset the style and clean content
        $helpObj.text("");
        $commonParentObj.removeClass("has-error has-success has-warning");
    };

    // Verify the format of password
    $("#input-register-password").change(function () {
        verify_register_password_format();
    });
    // Verify password format
    var verify_register_password_format = function () {
        var $passwordObj = $("#input-register-password");
        var password = $passwordObj.val();
        if (password.length < 6 || password.length > 16) {
            show_form_item_validate_msg($passwordObj, VALIDATION_ERROR_STATUS, "密码为 6-16 位字符");
            return false;
        } else {
            show_form_item_validate_msg($passwordObj, VALIDATION_SUCCESS_STATUS, "")
            return true;
        }
    };

    // Verify the format of the verify code
    $("#input-register-verify-code").change(function () {
        verify_code_format();
    });
    // Verify register verify code format
    var verify_code_format = function () {
        var $codeObj = $("#input-register-verify-code");
        var code = $codeObj.val();
        if (code.length < 6) {
            show_validate_email_code_msg($codeObj, VALIDATION_ERROR_STATUS, "验证码长度不正确");
            return false;
        } else {
            show_validate_email_code_msg($codeObj, VALIDATION_SUCCESS_STATUS, "");
            return true;
        }
    };

    // Show the verify msg of the email verify code
    var show_validate_email_code_msg = function (element, status, msg) {
        var $helpObj = element.parent().next("span");
        var $parentObj = element.parent().parent();
        // Reset the style and clear content
        $helpObj.text("");
        $parentObj.removeClass("has-error has-success has-warning");

        if (VALIDATION_SUCCESS_STATUS === status) {
            $parentObj.addClass("has-success");
        } else if (VALIDATION_ERROR_STATUS === status) {
            $parentObj.addClass("has-error");
        } else if (VALIDATION_WARNING_STATUS === status) {
            $parentObj.addClass("has-warning");
        }
        $helpObj.text(msg);
    };
});