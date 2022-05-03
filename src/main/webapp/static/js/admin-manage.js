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

    // Admin manage main content element
    var $div_main_content = $("#div-admin-manage-content");

    /* ============================================== Upload background ============================================= */
    // Upload background link click event
    $("#link-upload-background").click(function () {
        $div_main_content.empty();
        build_background_upload();
    });

    // Build upload background image module
    var build_background_upload = function () {
        var $input = $("<input/>").attr("type", "file").attr("accept", "image/jpeg,image/png")
            .attr("id", "input-background-upload").addClass("btn btn-block btn-success")
            .appendTo($div_main_content);
        $("<hr/>").appendTo($div_main_content);
        var $img = $("<img/>").attr("src", "").attr("alt", "").attr("id", "img-background")
            .addClass("img-responsive").appendTo($div_main_content);
        $("<hr/>").appendTo($div_main_content);
        var $btn = $("<button></button>").attr("type", "button").attr("id", "btn-background-upload")
            .append("立即上传").addClass("btn btn-block btn-primary").appendTo($div_main_content);
        var file;
        var fileName;
        // Choose image input element content change event
        $input.on("change", function (e) {
            file = e.target.files[0];
            fileName = $(this).val();
            var path = window.URL.createObjectURL(this.files[0]);
            $img.attr("src", path);
        });
        // Upload image now click event
        $btn.click(function () {
            var suffix = fileName.substring(fileName.lastIndexOf("."));
            alert(suffix);
            if (!(suffix === ".jpg" || suffix === ".png" || suffix === ".jpeg")) {
                show_notice_modal(RESPONSE_ERROR_CODE, "请选择正确格式的图片文件");
                return false;
            }
            // TODO Ask the server to save the background image file
        });
    };

    /* ================================================ Upload book ================================================= */
    $("#link-upload-book").click(function () {
        $div_main_content.empty();
        build_book_upload();
    });

    // Build upload book module
    var build_book_upload = function () {

    };

    /* ================================================ Show pixabay ================================================= */
    // Send an ajax request to server for getting a pixabay picture info
    var get_pixabay = function () {
        $div_main_content.empty();
        // Send an ajax request to server for pixabay info
        $.ajax({
            url: contextPath + "pixabay",
            type: "get",
            dataType: "json",
            success: function (response) {
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    build_pixabay_show_module(response);
                } else {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求获取 Pixabay 图片失败");
            }
        })
    };
    $("#link-show-pixabay").click(function () {
        $div_main_content.empty();
        get_pixabay();
    });

    // Build show pixabay module
    var build_pixabay_show_module = function (response) {
        var pixabay = response.resultMap.content;
        var $div_pixabay_manage = $("<div></div>").addClass("text-center").appendTo($div_main_content);
        // Delete current picture link
        var $span_delete = $("<span></span>").addClass("glyphicon glyphicon-trash").attr("aria-hidden", "true");
        var $btn_delete = $("<button></button>").attr("type", "button").addClass("btn btn-lg btn-primary")
            .append($span_delete).appendTo($div_pixabay_manage);
        // Delete all pictures link
        var $span_refresh = $("<span></span>").addClass("glyphicon glyphicon-refresh").attr("aria-hidden", "true");
        var $btn_refresh = $("<button></button>").attr("type", "button").addClass("btn btn-lg btn-warning")
            .append($span_refresh).appendTo($div_pixabay_manage);

        $("<hr/>").appendTo($div_main_content);
        var $a_img = $("<a></a>").attr("target", "_blank").attr("href", pixabay.url).appendTo($div_main_content);
        $("<img/>").attr("src", "" + pixabay.url).attr("alt", "").addClass("img-responsive")
            .appendTo($a_img);

        // Delete a pixabay picture click event
        $btn_delete.click(function () {
            var id = pixabay.id;
            // Send a request to server for delete a pixabay picture
            $.ajax({
                url: contextPath + "pixabay/" + id,
                type: "post",
                data: "_method=delete",
                dataType: "json",
                success: function (response) {
                    if (RESPONSE_SUCCESS_CODE === response.code) {
                        get_pixabay();
                    } else {
                        show_notice_modal(response.code, response.msg);
                    }
                },
                error: function () {
                    show_notice_modal(RESPONSE_ERROR_CODE, "请求删除 Pixabay 图片失败");
                }
            })
        });

        // Delete all pictures than import new pixabay pictures
        $btn_refresh.click(function () {
            // TODO design a modal to get the condition and pages
            $.ajax({
                url: contextPath + "pixabay/girls/1",
                type: "post",
                data: "_method=put",
                dataType: "json",
                success: function (response) {
                    if (RESPONSE_SUCCESS_CODE === response.code) {
                        get_pixabay();
                    } else {
                        show_notice_modal(response.code, response.msg);
                    }
                },
                error: function () {
                    show_notice_modal(RESPONSE_ERROR_CODE, "请求获取 Pixabay 数据失败");
                }
            })
        });
    };

    /* ================================================ Show portrait ================================================= */
    $("#link-show-portrait").click(function () {
        $div_main_content.empty();
        show_user_portrait_module();
    });

    // Build show user portrait
    var show_user_portrait_module = function () {
        $("<h3></h3>").append("用户头像").addClass("page-header").appendTo($div_main_content);
        var $div_row = $("<div></div>").addClass("row placeholders").appendTo($div_main_content);

        // First portrait
        var $div_img_parent = $("<div></div>").addClass("col-xs-6 col-sm-3 placeholder").appendTo($div_row);
        var $img = $("<img/>").attr("width", "200").attr("height", "200").attr("alt", "")
            .addClass("img-responsive").attr("src", "3.jpg").appendTo($div_img_parent);
        $("<h4></h4>").append("Spring-_-Bear").appendTo($div_img_parent);
        // First portrait
    };
});